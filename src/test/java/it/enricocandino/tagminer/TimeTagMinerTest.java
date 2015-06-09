package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Enrico Candino
 */
public class TimeTagMinerTest {

    @Test
    public void normalMining() {
        String originalSentence     = "Una frase con un tempo 12:23 a caso";
        String taggedSentenceResult = "Una frase con un tempo #TIME a caso";

        TimeTagMiner miner = new TimeTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#TIME").size());
        assertEquals("12:23", taggedSentence.getTagValuesMap().get("#TIME").get(0));
    }

    @Test
    public void normalMiningWithTwoTime() {
        String originalSentence     = "Una frase con un tempo 12:23, ed un altro 12h 34m 23s a caso";
        String taggedSentenceResult = "Una frase con un tempo #TIME, ed un altro #TIME a caso";

        TimeTagMiner miner = new TimeTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#TIME").size());
        assertEquals("12:23", taggedSentence.getTagValuesMap().get("#TIME").get(0));
        assertEquals("12h 34m 23s", taggedSentence.getTagValuesMap().get("#TIME").get(1));
    }

}
