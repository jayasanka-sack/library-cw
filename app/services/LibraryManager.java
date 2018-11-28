package services;

import dto.Book;
import dto.DVD;
import dto.Reader;

import java.util.Date;
import java.util.List;

public interface LibraryManager {

    void addBook(int isbn, String itemName, String authorId, String readerId, String pageCount, Date date);
    void addDvd(int isbn, String itemName, String publisherId, String readerId, String languages, Date date);
    void deleteItem(long isbn);

    List<Book> getAllBooks();
    List<DVD> getAllDvds();

    List<Reader> getAllReaders();
}
