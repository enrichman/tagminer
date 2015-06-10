package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.miner.OrdinalTagMiner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Enrico Candino
 */
public class OrdinalTagMinerTest {

    @Test
    public void normalMining() {
        String originalSentence     = "Una frase con un ordinale 1st a caso";
        String taggedSentenceResult = "Una frase con un ordinale #ORD a caso";

        OrdinalTagMiner miner = new OrdinalTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals("1st", taggedSentence.getTagValuesMap().get("#ORD").get(0));
    }

    @Test
    public void normalMiningWithTwoOrdinal() {
        String originalSentence     = "Una frase con due ordinali 1st, e 2nd a caso";
        String taggedSentenceResult = "Una frase con due ordinali #ORD, e #ORD a caso";

        OrdinalTagMiner miner = new OrdinalTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals("1st", taggedSentence.getTagValuesMap().get("#ORD").get(0));
        assertEquals("2nd", taggedSentence.getTagValuesMap().get("#ORD").get(1));
    }

    @Test
    public void normalMiningWithDifferentOrdinal() {
        String originalSentence     = "Una frase con due ordinali 1st, e 2nd a caso, poi anche 3rd e 4th.";
        String taggedSentenceResult = "Una frase con due ordinali #ORD, e #ORD a caso, poi anche #ORD e #ORD.";

        OrdinalTagMiner miner = new OrdinalTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(4, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals("1st", taggedSentence.getTagValuesMap().get("#ORD").get(0));
        assertEquals("2nd", taggedSentence.getTagValuesMap().get("#ORD").get(1));
        assertEquals("3rd", taggedSentence.getTagValuesMap().get("#ORD").get(2));
        assertEquals("4th", taggedSentence.getTagValuesMap().get("#ORD").get(3));
    }

    @Test
    public void normalMiningWithSameOrdinal() {
        String originalSentence     = "Una frase con due ordinali 1st, e 2nd a caso, poi anche 1st e 4th.";
        String taggedSentenceResult = "Una frase con due ordinali #ORD, e #ORD a caso, poi anche #ORD e #ORD.";

        OrdinalTagMiner miner = new OrdinalTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(4, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals("1st", taggedSentence.getTagValuesMap().get("#ORD").get(0));
        assertEquals("2nd", taggedSentence.getTagValuesMap().get("#ORD").get(1));
        assertEquals("1st", taggedSentence.getTagValuesMap().get("#ORD").get(2));
        assertEquals("4th", taggedSentence.getTagValuesMap().get("#ORD").get(3));
    }
}
