package legal_char;

import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by yui on 11/12/2015.
 */
public class JsonDatabase {
    JSONObject db;
    ArrayList<LNEntry> entries;

    public JsonDatabase(){
        db = new JSONObject();
        entries = new ArrayList<LNEntry>();
    }

    public void addEntry(LNEntry entry){
        if (!entries.contains(entry)){
            entries.add(entry);
            db.put(entry.getName(), new JSONObject(entry));
        }

    }

    public void editEntry(LNEntry entry){
        entries.add(entry);
        db.put(entry.getName(), new JSONObject(entry));
    }

    public String searchKeys(String search){
        return "not implemented";
    }

    public LNEntry getEntry(String key){
        if (db.has(key)){
            return parseJSON((JSONObject) db.get(key));
        }
        else return null;
    }

    public LNEntry parseJSON(JSONObject JSONEntry) {
        String name = (String)JSONEntry.get("name");
        String path = (String)JSONEntry.get("path");
        String currentChapter = (String)JSONEntry.get("currentChapter");
        LNEntry entry = new LNEntry(name, path, currentChapter);
        return  entry;
    }

    public String toString(){
        return db.toString();
    }

    public void writeToFile(){
        try {
            String path = "src/main/resources/db.txt";
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
            db.write(writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject readDBFile(){
        JSONObject returnValue = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/db.txt"), "UTF-8"));
            returnValue = new JSONObject(in.readLine());
            in.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public static void main(String args[]){
        JsonDatabase a = new JsonDatabase();
        LNEntry b = new LNEntry("test", "testPath", "testCH");
        LNEntry c = new LNEntry("test2", "testPath2", "testCH2");
        a.addEntry(b);
        a.addEntry(c);
        a.writeToFile();
        System.out.println(a.readDBFile().toString());
    }
}

