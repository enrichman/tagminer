package it.enricocandino.tagminer.miner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Enrico Candino
 */
public enum TLD {

    INSTANCE;

    private List<String> tldList;

    TLD() {
        loadTldFile();
    }

    private void loadTldFile() {
        tldList = new ArrayList<String>();

        BufferedReader br = null;
        try {

            br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/tld.txt")));
            String line = br.readLine();

            while (line != null) {
                tldList.add(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public boolean isUrl(String url) {
        url = url.toUpperCase();

        if (!url.contains("..")) {
            for (String tld : tldList) {

                if (url.endsWith("." + tld)
                        || url.contains("." + tld + "/")
                        || url.contains("." + tld + "?")) {


                    if (!url.toLowerCase().matches("^\\w+://.*") && !url.toLowerCase().contains("http")) {
                        url = "http://" + url;
                    }
                    try {
                        new URL(url);
                    } catch (MalformedURLException e) {
                        return false;
                    }

                    return !url.contains("@");
                }
            }
        }
        return false;
    }

    public boolean isEmail(String word) {
        word = word.toUpperCase();
        for (String tld : tldList) {
            if (word.endsWith("." + tld)) {
                return word.contains("@");
            }
        }
        return false;
    }
}
