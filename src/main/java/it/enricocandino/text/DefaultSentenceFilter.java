package it.enricocandino.text;

import it.enricocandino.text.filter.CapitalCounterFilter;
import it.enricocandino.text.filter.CommaCounterFilter;
import it.enricocandino.text.filter.DigitCounterFilter;
import it.enricocandino.text.filter.ShortSentenceFilter;

/**
 * @author Enrico Candino
 */
public class DefaultSentenceFilter extends SentenceFilter {

    public DefaultSentenceFilter() {
        addFilter(new ShortSentenceFilter());
        addFilter(new CommaCounterFilter());
        addFilter(new CapitalCounterFilter());
        addFilter(new DigitCounterFilter());
    }
}
