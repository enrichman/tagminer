package it.enricocandino.tagminer;

import it.enricocandino.model.Page;
import it.enricocandino.model.TaggedSentence;
import it.enricocandino.text.DefaultSentenceFilter;
import it.enricocandino.text.SentenceSplitter;
import it.enricocandino.util.SentenceUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enrico Candino
 */
public class Miner {

    public List<TaggedSentence> mine(Page page) {
        List<TaggedSentence> taggedSentenceList = new ArrayList<TaggedSentence>();

        Document doc = Jsoup.parse(page.getHtml());
        String bodyText = doc.select("body").text();

        List<String> sentences = SentenceSplitter.split(bodyText);

        List<String> cleanedSentences = new ArrayList<String>();

        DefaultSentenceFilter filter = new DefaultSentenceFilter();
        for(String sentence : sentences) {
            String cleaned = filter.doFilter(sentence);
            if(cleaned != null)
                cleanedSentences.add(cleaned);
        }

        TagMiner miner = new UrlTagMiner();

        for(String sentence : cleanedSentences) {
            TaggedSentence taggedSentence = new TaggedSentence();
            taggedSentence.setOriginalSentence(sentence);
            taggedSentence.setTaggedSentence(sentence);

            TaggedSentence taggedSentenceResult = miner.mine(taggedSentence);
            if(taggedSentenceResult != null)
                taggedSentenceList.add(taggedSentenceResult);
        }

        for(TaggedSentence ts : taggedSentenceList) {
            if(!ts.getTagValuesMap().isEmpty()) {
                System.out.println(ts.getTaggedSentence());
                System.out.println(ts.getTagValuesMap());
            }
        }

        return null;
    }

}
