package models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class BookModel {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "reader", referencedColumnName = "id")
    private ReaderModel reader;

    @ManyToMany(mappedBy = "books")
    private List<AuthorModel> authors;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
