package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.miner.OrdinalTagMiner;
import it.enricocandino.tagminer.miner.TagMiner;
import it.enricocandino.tagminer.miner.TimeTagMiner;
import it.enricocandino.tagminer.miner.UrlTagMiner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enrico Candino
 */
public abstract class Miner {

    private List<TagMiner> miners;

    public void addTagMiner(TagMiner tagMiner) {
        if(this.miners == null)
            this.miners = new ArrayList<TagMiner>();
        this.miners.add(tagMiner);
    }

    public List<TaggedSentence> mine(String warcId, List<String> sentences) {
        List<TaggedSentence> taggedSentences = new ArrayList<TaggedSentence>();
        for(String sentence : sentences) {
            taggedSentences.add(mine(warcId, sentence));
        }
        return taggedSentences;
    }

    public TaggedSentence mine(String warcId, String sentence) {
        TaggedSentence taggedSentence = new TaggedSentence();
        taggedSentence.setWarcId(warcId);
        taggedSentence.setOriginalSentence(sentence);
        taggedSentence.setTaggedSentence(sentence);

        if(miners != null) {
            for (TagMiner miner : miners) {
                taggedSentence = miner.mine(taggedSentence);
            }
        }

        return taggedSentence;
    }

}
