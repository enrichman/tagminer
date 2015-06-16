package it.enricocandino;

import clueweb09.WarcHTMLResponseRecord;
import clueweb09.WarcRecord;
import it.enricocandino.model.Page;
import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.DefaultMiner;
import it.enricocandino.tagminer.Miner;
import it.enricocandino.tagminer.combiner.DefaultTagCombiner;
import it.enricocandino.tagminer.combiner.TagCombiner;
import it.enricocandino.text.DefaultSentenceFilter;
import it.enricocandino.text.SentenceSplitter;
import it.enricocandino.text.csv.CsvWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Scanner;

/**
 * @author Enrico Candino
 */
public class Worker implements Runnable {

    private CsvWriter writer;
    private WarcRecord record;

    public Worker(CsvWriter writer, WarcRecord record) {
        this.writer = writer;
        this.record = record;
    }

    public void run() {

        Page page = buildPageFromWarcRecord(record);
        if(page != null) {

            // parse the page content in the body
            Document doc = Jsoup.parse(page.getHtml());
            String bodyText = doc.select("body").text();

            // Split the text in sentences
            List<String> sentences = SentenceSplitter.split(bodyText);
            if (sentences.isEmpty())
                return;

            // Remove the "noisy" sentences
            DefaultSentenceFilter filter = new DefaultSentenceFilter();
            List<String> cleanedSentences = filter.doFilter(sentences);
            if (cleanedSentences.isEmpty())
                return;

            // mine the sentences!
            Miner miner = new DefaultMiner();
            List<TaggedSentence> taggedSentences = miner.mine(page.getWarcID(), cleanedSentences);

            // combine tags following the default rules
            TagCombiner combiner = new DefaultTagCombiner();
            taggedSentences = combiner.applyRules(taggedSentences);

            writer.write(taggedSentences);
        }
    }

    /*
     * Convert the WarcRecord in a Page
     */
    private Page buildPageFromWarcRecord(WarcRecord record) {
        Page page = null;

        try {
            WarcHTMLResponseRecord htmlRecord = new WarcHTMLResponseRecord(record);

            String rawResponse = new String(htmlRecord.getRawRecord().getByteContent(), "iso-8859-1");

            boolean isTextFile = false;
            boolean headerRead = false;

            StringBuilder responseBuilder = new StringBuilder(htmlRecord.getRawRecord().getTotalRecordLength());

            Scanner scanner = new Scanner(rawResponse);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Content-Type:") && line.contains("text")) {
                    isTextFile = true;
                }

                // header end: check if is text file or is to skip
                if (line.equals("") || headerRead) {
                    headerRead = true;
                    if (!isTextFile) {
                        break;
                    } else {
                        responseBuilder.append(line);
                    }
                }
            }
            scanner.close();

            String responseBody = responseBuilder.toString();
            responseBody = responseBody.trim();

            if (responseBody.length() > 0) {
                page = new Page();
                page.setWarcID(htmlRecord.getRawRecord().getHeaderMetadataItem("WARC-TREC-ID"));
                page.setHtml(responseBody);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return page;
    }
}
