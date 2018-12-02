package dto;

public class Person {

    private int personID;
    private String personName;

    public Person(int personID, String personName) {
        this.personID = personID;
        this.personName = personName;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
