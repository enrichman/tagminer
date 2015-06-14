package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.miner.MonthTagMiner;
import it.enricocandino.tagminer.miner.OrdinalTagMiner;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

/**
 * @author Enrico Candino
 */
public class MonthTagMinerTest {

    @Test
    public void normalMining() {
        String originalSentence     = "Una frase con una data March a caso";
        String taggedSentenceResult = "Una frase con una data #MONTH a caso";

        MonthTagMiner miner = new MonthTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals("March", taggedSentence.getTagValuesMap().get("#MONTH").get(0));
    }

    @Test
    public void combinedMining() {
        String originalSentence     = "Una frase con april una data March 1st a caso apr";
        String taggedSentenceResult = "Una frase con #MONTH una data #MONTH #ORD a caso #MONTH";

        OrdinalTagMiner ordinalTagMiner = new OrdinalTagMiner();
        TaggedSentence taggedSentence = ordinalTagMiner.mine(originalSentence);

        MonthTagMiner miner = new MonthTagMiner();
        taggedSentence = miner.mine(taggedSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(3, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals("april", taggedSentence.getTagValuesMap().get("#MONTH").get(0));
        assertEquals("March", taggedSentence.getTagValuesMap().get("#MONTH").get(1));
        assertEquals("apr", taggedSentence.getTagValuesMap().get("#MONTH").get(2));
    }
}
