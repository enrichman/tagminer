package it.enricocandino.text.csv;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enrico on 10/06/15.
 */
public class CsvWriterTest {

    @Test
    public void testWriteLine() throws Exception {
        CsvWriter writer = new CsvWriter();
        List<String> lines = new ArrayList<String>();
        lines.add("line1");
        lines.add("line2");
        writer.writeCsv("test.csv", lines);
    }
}