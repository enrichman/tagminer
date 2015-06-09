package it.enricocandino.text;

import it.enricocandino.text.filter.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enrico Candino
 */
public class SentenceFilter {

    private List<Filter> filters;

    public void addFilter(Filter filter) {
        if(filters == null)
            filters = new ArrayList<Filter>();
        filters.add(filter);
    }

    public String doFilter(String sentence) {
        String result = sentence;
        if(filters != null) {
            for(Filter filter : filters) {
                result = filter.filter(result);

                if(result == null)
                     break;
            }
        }
        return result;
    }

}
