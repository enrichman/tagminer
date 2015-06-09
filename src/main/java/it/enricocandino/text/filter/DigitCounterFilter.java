package it.enricocandino.text.filter;

/**
 * @author Enrico Candino
 */
public class DigitCounterFilter extends BaseFilter {

    private static final double DIGIT_THRESHOLD = 0.2;

    public String filter(String sentence) {
        String[] words = getWords(sentence);

        double nonDigits = 0;
        double digits = 0;

        for(String w : words) {
            w = w.trim();
            if(w.length() > 0) {
                char first = w.charAt(0);
                if (Character.isDigit(first))
                    digits++;
                else
                    nonDigits++;
            }
        }

        if ((digits/(digits+nonDigits)) > DIGIT_THRESHOLD)
            return null;

        return sentence;
    }
}
