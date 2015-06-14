package it.enricocandino.tagminer.miner;

import it.enricocandino.model.TaggedSentence;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enrico Candino
 */
public abstract class BaseTagMiner implements TagMiner {

    public TaggedSentence mine(String sentence) {
        TaggedSentence taggedSentence = new TaggedSentence();
        taggedSentence.setOriginalSentence(sentence);
        taggedSentence.setTaggedSentence(sentence);

        return mine(taggedSentence);
    }

    public TaggedSentence setValue(String tag, String value, TaggedSentence taggedSentence) {
        List<String> values = taggedSentence.getTagValuesMap().get(tag);
        if (values == null)
            values = new ArrayList<String>();
        values.add(value);
        taggedSentence.getTagValuesMap().put(tag, values);

        return taggedSentence;
    }

}
