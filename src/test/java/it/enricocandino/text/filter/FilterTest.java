package it.enricocandino.text.filter;

import it.enricocandino.text.SentenceFilter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Enrico Candino
 */
public class FilterTest {

    @Test
    public void emptyFilter() {
        String original = "a random sentence";

        SentenceFilter filter = new SentenceFilter();
        String result = filter.doFilter(original);

        assertEquals(result, original);
    }

    @Test
    public void shortSentenceFilter() {
        String tooShort = "oneword";
        String longEnough = "short sentence";

        SentenceFilter filter = new SentenceFilter();
        filter.addFilter(new ShortSentenceFilter());
        String result1 = filter.doFilter(tooShort);
        String result2 = filter.doFilter(longEnough);

        assertEquals(null, result1);
        assertEquals(longEnough, result2);
    }

}
