package it.enricocandino;

import it.enricocandino.extractor.reader.ClueWebReader;
import it.enricocandino.text.csv.CsvWriter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Enrico Candino
 */
public class TagMinerMain {

    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.print("ERROR: missing arguments with the warc paths");
            return;
        }

        System.out.println("Start Tag Mining");
        long start = System.currentTimeMillis();

        CsvWriter writer = new CsvWriter("out1", "out2", "out3");
        ExecutorService pool = Executors.newFixedThreadPool(20);
        ClueWebReader reader = new ClueWebReader(pool, writer);

        // Read the warc files
        try {
            for (String path : args) {
                reader.read(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pool.shutdown();

        while(!pool.isTerminated()) {
            // still working
        }

        System.out.println("End Tag Mining in "+(System.currentTimeMillis()-start));
    }

}
