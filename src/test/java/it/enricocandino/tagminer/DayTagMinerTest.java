package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.miner.DayTagMiner;
import it.enricocandino.tagminer.miner.MonthTagMiner;
import it.enricocandino.tagminer.miner.OrdinalTagMiner;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * @author Enrico Candino
 */
public class DayTagMinerTest {

    @Test
    public void normalMining() {
        String originalSentence     = "Una frase con una giorno Monday a caso";
        String taggedSentenceResult = "Una frase con una giorno #DAY a caso";

        DayTagMiner miner = new DayTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#DAY").size());
        assertEquals("Monday", taggedSentence.getTagValuesMap().get("#DAY").get(0));
    }

}
