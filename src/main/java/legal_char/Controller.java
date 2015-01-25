package legal_char;

import java.io.*;
import java.util.Scanner;

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
            String path = defaultPath + new Scanner(System.in).nextLine() + ".html";
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
    }
}
