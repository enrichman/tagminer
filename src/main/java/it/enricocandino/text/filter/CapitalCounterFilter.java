package it.enricocandino.text.filter;

/**
 * @author Enrico Candino
 */
public class CapitalCounterFilter extends BaseFilter {

    private static final double CAPITALIZE_THRESHOLD = 0.35;

    public String filter(String sentence) {
        String[] words = getWords(sentence);

        double nonUpeer = 0;
        double upper = 0;

        for(String w : words) {
            w = w.trim();
            if(w.length() > 0) {
                char first = w.charAt(0);
                if(Character.isUpperCase(first))
                    upper++;
                else
                    nonUpeer++;
            }
        }

        if ((upper/(upper+nonUpeer)) > CAPITALIZE_THRESHOLD)
            return null;

        return sentence;
    }
}
