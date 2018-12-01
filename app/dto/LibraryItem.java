package dto;

import java.util.Date;

public abstract class LibraryItem {

    private int itemID;
    private String itemName;
    private Reader reader;
    private Date borrowDate;
    private String borrowDateText;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LibraryItem() {

    }

    public LibraryItem(int itemID, String itemName, Reader reader, Date borrowDate, String borrowDateText, String type, boolean status) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.reader = reader;
        this.borrowDate = borrowDate;
        this.borrowDateText = borrowDateText;
        this.type = type;
        this.status = status;
    }

//    public LibraryItem(int itemID, String itemName, Reader reader, Date borrowDate, String borrowDateText, boolean status) {
//
//        this.itemID = itemID;
//        this.itemName = itemName;
//        this.reader = reader;
//        this.borrowDate = borrowDate;
//        this.borrowDateText = borrowDateText;
//        this.status = status;
//    }

    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public String getBorrowDateText() {
        return borrowDateText;
    }

    public void setBorrowDateText(String borrowDateText) {
        this.borrowDateText = borrowDateText;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
