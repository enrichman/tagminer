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

    public TaggedSentence mine(String sentence) {

        TaggedSentence taggedSentence = new TaggedSentence();

        String[] words = SentenceUtil.getWords(sentence);
        int count = 1;
        for (String w : words) {
            w = w.trim();
            if (TLD.INSTANCE.isUrl(w)) {
                String TAG = "#URL"+count;

                sentence = sentence.replaceFirst(w, TAG);

                taggedSentence.setTaggedSentence(sentence);
                taggedSentence.getValueMap().put(TAG, w);

                count++;
            }
        }

        return taggedSentence;
    }
}
