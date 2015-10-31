package legal_char;

import org.apache.commons.lang.StringEscapeUtils;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.util.Scanner;

/**
 * Created by yui on 23/12/2014.
 */
public class Controller {

    private FindReplace replace;
    private boolean autoDL = false;
    private boolean autoPath = true;
    private int autoDLNr = -1;
    private Clipboard c;
    private boolean firstRun;

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
                e.printStackTrace();
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

        System.out.print("Assisted download? (y/N): ");
        input = new Scanner(System.in).nextLine();
        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")){
            this.autoDL = true;
        }
        else{
            c = Toolkit.getDefaultToolkit().getSystemClipboard();
        }
    }

    private String auto_dl(){
        String clipboard_new = " ";
        String clipboard_old = "wertyuiasdfklbcjoidfad";
        this.firstRun = true;
        System.out.println("Listening for chapter " + this.autoDLNr);
        while (true) {
            Transferable t = c.getContents(null);
            if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    clipboard_new = (String) t.getTransferData(DataFlavor.stringFlavor);
//                    clipboard_new = StringEscapeUtils.escapeJava(clipboardTemp);
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }

            if (!firstRun) {
//                System.out.println(clipboard_new + ", " + clipboard_old);
                if (!clipboard_new.equals(clipboard_old)) {
                    return clipboard_new;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                this.firstRun = false;
                clipboard_old = clipboard_new;
            }

        }
    }

    private String auto_path(String defaultPath){
        String path = "";
        if (this.autoDLNr < 0){
            String input;
            System.out.println("First chapter number?:");
            input = new Scanner(System.in).nextLine();
            this.autoDLNr = Integer.parseInt(input);
        }
        else{
            this.autoDLNr ++;
        }

        path = defaultPath + String.format("%03d", this.autoDLNr) + ".html";

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

    static public void main(String[] args){
        new Controller();
    }

}
