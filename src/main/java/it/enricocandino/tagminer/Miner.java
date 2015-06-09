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

    private List<TagMiner> miners;

    public Miner() {
        this.miners = new ArrayList<TagMiner>();
        this.miners.add(new UrlTagMiner());
    }

    public List<TaggedSentence> mine(List<String> sentences) {
        List<TaggedSentence> taggedSentences = new ArrayList<TaggedSentence>();
        for(String sentence : sentences) {
            taggedSentences.add(mine(sentence));
        }
        return taggedSentences;
    }

    public TaggedSentence mine(String sentence) {
        TaggedSentence taggedSentence = new TaggedSentence();
        taggedSentence.setOriginalSentence(sentence);
        taggedSentence.setTaggedSentence(sentence);

        for(TagMiner miner : miners) {
            taggedSentence = miner.mine(taggedSentence);
        }

        return taggedSentence;
    }

}
