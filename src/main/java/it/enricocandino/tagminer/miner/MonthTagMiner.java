package it.enricocandino.tagminer.miner;

import it.enricocandino.model.TaggedSentence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Enrico Candino
 */
public class MonthTagMiner extends BaseTagMiner {

    private static final String TAG = "#MONTH";

    private static final String REGEX_MONTH_FULL = "\\b(january|february|march|april|june|july|august|september|october|november|december)\\b";
    private static final String REGEX_MONTH_SHORT ="\\b(jan|feb|mar|apr|jun|jul|aug|sep|oct|nov|dec)\\b";
    private static final String REGEX_MAY ="\\bMay\\b";

    private static final String REGEX = "("+ REGEX_MONTH_FULL +"|"+ REGEX_MONTH_SHORT +")";

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

        // May need to be checked in a case sensitive way!
        matcher = Pattern.compile(REGEX_MAY).matcher(sentence);
        if (matcher.find()) {
            sentence = sentence.replaceFirst(REGEX_MAY, TAG);
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
