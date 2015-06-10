package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.miner.PhoneTagMiner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Enrico Candino
 */
public class PhoneTagMinerTest {

    @Test
    public void normalMining() {
        String originalSentence     = "Una frase con un numero 334 122 1234 a caso";
        String taggedSentenceResult = "Una frase con un numero #PHONE a caso";

        PhoneTagMiner miner = new PhoneTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#PHONE").size());
        assertEquals("334 122 1234", taggedSentence.getTagValuesMap().get("#PHONE").get(0));
    }

    @Test
    public void normalMiningWithPrefix() {
        String originalSentence     = "Una frase con un numero 1 334 122 1234 a caso";
        String taggedSentenceResult = "Una frase con un numero #PHONE a caso";

        PhoneTagMiner miner = new PhoneTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#PHONE").size());
        assertEquals("1 334 122 1234", taggedSentence.getTagValuesMap().get("#PHONE").get(0));
    }

    @Test
    public void normalDashMining() {
        String originalSentence     = "Una frase con un numero 334-122-1234 a caso";
        String taggedSentenceResult = "Una frase con un numero #PHONE a caso";

        PhoneTagMiner miner = new PhoneTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#PHONE").size());
        assertEquals("334-122-1234", taggedSentence.getTagValuesMap().get("#PHONE").get(0));
    }

    @Test
    public void normalDashMiningWithPrefix() {
        String originalSentence     = "Una frase con un numero 1-334-122-1234 a caso";
        String taggedSentenceResult = "Una frase con un numero #PHONE a caso";

        PhoneTagMiner miner = new PhoneTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#PHONE").size());
        assertEquals("1-334-122-1234", taggedSentence.getTagValuesMap().get("#PHONE").get(0));
    }

}
