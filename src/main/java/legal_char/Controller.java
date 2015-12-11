package legal_char;

import java.awt.*;
import java.awt.datatransfer.*;
import java.util.ArrayList;
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
    private JsonDatabase db = new JsonDatabase();
    private LNEntry currentLN;

    public Controller() {

        currentLN = getDBEntry();
        askForAuto();

//        replace = new FindReplace("src/main/resources/legal_chars.txt");

        String defaultPath = currentLN.getPath();
        String path;
        String url;


        while(true){
            path = generatePath(defaultPath);
            url = generateUrl();

            (new ThreadController(path, url)).run();
            if (autoPath){
                currentLN.setNextChapter(Integer.toString(autoDLNr + 1));
                db.editEntry(currentLN);
                db.writeToFile();
            }
//            replace.replaceChars(file);


        }
    }

    private LNEntry getDBEntry() {
        LNEntry returnEntry = null;
        String input;
        while(returnEntry == null){
            System.out.print("Option (search): ");
            input = new Scanner(System.in).nextLine();
            switch (input){
                case "help":
                    System.out.println("search, list, manual, add");
                    break;
                case "search":
                    returnEntry = useDBSearch();
                    break;
                case "list":
                    System.out.println("not implemented");
                    break;
                case "manual":
                    System.out.println("not implemented");
                    break;
                case "add":
                    addEntryToDB();
                    break;
                default:
                    returnEntry = useDBSearch();
                    break;
            }
        }


        return returnEntry;
    }

    private LNEntry addEntryToDB() {
        System.out.print("name: ");
        String name = new Scanner(System.in).nextLine();
        System.out.println("path: ");
        String path = new Scanner(System.in).nextLine();
        System.out.println("currentChapter: ");
        String currentChapter = new Scanner(System.in).nextLine();
        LNEntry entry = new LNEntry(name, path, currentChapter);
        db.addEntry(entry);
        db.writeToFile();
        return entry;
    }

    private LNEntry useDBSearch() {
        db.readDBFile();
        String input;
        System.out.print("Search word: ");
        input = new Scanner(System.in).nextLine();
        ArrayList<String> result = db.searchKeys(input);
        if (result.size() == 1){
            System.out.println("Found: " + result.get(0));
            return db.getEntry(result.get(0));
        }
        else if (result.size() > 1){
            for (int i = 0; i < result.size(); i++){
                System.out.println(i + ". " + result.get(i));
            }
            System.out.print("Selection? (0) ");
            input = new Scanner(System.in).nextLine();
            try{
                return db.getEntry(result.get(Integer.parseInt(input)));
            } catch (NumberFormatException e) {
                return db.getEntry(result.get(0));
            }
        }
        else{
            System.out.println("Try again");
            return useDBSearch();
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
            c = Toolkit.getDefaultToolkit().getSystemClipboard();
        }
    }

    private String auto_dl(){
        String clipboard_new = " ";
        String clipboard_old = "wertyuiasdfklbcjoidfad";
        this.firstRun = true;
//        System.out.println("Listening for chapter " + this.autoDLNr);
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
                    Thread.sleep(300);
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
            System.out.print("First chapter number? (" + currentLN.getNextChapter() + ")");
            input = new Scanner(System.in).nextLine();
            try{
                this.autoDLNr = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Using default: " + currentLN.getNextChapter());
                this.autoDLNr = Integer.parseInt(currentLN.getNextChapter());
            }
        }
        else{
            this.autoDLNr ++;
            System.out.println("Chapter: " + this.autoDLNr);
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
