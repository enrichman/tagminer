package it.enricocandino.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Enrico Candino
 */
public class TaggedSentence {

    private String taggedSentence;
    private Map<String, String> valueMap;

    public TaggedSentence() {
        valueMap = new HashMap<String, String>();
    }

    public String getTaggedSentence() {
        return taggedSentence;
    }

    public void setTaggedSentence(String taggedSentence) {
        this.taggedSentence = taggedSentence;
    }

    public Map<String, String> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, String> valueMap) {
        this.valueMap = valueMap;
    }
}
