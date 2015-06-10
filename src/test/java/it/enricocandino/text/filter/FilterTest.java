package it.enricocandino.text.filter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Enrico Candino
 */
public class FilterTest {

    @Test
    public void shortSentenceFilter() {
        String tooShort = "oneword";
        String longEnough = "short sentence";

        Filter filter = new ShortSentenceFilter();
        String result1 = filter.filter(tooShort);
        String result2 = filter.filter(longEnough);

        assertEquals(null, result1);
        assertEquals(longEnough, result2);
    }

}
