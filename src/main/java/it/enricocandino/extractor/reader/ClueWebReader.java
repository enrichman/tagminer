package it.enricocandino.extractor.reader;

import clueweb09.WarcHTMLResponseRecord;
import clueweb09.WarcRecord;
import it.enricocandino.model.Page;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;


public class ClueWebReader {

    public List<Page> read(String path) throws Exception {
        long start = System.currentTimeMillis();

        GZIPInputStream gzInputStream = new GZIPInputStream(new FileInputStream(path));
        DataInputStream inStream = new DataInputStream(gzInputStream);

        List<Page> pages = new ArrayList<Page>(50000);

        WarcRecord thisWarcRecord;
        int count = 1;

        while ((thisWarcRecord = WarcRecord.readNextWarcRecord(inStream)) != null) {
            if (thisWarcRecord.getHeaderRecordType().equals("response")) {
                WarcHTMLResponseRecord htmlRecord = new WarcHTMLResponseRecord(thisWarcRecord);
                String url = htmlRecord.getTargetURI();

                if(count%100 == 0)
                    System.out.println("["+count+"] " + url);

                String rawResponse = new String(htmlRecord.getRawRecord().getByteContent(), "iso-8859-1");

                boolean isTextFile = false;
                boolean headerRead = false;

                StringBuilder responseBuilder = new StringBuilder(htmlRecord.getRawRecord().getTotalRecordLength());

                Scanner scanner = new Scanner(rawResponse);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("Content-Type:") && line.contains("text")) {
                        isTextFile = true;
                    }

                    // header end: check if is text file or is to skip
                    if (line.equals("") || headerRead) {
                        headerRead = true;
                        if (!isTextFile) {
                            break;
                        } else {
                            responseBuilder.append(line);
                        }
                    }
                }
                scanner.close();

                String responseBody = responseBuilder.toString();

                responseBody = responseBody.trim();
                if (responseBody.length() > 0) {

                    /*
                    Document doc = Jsoup.parse(responseBody);
                    Elements titleElem = doc.select("title");
                    String title = "";
                    if (titleElem != null && titleElem.size() > 0)
                        title = titleElem.get(0).text();

                    String bodyText = "";
                    if (doc.body() != null)
                        bodyText = doc.body().text();
                        */

                    Page page = new Page();
                    page.setWarcID(htmlRecord.getRawRecord().getHeaderMetadataItem("WARC-Record-ID"));
                    page.setHtml(responseBody);
                    pages.add(page);
                }

                count++;

                if(count>1000)
                    break;
            }
        }

        inStream.close();

        System.out.println("Finish read in "+(System.currentTimeMillis()-start));

        return pages;
    }

}
