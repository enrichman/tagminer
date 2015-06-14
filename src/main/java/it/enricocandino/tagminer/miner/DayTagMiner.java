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
    private static final String REGEX_DAY_SHORT = "\\b(mon|tue|wed|thu|fri|sat)\\b";
    private static final String REGEX_SUN = "\\bSun\\b";

    private static final String REGEX = "("+ REGEX_DAY_FULL +"|"+ REGEX_DAY_SHORT +")";

    public TaggedSentence mine(TaggedSentence taggedSentence) {
        String sentence = taggedSentence.getTaggedSentence();

        Matcher matcher;
        matcher = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE).matcher(sentence);
        while (matcher.find()) {
            sentence = sentence.replaceFirst("(?i)"+REGEX, TAG);
            taggedSentence.setTaggedSentence(sentence);
            taggedSentence = setValue(TAG, matcher.group(), taggedSentence);
        }

        // Sunday need to be checked in a case sensitive way
        matcher = Pattern.compile(REGEX_SUN).matcher(sentence);
        while (matcher.find()) {
            sentence = sentence.replaceFirst(REGEX_SUN, TAG);
            taggedSentence.setTaggedSentence(sentence);
            taggedSentence = setValue(TAG, matcher.group(), taggedSentence);
        }

        return taggedSentence;
    }
}
