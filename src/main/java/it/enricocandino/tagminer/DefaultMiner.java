package it.enricocandino.tagminer;

import it.enricocandino.tagminer.miner.*;

/**
 * @author Enrico Candino
 */
public class DefaultMiner extends Miner {

    public DefaultMiner() {
        addTagMiner(new UrlTagMiner());
        addTagMiner(new OrdinalTagMiner());
        addTagMiner(new TimeTagMiner());
        addTagMiner(new MonthTagMiner());
    }

}
