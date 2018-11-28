package services;

import dto.Book;
import dto.Reader;

import java.util.List;

public interface LibraryManager {

    void addBook(String itemName, String authorName, String readerName);

    List<Book> getAllBooks();

    List<Reader> getAllReaders();
}
