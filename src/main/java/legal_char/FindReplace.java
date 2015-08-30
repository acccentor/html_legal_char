package legal_char;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by yui on 23/12/2014.
 */
public class FindReplace {

    List<List<String>> replaceCharsList;

    public FindReplace(String replaceCharsPath){
        updateReplaceCharList(replaceCharsPath);
	}

	public void replaceChars(File file){
//        String content = null; // file here?
//        try {
//            content = FileUtils.readFileToString(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (List<String> replacePar : replaceChars){
//                content = content.replace(replacePar.get(0), replacePar.get(1));
//        }
//        try {
//            FileUtils.writeStringToFile(file, content);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        List<String> fileAsString = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                for (List<String> replacePar : replaceCharsList) {
                    line = line.replaceAll(replacePar.get(0), replacePar.get(1));
                }
                fileAsString.add(line);
            }
//            System.out.println(fileAsString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (String inputLine : fileAsString){
            try{
//                System.out.println(inputLine);
                writer.write(inputLine);
                writer.newLine();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void updateReplaceCharList(String replaceCharsPath){
		 List<List<String>> tempReplaceChars = new ArrayList<List<String>>();
		 List<String> tempReplacePar;
		 try {
//            BufferedReader br = new BufferedReader(new FileReader(replaceCharsPath));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(replaceCharsPath), "UTF-8"));
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (!line.equals("")){
					tempReplacePar = Arrays.asList(line.split("\\s+"));
					tempReplaceChars.add(tempReplacePar);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        replaceCharsList = tempReplaceChars;
    }

    public static void main(String args[]){
//        String test = "hei–tralal–";
//        String replace = "–";
//        System.out.println(test.replace(replace, "_"));
//        FindReplace a = new FindReplace("src/main/resources/legal_chars.txt");
//        a.replaceChars(new File("test.txt"));
    }
}
