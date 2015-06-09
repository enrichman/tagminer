package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import it.enricocandino.util.SentenceUtil;
import it.enricocandino.util.TLD;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enrico Candino
 */
public class UrlTagMiner implements TagMiner {

    public TaggedSentence mine(TaggedSentence taggedSentence) {

        String sentence = taggedSentence.getTaggedSentence();
        String[] words = SentenceUtil.getWords(sentence);
        for (String w : words) {
            w = w.trim().replaceAll("\\[", "").replaceAll("\\]", "");;
            if (TLD.INSTANCE.isUrl(w)) {
                String TAG = "#URL";

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
