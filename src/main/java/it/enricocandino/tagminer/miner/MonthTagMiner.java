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

    private static final String TAG     = "#MONTH";

    private static final String REGEX_MONTH_FULL =
            "\\bjanuary\\b|\\bfebruary\\b|\\bmarch\\b|\\bapril\\b|\\bjune\\b|\\bjuly\\b|\\baugust\\b|\\bseptember\\b|\\boctober\\b|\\bnovember\\b|\\bdecember\\b";
    private static final String REGEX_MONTH_SHORT =
            "\\bjan\\b|\\bfeb\\b|\\bmar\\b|\\bapr\\b|\\bmay\\b|\\bjun\\b|\\bjul\\b|\\baug\\b|\\bsep\\b|\\boct\\b|\\bnov\\b|\\bdec\\b";

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
