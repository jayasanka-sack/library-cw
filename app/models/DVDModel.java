package models;

import io.ebean.Model;
import play.data.format.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dvds")
public class DVDModel extends Model {

    @Id
    @Column(name = "isbn")
    private int isbn;

    @Column(name = "name")
    private String name;

    @Column(name = "langages")
    private String languages;

    @Column(name = "status")
    private boolean status = true;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date borrowDate = new Date();

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    @ManyToOne
    @JoinColumn(name = "reader", referencedColumnName = "id")
    private ReaderModel reader;

    @ManyToOne
    @JoinColumn(name = "publisher", referencedColumnName = "id")
    private PublisherModel publisher;

    public PublisherModel getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherModel publisher) {
        this.publisher = publisher;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReaderModel getReader() {
        return reader;
    }

    public void setReader(ReaderModel reader) {
        this.reader = reader;
    }
}
