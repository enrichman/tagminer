package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.util.SentenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Enrico Candino
 */
public class TimeTagMiner extends BaseTagMiner {

    private static final String TAG = "#TIME";

    private static final String REGEX_HHMM = "[0-2][0-9]:[0-5][0-9]";
    private static final String REGEX_H_M_S = "[0-2][0-9]h\\s[0-5][0-9]m\\s[0-5][0-9]s";
    private static final String REGEX_M_S = "[0-5][0-9]m\\s[0-5][0-9]s";

    private static final String REGEX = "("+REGEX_HHMM+"|"+REGEX_H_M_S+"|"+REGEX_M_S+")";

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
