package dto;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class OverDueItem extends LibraryItem implements Comparable<OverDueItem>{
    private String returnDateText;
    private Date returnDate;
    private double fee;

    public String getReturnDateText() {
        return returnDateText;
    }

    public void setReturnDateText(String returnDateText) {
        this.returnDateText = returnDateText;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public double getFee() {
        return fee;
    }

    public OverDueItem(int itemID, String itemName, String type, String returnDateText, Date returnDate, double fee) {
        super(itemID, itemName, type);
        this.returnDateText = returnDateText;
        this.returnDate = returnDate;
        this.fee = fee;
    }

    public void setFee(double fee) {

        this.fee = fee;
    }

    @Override
    public int compareTo(@NotNull OverDueItem o) {
        long n1 = this.getReturnDate().getTime();
        long n2 = o.getReturnDate().getTime();
        return Long.compare(n1, n2);
    }
}
