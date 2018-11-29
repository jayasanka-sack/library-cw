package models;

import javax.persistence.*;
import java.util.List;
import java.util.Date;

import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name = "books")
public class BookModel {

//    Create colomns

    @Id
    @Column(name = "isbn")
    private int isbn;

    @Column(name = "name")
    private String name;


    @Column(name = "status")
    private boolean status = true;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Column(name = "pageCount")
    private int pageCount;

    @Formats.DateTime(pattern = "dd/MM/yyyy")
    public Date borrowDate = new Date();

    @ManyToOne
    @JoinColumn(name = "reader", referencedColumnName = "id")
    private ReaderModel reader;

    @ManyToMany(mappedBy = "books")
    private List<AuthorModel> authors;

//    create getters and setters

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
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

    public List<AuthorModel> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorModel> authors) {
        this.authors = authors;
    }
}
