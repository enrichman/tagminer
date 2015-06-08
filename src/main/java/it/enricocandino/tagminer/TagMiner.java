package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;

import java.util.List;

/**
 * @author Enrico Candino
 */
public interface TagMiner {

    TaggedSentence mine(String sentence);
}
