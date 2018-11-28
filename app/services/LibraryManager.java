package services;

import dto.Book;

import java.util.List;

public interface LibraryManager {

    void addBook(String itemName, String authorName, String readerName);

    List<Book> getAllBooks();
}
