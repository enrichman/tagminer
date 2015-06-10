package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.combiner.Rule;
import it.enricocandino.tagminer.combiner.TagCombiner;
import it.enricocandino.tagminer.miner.MonthTagMiner;
import it.enricocandino.tagminer.miner.OrdinalTagMiner;
import it.enricocandino.tagminer.miner.TagMiner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Enrico Candino
 */
public class TagCombinerTest {

    @Test
    public void normalCombining() {
        String originalSentence = "March 1st";
        String firstTagged      = "March #ORD";
        String secondTagged     = "#MONTH #ORD";
        String resultTagged     = "#DATE";

        OrdinalTagMiner ordinalTagMiner = new OrdinalTagMiner();
        TaggedSentence taggedSentence = ordinalTagMiner.mine(originalSentence);

        assertEquals(firstTagged, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals("1st", taggedSentence.getTagValuesMap().get("#ORD").get(0));

        TagMiner miner = new MonthTagMiner();
        taggedSentence = miner.mine(taggedSentence);

        assertEquals(secondTagged, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals("March", taggedSentence.getTagValuesMap().get("#MONTH").get(0));

        Rule rule = new Rule("#DATE", "#MONTH", "#ORD");
        TagCombiner combiner = new TagCombiner();
        combiner.addRule(rule);

        taggedSentence = combiner.applyRules(taggedSentence);

        assertEquals(resultTagged, taggedSentence.getTaggedSentence());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#DATE").size());
        assertEquals("March 1st", taggedSentence.getTagValuesMap().get("#DATE").get(0));
    }

    @Test
    public void multipleCombining() {
        String originalSentence = "March 1st and Feb 2nd";
        String firstTagged      = "March #ORD and Feb #ORD";
        String secondTagged     = "#MONTH #ORD and #MONTH #ORD";
        String resultTagged     = "#DATE and #DATE";

        OrdinalTagMiner ordinalTagMiner = new OrdinalTagMiner();
        TaggedSentence taggedSentence = ordinalTagMiner.mine(originalSentence);

        assertEquals(firstTagged, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals("1st", taggedSentence.getTagValuesMap().get("#ORD").get(0));
        assertEquals("2nd", taggedSentence.getTagValuesMap().get("#ORD").get(1));

        TagMiner miner = new MonthTagMiner();
        taggedSentence = miner.mine(taggedSentence);

        assertEquals(secondTagged, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals("March", taggedSentence.getTagValuesMap().get("#MONTH").get(0));
        assertEquals("Feb", taggedSentence.getTagValuesMap().get("#MONTH").get(1));

        Rule rule = new Rule("#DATE", "#MONTH", "#ORD");
        TagCombiner combiner = new TagCombiner();
        combiner.addRule(rule);

        taggedSentence = combiner.applyRules(taggedSentence);

        assertEquals(resultTagged, taggedSentence.getTaggedSentence());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#DATE").size());
        assertEquals("March 1st", taggedSentence.getTagValuesMap().get("#DATE").get(0));
        assertEquals("Feb 2nd", taggedSentence.getTagValuesMap().get("#DATE").get(1));
    }

    @Test
    public void combineOnlyOne() {
        String originalSentence = "1st March and Feb 2nd";
        String firstTagged      = "#ORD March and Feb #ORD";
        String secondTagged     = "#ORD #MONTH and #MONTH #ORD";
        String resultTagged     = "#ORD #MONTH and #DATE";

        OrdinalTagMiner ordinalTagMiner = new OrdinalTagMiner();
        TaggedSentence taggedSentence = ordinalTagMiner.mine(originalSentence);

        assertEquals(firstTagged, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals("1st", taggedSentence.getTagValuesMap().get("#ORD").get(0));
        assertEquals("2nd", taggedSentence.getTagValuesMap().get("#ORD").get(1));

        TagMiner miner = new MonthTagMiner();
        taggedSentence = miner.mine(taggedSentence);

        assertEquals(secondTagged, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals("March", taggedSentence.getTagValuesMap().get("#MONTH").get(0));
        assertEquals("Feb", taggedSentence.getTagValuesMap().get("#MONTH").get(1));

        TagCombiner combiner = new TagCombiner();

        Rule rule = new Rule("#DATE", "#MONTH", "#ORD");
        combiner.addRule(rule);

        taggedSentence = combiner.applyRules(taggedSentence);

        assertEquals(resultTagged, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals("1st", taggedSentence.getTagValuesMap().get("#ORD").get(0));
        assertEquals(1, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals("March", taggedSentence.getTagValuesMap().get("#MONTH").get(0));
        assertEquals(1, taggedSentence.getTagValuesMap().get("#DATE").size());
        assertEquals("Feb 2nd", taggedSentence.getTagValuesMap().get("#DATE").get(0));
    }

    @Test
    public void combineTwoRules() {
        String originalSentence = "1st March and Feb 2nd";
        String firstTagged      = "#ORD March and Feb #ORD";
        String secondTagged     = "#ORD #MONTH and #MONTH #ORD";
        String resultTagged     = "#DATE and #DATE";

        OrdinalTagMiner ordinalTagMiner = new OrdinalTagMiner();
        TaggedSentence taggedSentence = ordinalTagMiner.mine(originalSentence);

        assertEquals(firstTagged, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals("1st", taggedSentence.getTagValuesMap().get("#ORD").get(0));
        assertEquals("2nd", taggedSentence.getTagValuesMap().get("#ORD").get(1));

        TagMiner miner = new MonthTagMiner();
        taggedSentence = miner.mine(taggedSentence);

        assertEquals(secondTagged, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals("March", taggedSentence.getTagValuesMap().get("#MONTH").get(0));
        assertEquals("Feb", taggedSentence.getTagValuesMap().get("#MONTH").get(1));

        TagCombiner combiner = new TagCombiner();

        Rule rule = new Rule("#DATE", "#MONTH", "#ORD");
        combiner.addRule(rule);
        Rule ruleInverted = new Rule("#DATE", "#ORD", "#MONTH");
        combiner.addRule(ruleInverted);

        taggedSentence = combiner.applyRules(taggedSentence);

        assertEquals(resultTagged, taggedSentence.getTaggedSentence());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#DATE").size());
        assertEquals("Feb 2nd", taggedSentence.getTagValuesMap().get("#DATE").get(0));
        assertEquals("1st March", taggedSentence.getTagValuesMap().get("#DATE").get(1));
    }
}
