package it.enricocandino.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Enrico Candino
 */
public class TaggedSentence {

    private String warcId;
    private String originalSentence;
    private String taggedSentence;
    private Map<String, List<String>> tagValuesMap;

    public TaggedSentence() {
        tagValuesMap = new HashMap<String, List<String>>();
    }

    public String getWarcId() {
        return warcId;
    }

    public void setWarcId(String warcId) {
        this.warcId = warcId;
    }

    public String getOriginalSentence() {
        return originalSentence;
    }

    public void setOriginalSentence(String originalSentence) {
        this.originalSentence = originalSentence;
    }

    public String getTaggedSentence() {
        return taggedSentence;
    }

    public void setTaggedSentence(String taggedSentence) {
        this.taggedSentence = taggedSentence;
    }

    public Map<String, List<String>> getTagValuesMap() {
        return tagValuesMap;
    }

    public void setTagValuesMap(Map<String, List<String>> tagValuesMap) {
        this.tagValuesMap = tagValuesMap;
    }

    @Override
    public String toString() {
        return "TaggedSentence{" +
                "originalSentence='" + originalSentence + '\'' +
                ", taggedSentence='" + taggedSentence + '\'' +
                ", tagValuesMap=" + tagValuesMap +
                '}';
    }
}
