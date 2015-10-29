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
    private boolean autoDL = true;
    private boolean autoPath = true;
    private int autoDLNr = -1;

    public Controller() {

        askForAuto();

        replace = new FindReplace("src/main/resources/legal_chars.txt");

        System.out.println("default path?");
        String defaultPath = new Scanner(System.in).nextLine();
        String path;
        String url;


        while(true){
            path = generatePath(defaultPath);
            url = generateUrl();

            try {
                File file = Download_html.download_html(path, url);
                replace.replaceChars(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private String generateUrl() {
        String url = "";
        if (this.autoDL){
            url = auto_dl();
        }
        else{
            url = manual_dl();
        }

        return url;
    }

    private String generatePath(String defaultPath) {
        String path = "";
        if (this.autoPath){
            path = auto_path(defaultPath);
        }
        else{
            path = manual_path(defaultPath);
        }


        return path;
    }

    private void askForAuto() {
        String input;
        System.out.print("Assisted path? (Y/n): ");
        input = new Scanner(System.in).nextLine();
        if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")){
            this.autoPath = false;
        }

        System.out.print("Assisted download? (Y/n): ");
        input = new Scanner(System.in).nextLine();
        if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")){
            this.autoDL = false;
        }
    }

    private String auto_dl(){

        return null;
    }

    private String auto_path(String defaultPath){
        String path = "";
        if (this.autoDLNr < 0){
            String input;
            System.out.println("First chapter number? (Y/n):");
            input = new Scanner(System.in).nextLine();
            this.autoDLNr = Integer.parseInt(input);
        }

        path = defaultPath + String.format("%03d", this.autoDLNr) + ".html";
        this.autoDLNr += 1;

        return path;
    }

    private String manual_dl(){
        System.out.println("url?");
        return new Scanner(System.in).nextLine();
    }

    private String manual_path(String defaultPath){
        System.out.println("path?");
        String path = defaultPath + new Scanner(System.in).nextLine();
//        List<String> pathList = Arrays.asList(path.split(Pattern.quote("/")));
//        String title = pathList.get(pathList.size() - 1);
        path += ".html";
        return path;
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
