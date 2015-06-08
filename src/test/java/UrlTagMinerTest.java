import it.enricocandino.model.TaggedSentence;
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
        String taggedSentenceResult = "Una frase con un sito web #URL1 come questa ad esempio";

        UrlTagMiner tagMiner = new UrlTagMiner();
        TaggedSentence taggedSentence = tagMiner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getValueMap().size());
        assertEquals("abc.com", taggedSentence.getValueMap().get("#URL1"));
    }

    @Test
    public void normalMiningWithSameUrls() {
        String originalSentence = "Una frase con un sito web abc.com e anche abc.com come questa ad esempio";
        String taggedSentenceResult = "Una frase con un sito web #URL1 e anche #URL2 come questa ad esempio";

        UrlTagMiner tagMiner = new UrlTagMiner();
        TaggedSentence taggedSentence = tagMiner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getValueMap().size());
        assertEquals("abc.com", taggedSentence.getValueMap().get("#URL1"));
        assertEquals("abc.com", taggedSentence.getValueMap().get("#URL2"));
    }

    @Test
    public void normalMiningWithTwoDifferentUrls() {
        String originalSentence = "Una frase con un sito web abc.com e anche def.it come questa ad esempio";
        String taggedSentenceResult = "Una frase con un sito web #URL1 e anche #URL2 come questa ad esempio";

        UrlTagMiner tagMiner = new UrlTagMiner();
        TaggedSentence taggedSentence = tagMiner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getValueMap().size());
        assertEquals("abc.com", taggedSentence.getValueMap().get("#URL1"));
        assertEquals("def.it", taggedSentence.getValueMap().get("#URL2"));
    }
}
