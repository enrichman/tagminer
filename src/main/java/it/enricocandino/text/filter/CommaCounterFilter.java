package it.enricocandino.text.filter;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Enrico Candino
 */
public class CommaCounterFilter extends BaseFilter {

    private static final double COMMA_THRESHOLD = 0.02; // 1/50

    public String filter(String sentence) {
        String[] words = getWords(sentence);

        double commas = StringUtils.countMatches(sentence, ", ");
        if ((commas/(words.length)) < COMMA_THRESHOLD) {
            return null;
        }

        return sentence;
    }
}
