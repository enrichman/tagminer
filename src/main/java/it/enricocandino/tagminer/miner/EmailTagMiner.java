package it.enricocandino.tagminer.miner;

import it.enricocandino.model.TaggedSentence;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Enrico Candino
 */
public class EmailTagMiner extends BaseTagMiner {

    private static final String TAG = "#EMAIL";
    private static final String REGEX = "\\b([a-z0-9_\\.\\+-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})\\b";

    public TaggedSentence mine(TaggedSentence taggedSentence) {

        String sentence = taggedSentence.getTaggedSentence();

        Matcher matcher;
        matcher = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE).matcher(sentence);
        if (matcher.find()) {

            String email = matcher.group();

            if (TLD.INSTANCE.isEmail(email)) {

                sentence = sentence.replaceFirst("(?i)"+REGEX, TAG);
                taggedSentence.setTaggedSentence(sentence);
                taggedSentence = setValue(TAG, email, taggedSentence);

                mine(taggedSentence);
            }
        }

        return taggedSentence;
    }
}
