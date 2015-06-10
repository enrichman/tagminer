package it.enricocandino.tagminer.miner;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.util.SentenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enrico Candino
 */
public class EmailTagMiner extends BaseTagMiner {

    private static final String TAG = "#EMAIL";

    public TaggedSentence mine(TaggedSentence taggedSentence) {

        String sentence = taggedSentence.getTaggedSentence();
        String[] words = SentenceUtil.getWords(sentence);
        for (String w : words) {
            w = w.trim().replaceAll("\\[", "").replaceAll("\\]", "");

            if (TLD.INSTANCE.isEmail(w)) {

                String escaped = "\\Q"+w;

                sentence = sentence.replaceFirst(escaped, TAG);
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
