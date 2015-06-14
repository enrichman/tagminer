package it.enricocandino.text.csv;

import it.enricocandino.model.TaggedSentence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Enrico Candino
 */
public class CsvWriter {

    private String time;
    private String outDirectory;

    private String filename1;
    private String filename2;
    private String filename3;

    public CsvWriter(String outDirectory, String tagValueFilename, String taggedSentenceFilename, String originalSentenceFilename) {
        this.outDirectory = outDirectory;
        this.time = new SimpleDateFormat("yyyyMMdd_HHmm_").format(new Date());

        this.filename1 = tagValueFilename;
        this.filename2 = taggedSentenceFilename;
        this.filename3 = originalSentenceFilename;
    }

    public CsvWriter(String tagValueFilename, String taggedSentenceFilename, String originalSentenceFilename) {
        this("out", tagValueFilename, taggedSentenceFilename, originalSentenceFilename);
    }

    public void write(List<TaggedSentence> taggedSentences) {

        List<String> firstCSV = getFirstCsvLines(taggedSentences);
        writeCsv(filename1, firstCSV);

        List<String> secondCSV = getSecondCsvLines(taggedSentences);
        writeCsv(filename2, secondCSV);

        List<String> thirdCSV = getThirdCsvLines(taggedSentences);
        writeCsv(filename3, thirdCSV);
    }

    private List<String> getFirstCsvLines(List<TaggedSentence> taggedSentences) {
        List<String> lines = new ArrayList<String>();
        for (TaggedSentence sentence : taggedSentences) {
            for (String tag : sentence.getTagValuesMap().keySet()) {
                List<String> values = sentence.getTagValuesMap().get(tag);
                for (String val : values) {
                    lines.add(sentence.getWarcId() + "\t" + val + "\t" + tag);
                }
            }
        }
        return lines;
    }

    private List<String> getSecondCsvLines(List<TaggedSentence> taggedSentences) {
        List<String> lines = new ArrayList<String>();
        for (TaggedSentence sentence : taggedSentences) {
            lines.add(sentence.getWarcId() + "\t" + sentence.getOriginalSentence());
        }
        return lines;
    }

    private List<String> getThirdCsvLines(List<TaggedSentence> taggedSentences) {
        List<String> lines = new ArrayList<String>();
        for (TaggedSentence sentence : taggedSentences) {
            lines.add(sentence.getWarcId() + "\t" + sentence.getTaggedSentence());
        }
        return lines;
    }

    public void writeCsv(String filename, List<String> lines) {
        File outDir = new File(outDirectory);
        if (!outDir.exists())
            outDir.mkdir();

        PrintWriter writer = null;
        try {
            String path = outDirectory + "/" + time + filename;
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
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
