package legal_char;

import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yui on 11/12/2015.
 */
public class JsonDatabase {
    JSONObject db;

    public JsonDatabase(){
        db = new JSONObject();
        readDBFile();
    }

    public void addEntry(LNEntry entry){
        if (!db.has(entry.getName())){
            db.put(entry.getName(), new JSONObject(entry));
        }

    }

    public void editEntry(LNEntry entry){
        db.put(entry.getName(), new JSONObject(entry));
    }

    public ArrayList<String> searchKeys(String search){
        ArrayList<String> returnList = new ArrayList<String>();
        Iterator<String> keys = db.keys();
        while (keys.hasNext()){
            String key = keys.next();
            if (key.contains(search)){
                returnList.add(key);
            }
        }
        return returnList;
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

    public void readDBFile(){
        this.db = returnReadDBFile();
    }

    public JSONObject returnReadDBFile(){
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
    }
}

