package it.enricocandino.tagminer.miner;

import it.enricocandino.model.TaggedSentence;

import java.util.List;

/**
 * @author Enrico Candino
 */
public interface TagMiner {

    TaggedSentence mine(TaggedSentence sentence);
}
