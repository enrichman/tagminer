package it.enricocandino;

import it.enricocandino.model.Page;
import it.enricocandino.extractor.reader.ClueWebReader;
import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.Miner;
import it.enricocandino.text.DefaultSentenceFilter;
import it.enricocandino.text.SentenceSplitter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * @author Enrico Candino
 */
public class Extractor {

    private static final String BASE_FOLDER = "/Users/enrico/Documents/UNI-MAGISTRALE/Analisi e Gestione dell'informazione su Web/warc/";

    public static void main(String[] args) {
        try {

            ClueWebReader reader = new ClueWebReader();

            for (int i = 0; i < 1; i++) {

                String path = BASE_FOLDER + "0" + i + ".warc.gz";

                // Read the warc file
                List<Page> pages = reader.read(path);

                for(Page p : pages) {

                    // parse the page content in the body
                    Document doc = Jsoup.parse(p.getHtml());
                    String bodyText = doc.select("body").text();

                    // Split the text in sentences
                    List<String> sentences = SentenceSplitter.split(bodyText);

                    // Remove the "noisy" sentences
                    DefaultSentenceFilter filter = new DefaultSentenceFilter();
                    List<String> cleanedSentences = filter.doFilter(sentences);

                    // mine the sentences!
                    Miner miner = new Miner();
                    List<TaggedSentence> taggedSentences = miner.mine(cleanedSentences);

                    System.out.println(taggedSentences);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
