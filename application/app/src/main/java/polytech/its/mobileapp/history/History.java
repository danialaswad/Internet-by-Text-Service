package polytech.its.mobileapp.history;

/**
 * @author: Abdelkarim Andolerzak
 */

public class History {
    String nameFile;
    String content;

    public History(String name, String content) {
        nameFile = name;
        this.content = content;
    }

    public String getNameFile() {
        return nameFile;
    }

    public String getContent() {
        return content;
    }

}
