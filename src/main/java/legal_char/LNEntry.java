package legal_char;

public class LNEntry{
    private String name;
    private String path;
    private String currentChapter;

    public LNEntry(String name, String path, String currentChapter){
        this.name = name;
        this.path = path;
        this.currentChapter = currentChapter;
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

    public String getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(String currentChapter) {
        this.currentChapter = currentChapter;
    }
}
