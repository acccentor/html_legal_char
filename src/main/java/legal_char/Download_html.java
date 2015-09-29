package legal_char;

import java.io.*;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by yui on 24/01/2015.
 */
public class Download_html {

    public static File download_html(String path, String urlString) throws Exception {


        URL url = new URL(urlString);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
//        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "Cp1252"));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));

        String inputLine;
        while ((inputLine = in.readLine()) != null){
            try{
                writer.write(inputLine);
            }
            catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }
        in.close();
        writer.close();

        File file = new File(path);
        return file;
    }

    public static void main(String args[]){
        try {
            Download_html.download_html("D:/google_drive/Dropbox/books/LN/test_project/test_ch-002.html", "https://shinsoritranslations.wordpress.com/2015/07/23/logged-into-a-different-world/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
