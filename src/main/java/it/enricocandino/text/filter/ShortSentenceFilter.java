package it.enricocandino.text.filter;

/**
 * @author Enrico Candino
 */
public class ShortSentenceFilter extends BaseFilter {

    private static final int WORD_THRESHOLD = 2;

    public String filter(String sentence) {
        String[] words = getWords(sentence);

        // avoid sentence of less than WORD_TRESHOLD
        if(words.length < WORD_THRESHOLD)
            return null;

        return sentence;
    }
}
