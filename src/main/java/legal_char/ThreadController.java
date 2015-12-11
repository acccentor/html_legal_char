package legal_char;

import java.io.File;

/**
 * Created by yui on 11/12/2015.
 */
public class ThreadController implements Runnable {

    private String path;
    private String urlString;

    public ThreadController(String path, String urlString){
        this.path = path;
        this.urlString = urlString;
    }

    public void run() {
        try {
            Download_html.download_html(path, urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
