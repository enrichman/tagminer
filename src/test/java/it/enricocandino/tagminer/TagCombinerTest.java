package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.tagminer.combiner.Rule;
import it.enricocandino.tagminer.combiner.TagCombiner;
import it.enricocandino.tagminer.miner.*;
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

        Rule rule = new Rule("#DATE", " ", "#MONTH", "#ORD");
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

        Rule rule = new Rule("#DATE", " ", "#MONTH", "#ORD");
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

        Rule rule = new Rule("#DATE", " ", "#MONTH", "#ORD");
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

        Rule rule = new Rule("#DATE", " ", "#MONTH", "#ORD");
        combiner.addRule(rule);
        Rule ruleInverted = new Rule("#DATE", " ", "#ORD", "#MONTH");
        combiner.addRule(ruleInverted);

        taggedSentence = combiner.applyRules(taggedSentence);

        assertEquals(resultTagged, taggedSentence.getTaggedSentence());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#DATE").size());
        assertEquals("Feb 2nd", taggedSentence.getTagValuesMap().get("#DATE").get(0));
        assertEquals("1st March", taggedSentence.getTagValuesMap().get("#DATE").get(1));
    }

    @Test
    public void dateCombiner() {
        String originalSentence = "20 20/20/2000 20-20-2000 fdnukfndsk";
        String firstTagged      = "#NUM #NUM/#NUM/#NUM #NUM-#NUM-#NUM fdnukfndsk";
        String resultTagged     = "#NUM #DATE #DATE fdnukfndsk";

        NumberTagMiner ordinalTagMiner = new NumberTagMiner();
        TaggedSentence taggedSentence = ordinalTagMiner.mine(originalSentence);

        assertEquals(firstTagged, taggedSentence.getTaggedSentence());
        assertEquals(7, taggedSentence.getTagValuesMap().get("#NUM").size());
        assertEquals("20", taggedSentence.getTagValuesMap().get("#NUM").get(0));
        assertEquals("20", taggedSentence.getTagValuesMap().get("#NUM").get(1));
        assertEquals("20", taggedSentence.getTagValuesMap().get("#NUM").get(2));
        assertEquals("2000", taggedSentence.getTagValuesMap().get("#NUM").get(3));
        assertEquals("20", taggedSentence.getTagValuesMap().get("#NUM").get(4));
        assertEquals("20", taggedSentence.getTagValuesMap().get("#NUM").get(5));
        assertEquals("2000", taggedSentence.getTagValuesMap().get("#NUM").get(6));

        TagCombiner combiner = new TagCombiner();

        Rule rule1 = new Rule("#DATE", "/", "#NUM", "#NUM", "#NUM");
        combiner.addRule(rule1);
        Rule rule2 = new Rule("#DATE", "-", "#NUM", "#NUM", "#NUM");
        combiner.addRule(rule2);

        taggedSentence = combiner.applyRules(taggedSentence);

        assertEquals(resultTagged, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#NUM").size());
        assertEquals("20", taggedSentence.getTagValuesMap().get("#NUM").get(0));
        assertEquals(2, taggedSentence.getTagValuesMap().get("#DATE").size());
        assertEquals("20/20/2000", taggedSentence.getTagValuesMap().get("#DATE").get(0));
        assertEquals("20-20-2000", taggedSentence.getTagValuesMap().get("#DATE").get(1));
    }

    @Test
    public void dateCombinerResult() {
        String originalSentence = "1901-12-13 1826 bla 2000-01-02 2005";

        String firstTagged      = "#NUM-#NUM-#NUM #NUM bla #NUM-#NUM-#NUM #NUM";
        String resultTagged     = "#DATE #NUM bla #DATE #NUM";

        NumberTagMiner ordinalTagMiner = new NumberTagMiner();
        TaggedSentence taggedSentence = ordinalTagMiner.mine(originalSentence);

        assertEquals(firstTagged, taggedSentence.getTaggedSentence());
        assertEquals(8, taggedSentence.getTagValuesMap().get("#NUM").size());
        assertEquals("1901", taggedSentence.getTagValuesMap().get("#NUM").get(0));
        assertEquals("12", taggedSentence.getTagValuesMap().get("#NUM").get(1));
        assertEquals("13", taggedSentence.getTagValuesMap().get("#NUM").get(2));
        assertEquals("1826", taggedSentence.getTagValuesMap().get("#NUM").get(3));
        assertEquals("2000", taggedSentence.getTagValuesMap().get("#NUM").get(4));
        assertEquals("01", taggedSentence.getTagValuesMap().get("#NUM").get(5));
        assertEquals("02", taggedSentence.getTagValuesMap().get("#NUM").get(6));
        assertEquals("2005", taggedSentence.getTagValuesMap().get("#NUM").get(7));

        TagCombiner combiner = new TagCombiner();

        Rule rule1 = new Rule("#DATE", "/", "#NUM", "#NUM", "#NUM");
        combiner.addRule(rule1);
        Rule rule2 = new Rule("#DATE", "-", "#NUM", "#NUM", "#NUM");
        combiner.addRule(rule2);
        Rule rule3 = new Rule("#DATE", ".", "#NUM", "#NUM", "#NUM");
        combiner.addRule(rule3);

        taggedSentence = combiner.applyRules(taggedSentence);

        assertEquals(resultTagged, taggedSentence.getTaggedSentence());
        assertEquals(2, taggedSentence.getTagValuesMap().get("#NUM").size());
        assertEquals("1826", taggedSentence.getTagValuesMap().get("#NUM").get(0));
        assertEquals("2005", taggedSentence.getTagValuesMap().get("#NUM").get(1));
        assertEquals(2, taggedSentence.getTagValuesMap().get("#DATE").size());
        assertEquals("1901-12-13", taggedSentence.getTagValuesMap().get("#DATE").get(0));
        assertEquals("2000-01-02", taggedSentence.getTagValuesMap().get("#DATE").get(1));
    }

    @Test
    public void complexDateTest() {
        String originalSentence = "Jan 2nd 2009 9:06AM";

        String firstTagged      = "#MONTH #ORD #NUM #TIME";
        String resultTagged     = "#DATE";

        OrdinalTagMiner ordinalTagMiner = new OrdinalTagMiner();
        TaggedSentence taggedSentence = ordinalTagMiner.mine(originalSentence);
        TimeTagMiner timeTagMiner = new TimeTagMiner();
        taggedSentence = timeTagMiner.mine(taggedSentence);
        NumberTagMiner numberTagMiner = new NumberTagMiner();
        taggedSentence = numberTagMiner.mine(taggedSentence);
        MonthTagMiner monthTagMiner = new MonthTagMiner();
        taggedSentence = monthTagMiner.mine(taggedSentence);

        assertEquals(firstTagged, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#NUM").size());
        assertEquals("2009", taggedSentence.getTagValuesMap().get("#NUM").get(0));
        assertEquals(1, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals("Jan", taggedSentence.getTagValuesMap().get("#MONTH").get(0));
        assertEquals(1, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals("2nd", taggedSentence.getTagValuesMap().get("#ORD").get(0));
        assertEquals(1, taggedSentence.getTagValuesMap().get("#TIME").size());
        assertEquals("9:06AM", taggedSentence.getTagValuesMap().get("#TIME").get(0));

        TagCombiner combiner = new TagCombiner();

        Rule rule4 = new Rule("#DATE", " ", "#MONTH", "#ORD", "#NUM", "#TIME");
        combiner.addRule(rule4);

        taggedSentence = combiner.applyRules(taggedSentence);

        assertEquals(resultTagged, taggedSentence.getTaggedSentence());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#NUM").size());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#ORD").size());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#TIME").size());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#DATE").size());
        assertEquals("Jan 2nd 2009 9:06AM", taggedSentence.getTagValuesMap().get("#DATE").get(0));
    }

    @Test
    public void monthNum() {
        String originalSentence = "Jan 2009";

        String firstTagged      = "#MONTH #NUM";
        String resultTagged     = "#MONTH_YEAR";

        NumberTagMiner numberTagMiner = new NumberTagMiner();
        TaggedSentence taggedSentence = numberTagMiner.mine(originalSentence);
        MonthTagMiner monthTagMiner = new MonthTagMiner();
        taggedSentence = monthTagMiner.mine(taggedSentence);

        assertEquals(firstTagged, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#NUM").size());
        assertEquals("2009", taggedSentence.getTagValuesMap().get("#NUM").get(0));
        assertEquals(1, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals("Jan", taggedSentence.getTagValuesMap().get("#MONTH").get(0));
        TagCombiner combiner = new TagCombiner();

        Rule rule6 = new Rule("#MONTH_YEAR", " ", "#MONTH", "#NUM");
        combiner.addRule(rule6);

        taggedSentence = combiner.applyRules(taggedSentence);

        assertEquals(resultTagged, taggedSentence.getTaggedSentence());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#NUM").size());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#MONTH_YEAR").size());
        assertEquals("Jan 2009", taggedSentence.getTagValuesMap().get("#MONTH_YEAR").get(0));
    }

    @Test
    public void monthNumComma() {
        String originalSentence = "January, 2009";

        String firstTagged      = "#MONTH, #NUM";
        String resultTagged     = "#DATE";

        NumberTagMiner numberTagMiner = new NumberTagMiner();
        TaggedSentence taggedSentence = numberTagMiner.mine(originalSentence);
        MonthTagMiner monthTagMiner = new MonthTagMiner();
        taggedSentence = monthTagMiner.mine(taggedSentence);

        assertEquals(firstTagged, taggedSentence.getTaggedSentence());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#NUM").size());
        assertEquals("2009", taggedSentence.getTagValuesMap().get("#NUM").get(0));
        assertEquals(1, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals("January", taggedSentence.getTagValuesMap().get("#MONTH").get(0));
        TagCombiner combiner = new TagCombiner();

        Rule rule6 = new Rule("#DATE", ", ", "#MONTH", "#NUM");
        combiner.addRule(rule6);

        taggedSentence = combiner.applyRules(taggedSentence);

        assertEquals(resultTagged, taggedSentence.getTaggedSentence());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#NUM").size());
        assertEquals(0, taggedSentence.getTagValuesMap().get("#MONTH").size());
        assertEquals(1, taggedSentence.getTagValuesMap().get("#DATE").size());
        assertEquals("January, 2009", taggedSentence.getTagValuesMap().get("#DATE").get(0));
    }

}
