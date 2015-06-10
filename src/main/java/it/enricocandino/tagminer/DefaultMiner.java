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
public class DefaultMiner extends Miner {

    public DefaultMiner() {
        addTagMiner(new UrlTagMiner());
        addTagMiner(new OrdinalTagMiner());
        addTagMiner(new TimeTagMiner());
    }

}
