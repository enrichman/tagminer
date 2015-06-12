package it.enricocandino.text.csv;

import it.enricocandino.model.TaggedSentence;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enrico Candino
 */
public class CsvWriter {

    public void write(List<TaggedSentence> taggedSentences) {
        List<String> firstCSV = getFirstCsvLines(taggedSentences);
        writeCsv("out1", firstCSV);

        List<String> secondCSV = getSecondCsvLines(taggedSentences);
        writeCsv("out2", secondCSV);

        List<String> thirdCSV = getThirdCsvLines(taggedSentences);
        writeCsv("out3", thirdCSV);

    }

    private List<String> getFirstCsvLines(List<TaggedSentence> taggedSentences) {
        List<String> lines = new ArrayList<String>();
        for(TaggedSentence sentence : taggedSentences) {
            for(String tag : sentence.getTagValuesMap().keySet()) {
                List<String> values = sentence.getTagValuesMap().get(tag);
                for(String val : values) {
                    lines.add(sentence.getWarcId()+"\t"+val+"\t"+tag);
                }
            }
        }
        return lines;
    }

    private List<String> getSecondCsvLines(List<TaggedSentence> taggedSentences) {
        List<String> lines = new ArrayList<String>();
        for(TaggedSentence sentence : taggedSentences) {
            lines.add(sentence.getWarcId()+"\t"+sentence.getOriginalSentence());
        }
        return lines;
    }

    private List<String> getThirdCsvLines(List<TaggedSentence> taggedSentences) {
        List<String> lines = new ArrayList<String>();
        for(TaggedSentence sentence : taggedSentences) {
            lines.add(sentence.getWarcId()+"\t"+sentence.getTaggedSentence());
        }
        return lines;
    }

    public void writeCsv(String filename, List<String> lines) {
        PrintWriter writer = null;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
            writer = new PrintWriter(bw);
            for (String line : lines) {
                writer.println(line);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
