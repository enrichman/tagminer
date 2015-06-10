package it.enricocandino.tagminer.miner;

import it.enricocandino.model.TaggedSentence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Enrico Candino
 */
public class PhoneTagMiner extends BaseTagMiner {

    private static final String TAG     = "#PHONE";
    private static final String REGEX   = "\\b(\\d\\d?(\\s|-))?(\\d\\d\\d)(\\s|-)(\\d\\d\\d)(\\s|-)(\\d\\d\\d\\d)\\b";

    public TaggedSentence mine(TaggedSentence taggedSentence) {
        String sentence = taggedSentence.getTaggedSentence();

        Matcher matcher;
        matcher = Pattern.compile(REGEX).matcher(sentence);
        if (matcher.find()) {
            sentence = sentence.replaceFirst(REGEX, TAG);
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
