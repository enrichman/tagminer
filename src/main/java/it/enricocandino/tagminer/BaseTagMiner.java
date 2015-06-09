package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;

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

}
