package legal_char;

public class LNEntry{
    private String name;
    private String path;
    private String nextChapter;

    public LNEntry(String name, String path, String nextChapter){
        this.name = name;
        this.path = path;
        this.nextChapter = nextChapter;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNextChapter() {
        return nextChapter;
    }

    public void setNextChapter(String nextChapter) {
        this.nextChapter = nextChapter;
    }
}
