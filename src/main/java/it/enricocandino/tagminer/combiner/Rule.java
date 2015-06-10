package it.enricocandino.tagminer.combiner;

/**
 * @author Enrico Candino
 */
public class Rule {

    private String tag;
    private String[] matchingTags;

    private String regex = "";

    public Rule(String tag, String... matchingTags) {
        this.tag = tag;
        this.matchingTags = matchingTags;

        for (String t : matchingTags) {
            regex += " " + t;
        }
        regex = regex.trim();
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
}
