package it.enricocandino.tagminer.combiner;

import it.enricocandino.model.TaggedSentence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Enrico Candino
 */
public class TagCombiner {

    private List<Rule> rules;

    public void addRule(Rule rule) {
        if(rules == null)
            rules = new ArrayList<Rule>();
        rules.add(rule);
    }

    public List<TaggedSentence> applyRules(List<TaggedSentence> taggedSentences) {
        for(TaggedSentence taggedSentence : taggedSentences) {
            taggedSentence = applyRules(taggedSentence);
        }
        return taggedSentences;
    }

    public TaggedSentence applyRules(TaggedSentence taggedSentence) {
        for(Rule rule : rules) {
            taggedSentence = applyRule(rule, taggedSentence);
        }
        return taggedSentence;
    }

    public TaggedSentence applyRule(Rule rule, TaggedSentence taggedSentence) {
        String regex = rule.getRegex();

        Matcher matcher = Pattern.compile(regex).matcher(taggedSentence.getTaggedSentence());

        while(matcher.find()) {
            Matcher offsetMatcher = Pattern.compile(regex).matcher(taggedSentence.getTaggedSentence());
            offsetMatcher.find();
            String valueToReplace = offsetMatcher.group();

            String value = "";
            for(String matchingTag : rule.getMatchingTags()) {

                // find the matching
                int index = 0;
                int count = 0;
                int offset = regex.indexOf(matchingTag) -2; // needed for the escape of \Q !!!

                Matcher tagMatcher = Pattern.compile(matchingTag+"\\b").matcher(taggedSentence.getTaggedSentence());
                while(tagMatcher.find()) {
                    if((offsetMatcher.start()+offset) == tagMatcher.start()) {
                        index = count;
                    }
                    count++;
                }

                if(!value.equals(""))
                    value += rule.getSeparator();
                value += taggedSentence.getTagValuesMap().get(matchingTag).get(index);

                taggedSentence.getTagValuesMap().get(matchingTag).remove(index);
            }

            value = value.trim();
            taggedSentence.setTaggedSentence(taggedSentence.getTaggedSentence().replaceFirst(valueToReplace, rule.getTag()));

            List<String> values = taggedSentence.getTagValuesMap().get(rule.getTag());
            if (values == null)
                values = new ArrayList<String>();
            values.add(value);
            taggedSentence.getTagValuesMap().put(rule.getTag(), values);
        }

        return taggedSentence;
    }
}
