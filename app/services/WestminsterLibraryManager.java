package services;

import dto.Book;
import dto.Reader;
import io.ebean.Ebean;
import models.AuthorModel;
import models.BookModel;
import models.ReaderModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WestminsterLibraryManager implements LibraryManager {


    @Override
    public void addBook(String itemName, String authorName, String readerName) {

        BookModel book = new BookModel();

        book.setName(itemName);

        ReaderModel reader = new ReaderModel();
        reader.setName(readerName);

        Ebean.save(reader);

        AuthorModel author = new AuthorModel();
        author.setName(authorName);

        Ebean.save(author);

        book.setReader(reader);
        book.setAuthors(Arrays.asList(author));

        Ebean.save(book);

    }

    @Override
    public List<Book> getAllBooks() {
        List<BookModel> bookModels = Ebean.find(BookModel.class).findList();

        List<Book> books = new ArrayList<>();

        for (BookModel bookModel : bookModels) {
            Book book = getBookDTObyModel(bookModel);
            books.add(book);
        }

        return books;
    }

    private Book getBookDTObyModel(BookModel bookModel) {
        Book book = new Book();
        book.setItemName(bookModel.getName());
        book.setItemID(bookModel.getId());

        Reader reader = getReaderDTObyModel(bookModel.getReader());
        book.setReader(reader);

        //TODO: write a method to get author list.

        return book;
    }

    private Reader getReaderDTObyModel(ReaderModel readerModel) {
        Reader reader = new Reader();
        reader.setReaderName(readerModel.getName());
        reader.setReaderID(readerModel.getId());

        return reader;
    }
}
