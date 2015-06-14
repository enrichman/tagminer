package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.miner.NumberTagMiner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Enrico Candino
 */
public class NumberTagMinerTest {

    @Test
    public void normalMining() {
        String originalSentence = "Una frase con un numero 334 a caso";
        String taggedSentenceResult = "Una frase con un numero #NUM a caso";

        NumberTagMiner miner = new NumberTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#NUM").size());
        assertEquals("334", taggedSentence.getTagValuesMap().get("#NUM").get(0));
    }

    @Test
    public void miningNumberInsideWord() {
        String originalSentence = "Una frase con un numero st0p a caso";
        String taggedSentenceResult = "Una frase con un numero st0p a caso";

        NumberTagMiner miner = new NumberTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertNull(taggedSentence.getTagValuesMap().get("#NUM"));
    }

}
