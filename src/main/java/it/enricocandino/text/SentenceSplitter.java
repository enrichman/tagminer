package it.enricocandino.text;

import com.ibm.icu.text.BreakIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Enrico Candino
 */
public class SentenceSplitter {

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
}
