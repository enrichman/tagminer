package it.enricocandino.tagminer.miner;

import it.enricocandino.model.TaggedSentence;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Enrico Candino
 */
public class UrlTagMiner extends BaseTagMiner {

    private static final String TAG = "#URL";

    private static final String REGEX = "(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w\\?\\=\\&\\.-]*)*\\/?";

    public TaggedSentence mine(TaggedSentence taggedSentence) {

        String sentence = taggedSentence.getTaggedSentence();

        Matcher matcher;
        matcher = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE).matcher(sentence);
        if (matcher.find()) {

            String url = matcher.group();

            if (TLD.INSTANCE.isUrl(url)) {

                sentence = sentence.replaceFirst("(?i)"+REGEX, TAG);
                taggedSentence.setTaggedSentence(sentence);
                taggedSentence = setValue(TAG, url, taggedSentence);

                mine(taggedSentence);
            }
        }

        return taggedSentence;
    }
}
