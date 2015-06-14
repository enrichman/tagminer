package it.enricocandino.tagminer.combiner;

/**
 * @author Enrico Candino
 */
public class DefaultTagCombiner extends TagCombiner {

    public DefaultTagCombiner() {
        Rule rule1 = new Rule("#DATE", "/", "#NUM", "#NUM", "#NUM");
        addRule(rule1);
        Rule rule2 = new Rule("#DATE", "-", "#NUM", "#NUM", "#NUM");
        addRule(rule2);
        Rule rule3 = new Rule("#DATE", ".", "#NUM", "#NUM", "#NUM");
        addRule(rule3);
        Rule rule4 = new Rule("#DATE", " ", "#MONTH", "#ORD", "#NUM", "#TIME");
        addRule(rule4);
        Rule rule5 = new Rule("#DATE", " ", "#NUM", "#MONTH", "#NUM", "#TIME");
        addRule(rule5);
        Rule rule6 = new Rule("#MONTH_YEAR", " ", "#MONTH", "#NUM");
        addRule(rule6);
    }

}
