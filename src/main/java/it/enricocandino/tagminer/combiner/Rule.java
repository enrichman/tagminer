package it.enricocandino.tagminer.combiner;

/**
 * @author Enrico Candino
 */
public class Rule {

    private String tag;
    private String[] matchingTags;
    private String separator = " ";

    private String regex = "";

    public Rule(String tag, String separator, String... matchingTags) {
        this.tag = tag;
        this.separator = separator;
        this.matchingTags = matchingTags;

        for(int i=0; i<matchingTags.length; i++) {
            if(i==0)
                regex = matchingTags[i];
            else
                regex += separator + matchingTags[i];
        }
    }

    public String getRegex() {
        return regex;
    }

    public String getTag() {
        return tag;
    }

    public String[] getMatchingTags() {
        return matchingTags;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
