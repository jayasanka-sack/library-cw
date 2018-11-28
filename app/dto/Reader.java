package dto;

public class Reader {

    private int readerID;
    private String readerName;
    private String readerEmail;
    private String readerMobile;

    public String getReaderEmail() {
        return readerEmail;
    }

    public void setReaderEmail(String readerEmail) {
        this.readerEmail = readerEmail;
    }

    public String getReaderMobile() {
        return readerMobile;
    }

    public void setReaderMobile(String readerMobile) {
        this.readerMobile = readerMobile;
    }

    public int getReaderID() {
        return readerID;
    }

    public void setReaderID(int readerID) {
        this.readerID = readerID;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }
}
