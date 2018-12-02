package dto;

import java.util.List;

public class Book extends LibraryItem {
    public Book(int itemID, String itemName, String type, int pageCount, List<Author> authors) {
        super(itemID, itemName, type);
        this.pageCount = pageCount;
        this.authors = authors;
    }

    private int pageCount;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    private List<Author> authors;

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
