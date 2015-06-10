package it.enricocandino.tagminer.miner;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.util.SentenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enrico Candino
 */
public class OrdinalTagMiner extends BaseTagMiner {

    private static final String TAG     = "#ORD";
    private static final String REGEX   = "\\b\\d+(st|nd|rd|th)\\b";

    public TaggedSentence mine(TaggedSentence taggedSentence) {

        String sentence = taggedSentence.getTaggedSentence();
        String[] words = SentenceUtil.getWords(sentence);
        for (String w : words) {
            w = w.trim().replaceAll("\\[", "").replaceAll("\\]", "");

            if (w.matches(REGEX)) {

                sentence = sentence.replaceFirst(w, TAG);
                taggedSentence.setTaggedSentence(sentence);

                List<String> values = taggedSentence.getTagValuesMap().get(TAG);
                if(values == null)
                    values = new ArrayList<String>();
                values.add(w);
                taggedSentence.getTagValuesMap().put(TAG, values);
            }
        }

        return taggedSentence;
    }
}
