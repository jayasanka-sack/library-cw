package services;

import dto.*;

import java.util.Date;
import java.util.List;

public interface LibraryManager {

    void addBook(int isbn, String itemName, String authorId, String readerId, String pageCount);
    String addDvd(int isbn, String itemName, String publisherId, String readerId, String languages);
    void deleteItem(long isbn);

    List<Book> getAllBooks();
    List<DVD> getAllDvds();
    List<LibraryItem> getAllItems();

    List<Reader> getAllReaders();


    String returnItem(long isbn);
    String borrowItem(long isbn, String readerId);

    List<OverDueItem> getReport();

}
