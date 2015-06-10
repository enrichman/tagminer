package it.enricocandino.tagminer.miner;

import it.enricocandino.model.TaggedSentence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Enrico Candino
 */
public class DayTagMiner extends BaseTagMiner {

    private static final String TAG = "#DAY";

    private static final String REGEX_DAY_FULL = "\\b(monday|tuesday|wednesday|thursday|friday|saturday|sunday)\\b";
    private static final String REGEX_DAY_SHORT = "\\b(mon|tue|wed|thu|fri|sat|sun)\\b";

    private static final String REGEX = "("+ REGEX_DAY_FULL +"|"+ REGEX_DAY_SHORT +")";

    public TaggedSentence mine(TaggedSentence taggedSentence) {
        String sentence = taggedSentence.getTaggedSentence();

        Matcher matcher;
        matcher = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE).matcher(sentence);
        if (matcher.find()) {
            sentence = sentence.replaceFirst("(?i)"+REGEX, TAG);
            taggedSentence.setTaggedSentence(sentence);
            taggedSentence = setValue(matcher.group(), taggedSentence);

            mine(taggedSentence);
        }

        return taggedSentence;
    }

    private TaggedSentence setValue(String value, TaggedSentence taggedSentence) {
        List<String> values = taggedSentence.getTagValuesMap().get(TAG);
        if (values == null)
            values = new ArrayList<String>();
        values.add(value);
        taggedSentence.getTagValuesMap().put(TAG, values);

        return taggedSentence;
    }
}
