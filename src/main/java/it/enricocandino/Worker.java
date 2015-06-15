package it.enricocandino;

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

/**
 * @author Enrico Candino
 */
public class Worker implements Runnable {

    private CsvWriter writer;
    private Page page;

    public Worker(CsvWriter writer, Page page) {
        this.writer = writer;
        this.page = page;
    }

    public void run() {

        // parse the page content in the body
        Document doc = Jsoup.parse(page.getHtml());
        String bodyText = doc.select("body").text();

        // Split the text in sentences
        List<String> sentences = SentenceSplitter.split(bodyText);
        if(sentences.isEmpty())
            return;

        // Remove the "noisy" sentences
        DefaultSentenceFilter filter = new DefaultSentenceFilter();
        List<String> cleanedSentences = filter.doFilter(sentences);
        if(cleanedSentences.isEmpty())
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
