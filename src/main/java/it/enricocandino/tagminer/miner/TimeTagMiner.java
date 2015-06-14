package it.enricocandino.tagminer.miner;

import it.enricocandino.model.TaggedSentence;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Enrico Candino
 */
public class TimeTagMiner extends BaseTagMiner {

    private static final String TAG = "#TIME";

    private static final String REGEX_HHMMSS = "\\b(([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])(\\s?am|\\s?pm|\\b)\\b";
    private static final String REGEX_HHMM_AMPM = "\\b(([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9])(\\s?am|\\s?pm|\\b)\\b";
    private static final String REGEX_H_M_S = "[0-2][0-9]h\\s[0-5][0-9]m\\s[0-5][0-9]s";
    private static final String REGEX_M_S = "[0-5][0-9]m\\s[0-5][0-9]s";

    private static final String REGEX = "("+REGEX_HHMMSS+"|"+REGEX_HHMM_AMPM+"|"+REGEX_H_M_S+"|"+REGEX_M_S+")";

    public TaggedSentence mine(TaggedSentence taggedSentence) {

        String sentence = taggedSentence.getTaggedSentence();

        Matcher matcher;
        matcher = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE).matcher(sentence);
        if (matcher.find()) {
            sentence = sentence.replaceFirst("(?i)"+REGEX, TAG);
            taggedSentence.setTaggedSentence(sentence);
            taggedSentence = setValue(TAG, matcher.group(), taggedSentence);

            mine(taggedSentence);
        }

        return taggedSentence;
    }
}
