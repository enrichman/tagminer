package it.enricocandino.text.filter;

/**
 * @author Enrico Candino
 */
public abstract class BaseFilter implements Filter {

    public static final String WHITESPACE_REGEX = "\\s+|\\xA0";

    public String[] getWords(String sentence) {
        return sentence.split(WHITESPACE_REGEX);
    }
}
