import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.Miner;
import it.enricocandino.tagminer.UrlTagMiner;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

/**
 * @author Enrico Candino
 */
public class UrlTagMinerTest {

    @Test
    public void normalMining() {
        String originalSentence = "Una frase con un sito web abc.com come questa ad esempio";
        String taggedSentenceResult = "Una frase con un sito web #URL come questa ad esempio";

        UrlTagMiner miner = new UrlTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#URL").size());
        assertEquals("abc.com", taggedSentence.getTagValuesMap().get("#URL").get(0));
    }

    @Test
    public void normalMiningWithSameUrls() {
        String originalSentence = "Una frase con un sito web abc.com e anche abc.com come questa ad esempio";
        String taggedSentenceResult = "Una frase con un sito web #URL e anche #URL come questa ad esempio";

        UrlTagMiner miner = new UrlTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#URL").size());
        assertEquals("abc.com", taggedSentence.getTagValuesMap().get("#URL").get(0));
        assertEquals("abc.com", taggedSentence.getTagValuesMap().get("#URL").get(1));
    }

    @Test
    public void normalMiningWithTwoDifferentUrls() {
        String originalSentence = "Una frase con un sito web abc.com e anche def.it come questa ad esempio";
        String taggedSentenceResult = "Una frase con un sito web #URL e anche #URL come questa ad esempio";

        UrlTagMiner miner = new UrlTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#URL").size());
        assertEquals("abc.com", taggedSentence.getTagValuesMap().get("#URL").get(0));
        assertEquals("def.it", taggedSentence.getTagValuesMap().get("#URL").get(1));
    }

    @Test
    public void normalMiningWithThreeUrls() {
        String originalSentence = "Una frase con un sito web abc.com, def.it e anche abc.com come questa ad esempio";
        String taggedSentenceResult = "Una frase con un sito web #URL, #URL e anche #URL come questa ad esempio";

        UrlTagMiner miner = new UrlTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(3, taggedSentence.getTagValuesMap().get("#URL").size());
        assertEquals("abc.com", taggedSentence.getTagValuesMap().get("#URL").get(0));
        assertEquals("def.it", taggedSentence.getTagValuesMap().get("#URL").get(1));
        assertEquals("abc.com", taggedSentence.getTagValuesMap().get("#URL").get(2));
    }
}
