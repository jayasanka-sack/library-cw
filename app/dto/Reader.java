package dto;

public class Reader extends Person{


    private String readerEmail;
    private String readerMobile;

    public Reader(int personID, String personName, String readerEmail, String readerMobile) {
        super(personID, personName);
        this.readerEmail = readerEmail;
        this.readerMobile = readerMobile;
    }

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


}
