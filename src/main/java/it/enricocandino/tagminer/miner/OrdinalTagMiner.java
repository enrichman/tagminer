package it.enricocandino.tagminer.miner;

import it.enricocandino.model.TaggedSentence;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Enrico Candino
 */
public class OrdinalTagMiner extends BaseTagMiner {

    private static final String TAG     = "#ORD";
    private static final String REGEX   = "\\b\\d+(st|nd|rd|th)\\b";

    public TaggedSentence mine(TaggedSentence taggedSentence) {

        String sentence = taggedSentence.getTaggedSentence();

        Matcher matcher;
        matcher = Pattern.compile(REGEX).matcher(sentence);
        while (matcher.find()) {
            sentence = sentence.replaceFirst(REGEX, TAG);
            taggedSentence.setTaggedSentence(sentence);
            taggedSentence = setValue(TAG, matcher.group(), taggedSentence);
        }

        return taggedSentence;
    }
}
