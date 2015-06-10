package it.enricocandino;

import it.enricocandino.model.Page;
import it.enricocandino.extractor.reader.ClueWebReader;
import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.DefaultMiner;
import it.enricocandino.tagminer.Miner;
import it.enricocandino.text.DefaultSentenceFilter;
import it.enricocandino.text.SentenceSplitter;
import it.enricocandino.text.csv.CsvWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enrico Candino
 */
public class TagMinerMain {

    private static final String BASE_FOLDER = "/Users/enrico/Documents/UNI-MAGISTRALE/Analisi e Gestione dell'informazione su Web/warc/";

    public static void main(String[] args) {

        String[] files = new String[] { "out1", "out2", "out3" };
        for(String filename : files) {
            File file = new File(filename);
            if (file.exists())
                file.delete();
        }

        // Read the warc file
        try {
            ClueWebReader reader = new ClueWebReader();

            for (int i = 0; i < 1; i++) {
                String path = BASE_FOLDER + "0" + i + ".warc.gz";
                List<Page> pages = reader.read(path);

                List<TaggedSentence> pageSentences = new ArrayList<TaggedSentence>();

                // process extracted pages
                for (Page p : pages) {

                    // parse the page content in the body
                    Document doc = Jsoup.parse(p.getHtml());
                    String bodyText = doc.select("body").text();

                    // Split the text in sentences
                    List<String> sentences = SentenceSplitter.split(bodyText);
                    if(sentences.isEmpty())
                        continue;

                    // Remove the "noisy" sentences
                    DefaultSentenceFilter filter = new DefaultSentenceFilter();
                    List<String> cleanedSentences = filter.doFilter(sentences);
                    if(cleanedSentences.isEmpty())
                        continue;

                    // mine the sentences!
                    Miner miner = new DefaultMiner();
                    List<TaggedSentence> taggedSentences = miner.mine(cleanedSentences);

                    System.out.println(taggedSentences);
                    pageSentences.addAll(taggedSentences);
                }

                CsvWriter writer = new CsvWriter();
                writer.write(pageSentences);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
