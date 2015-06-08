package it.enricocandino.tagminer;

import it.enricocandino.model.Page;
import it.enricocandino.model.TaggedSentence;
import it.enricocandino.util.SentenceUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * @author Enrico Candino
 */
public class Miner {

    public List<TaggedSentence> mine(Page page) {
        Document doc = Jsoup.parse(page.getHtml());
        String bodyText = doc.select("body").text();

        List<String> sentences = SentenceUtil.split(bodyText);
        List<String> cleanedSentences = SentenceUtil.clean(sentences);

        TaggedSentence taggedSentence = null;

        TagMiner miner = new UrlTagMiner();
        for(String sentence : cleanedSentences) {
            taggedSentence = miner.mine(sentence);
        }

        return null;
    }

}
