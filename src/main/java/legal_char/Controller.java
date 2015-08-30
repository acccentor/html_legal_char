package legal_char;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by yui on 23/12/2014.
 */
public class Controller {

    private FindReplace replace;

    public Controller() {
        replace = new FindReplace("src/main/resources/legal_chars.txt");
        System.out.println("default path?");
        String defaultPath = new Scanner(System.in).nextLine();
        while(true){
            System.out.println("path?");
            String path = defaultPath + new Scanner(System.in).nextLine();
            List<String> pathList = Arrays.asList(path.split(Pattern.quote("/")));
            String title = pathList.get(pathList.size() - 1);
            path += ".html";
            System.out.println("url?");
            String url = new Scanner(System.in).nextLine();



            try {
                File file = Download_html.download_html(path, url);
                replace.replaceChars(file);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public static void main(String args[]){
        new Controller();
        /*
        FindReplace replace;
        replace = new FindReplace("src/main/resources/legal_chars.txt");
        replace.replaceChars(new File("D:/google_drive/Dropbox/books/LN/ID – The Greatest Fusion Fantasy/japtem/id_ch-3.html"));
        */
    }
}
