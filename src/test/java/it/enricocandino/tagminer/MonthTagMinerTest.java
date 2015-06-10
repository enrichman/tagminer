package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.miner.MonthTagMiner;
import it.enricocandino.tagminer.miner.OrdinalTagMiner;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * @author Enrico Candino
 */
public class MonthTagMinerTest {

    @Test
    public void normalMining() {
        String originalSentence     = "Una frase con una data March a caso";
        String taggedSentenceResult = "Una frase con una data #DATE a caso";

        MonthTagMiner miner = new MonthTagMiner();
        TaggedSentence taggedSentence = miner.mine(originalSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#DATE").size());
        assertEquals("March", taggedSentence.getTagValuesMap().get("#DATE").get(0));
    }

    @Test
    public void combinedMining() {
        String originalSentence     = "Una frase con april una data March 1st a caso apr";
        String taggedSentenceResult = "Una frase con #DATE una data #DATE #ORD a caso #DATE";

        OrdinalTagMiner ordinalTagMiner = new OrdinalTagMiner();
        TaggedSentence taggedSentence = ordinalTagMiner.mine(originalSentence);

        MonthTagMiner miner = new MonthTagMiner();
        taggedSentence = miner.mine(taggedSentence);

        assertEquals(taggedSentenceResult, taggedSentence.getTaggedSentence());
        assertEquals(3, taggedSentence.getTagValuesMap().get("#DATE").size());
        assertEquals("april", taggedSentence.getTagValuesMap().get("#DATE").get(0));
        assertEquals("March", taggedSentence.getTagValuesMap().get("#DATE").get(1));
        assertEquals("apr", taggedSentence.getTagValuesMap().get("#DATE").get(2));

        Matcher matcher = Pattern.compile("#DATE\\s#ORD").matcher(taggedSentence.getTaggedSentence());
        if(matcher.find()) {
            String result = matcher.group();

            int index = 0;
            int count = 0;

            Matcher dateMatcher = Pattern.compile("#DATE").matcher(taggedSentence.getTaggedSentence());
            while(dateMatcher.find()) {
                if(matcher.start() == dateMatcher.start()) {
                    index = count;
                }
                count++;
            }

            String dateValue = taggedSentence.getTagValuesMap().get("#DATE").get(index);

            Matcher ordMatcher = Pattern.compile("#ORD").matcher(taggedSentence.getTaggedSentence());
            while(ordMatcher.find()) {
                if(matcher.start() == ordMatcher.start()) {
                    index = count;
                }
                count++;
            }

            String ordValue = taggedSentence.getTagValuesMap().get("#ORD").get(index);


            System.out.println(dateValue +" "+ordValue);
        }
    }
}
