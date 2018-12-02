package dto;

public class DVD extends LibraryItem{
    private String languages;

    public DVD(int itemID, String itemName, String type, String languages) {
        super(itemID, itemName, type);
        this.languages = languages;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
