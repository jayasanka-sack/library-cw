package models;

import io.ebean.Model;

import javax.persistence.*;

@Entity
@Table(name = "dvds")
public class DVDModel extends Model {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "reader", referencedColumnName = "id")
    private ReaderModel reader;

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
}
