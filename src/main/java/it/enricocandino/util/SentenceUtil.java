package it.enricocandino.util;

import com.ibm.icu.text.BreakIterator;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Enrico Candino
 */
public class SentenceUtil {

    public static final String WHITESPACE_REGEX = "\\s+|\\xA0";

    private static final int WORD_THRESHOLD             = 2;
    private static final double COMMA_THRESHOLD         = 0.05;
    private static final double CAPITALIZE_THRESHOLD    = 0.35;
    private static final double DIGIT_THRESHOLD         = 0.2;


    public static List<String> split(String text) {
        List<String> sentences1 = new ArrayList<String>();

        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {

            sentences1.add(text.substring(start, end));
        }

        List<String> sentences2 = new ArrayList<String>();

        for(String sentence : sentences1) {
            String[] splitted = sentence.split("\\|");
            for (String split : splitted) {
                sentences2.add(split.trim());
            }
        }


        return sentences2;
    }

    public static String[] getWords(String sentence) {
        return sentence.split(WHITESPACE_REGEX);
    }

    public static List<String> clean(List<String> sentences) {
        List<String> cleaned = new ArrayList<String>();

        for(String sentence : sentences) {

            String[] words = getWords(sentence);

            // avoid sentence of less than WORD_TRESHOLD
            if(words.length < WORD_THRESHOLD)
                break;

            // sentence of more than LENGTH_TRESHOLD should have a comma!
            if(hasTooFewCommas(sentence, words))
                break;

            // if there are too many uppercase on starting it's probably noise
            if(hasTooManyFirstCapitals(words))
                break;

            // if there are too many digits it's probably noise
            if(hasTooManyDigits(words))
                break;

            cleaned.add(sentence);
        }

        return cleaned;
    }

    private static boolean hasTooFewCommas(String sentence, String[] words) {
        double commas = StringUtils.countMatches(sentence, ", ");
        return (commas/(words.length)) > COMMA_THRESHOLD;
    }

    private static boolean hasTooManyFirstCapitals(String[] words) {
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

        return (upper/(upper+nonUpeer)) > CAPITALIZE_THRESHOLD;
    }

    private static boolean hasTooManyDigits(String[] words) {
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

        return (digits/(digits+nonDigits)) > DIGIT_THRESHOLD;
    }

}
