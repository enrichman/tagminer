package it.enricocandino.tagminer;

import it.enricocandino.tagminer.miner.*;

/**
 * @author Enrico Candino
 */
public class DefaultMiner extends Miner {

    public DefaultMiner() {
        addTagMiner(new UrlTagMiner());
        addTagMiner(new PhoneTagMiner());
        addTagMiner(new EmailTagMiner());
        addTagMiner(new MoneyTagMiner());
        addTagMiner(new OrdinalTagMiner());
        addTagMiner(new TimeTagMiner());
        addTagMiner(new DayTagMiner());
        addTagMiner(new MonthTagMiner());
        addTagMiner(new NumberTagMiner());
    }

}
