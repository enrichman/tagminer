package it.enricocandino;

import it.enricocandino.model.Page;
import it.enricocandino.extractor.reader.ClueWebReader;
import it.enricocandino.tagminer.Miner;
import it.enricocandino.util.TLD;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Enrico Candino
 */
public class Extractor {

    private static final String BASE_FOLDER = "/Users/enrico/Documents/UNI-MAGISTRALE/Analisi e Gestione dell'informazione su Web/warc/";

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(8);

        long startBatch = System.currentTimeMillis();
        long start = System.currentTimeMillis();

        try {

            ClueWebReader reader = new ClueWebReader();

            for (int i = 0; i < 1; i++) {

                String path = BASE_FOLDER + "0" + i + ".warc.gz";

                System.out.println("Start reading from: "+path);
                List<Page> pages = reader.read(path);


                start = System.currentTimeMillis();
                System.out.println("Start mining");

                Miner miner = new Miner();
                for(Page p : pages) {
                    miner.mine(p);
                }

            }

            executor.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }

        while (!executor.isTerminated()) {}

        System.out.println("Finish indexing in " + (System.currentTimeMillis() - start));
        System.out.println("Finish batch in " + (System.currentTimeMillis() - startBatch));

        System.out.println("** Completed! **");

    }

    // chops a list into non-view sublists of length L
    static <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                            list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

}
