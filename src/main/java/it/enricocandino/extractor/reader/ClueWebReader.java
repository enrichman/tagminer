package it.enricocandino.extractor.reader;

import clueweb09.WarcHTMLResponseRecord;
import clueweb09.WarcRecord;
import it.enricocandino.Worker;
import it.enricocandino.model.Page;
import it.enricocandino.text.csv.CsvWriter;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.zip.GZIPInputStream;


public class ClueWebReader {

    private ExecutorService pool;
    private CsvWriter writer;

    public ClueWebReader(ExecutorService pool, CsvWriter writer) {
        this.pool = pool;
        this.writer = writer;
    }

    public void read(String path) throws Exception {

        System.out.println("Start reading WARC file [ " + path + " ]");
        long start = System.currentTimeMillis();

        GZIPInputStream gzInputStream = new GZIPInputStream(new FileInputStream(path));
        DataInputStream inStream = new DataInputStream(new BufferedInputStream(gzInputStream));

        WarcRecord thisWarcRecord;
        int count = 1;

        while ((thisWarcRecord = WarcRecord.readNextWarcRecord(inStream)) != null) {
            if (thisWarcRecord.getHeaderRecordType().equals("response")) {

                String url = thisWarcRecord.getHeaderMetadataItem("WARC-Target-URI");
                if (count % 100 == 0)
                    System.out.println("[" + count + "] " + url);

                pool.execute(new Worker(writer, thisWarcRecord));

                count++;
            }
        }

        inStream.close();

        System.out.println("Finish read WARC file in " + (System.currentTimeMillis() - start));
    }

}
