package legal_char;

import java.io.*;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by yui on 24/01/2015.
 */
public class Download_html {

    public static File download_html(String path, String url) throws Exception {


        URL oracle = new URL(url);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));
//        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "Cp1252"));

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
            Download_html.download_html("outputfile.txt", "http://baka-tsuki.org/project/index.php?title=Mushoku_Tensei:Web_Chapter_229");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
