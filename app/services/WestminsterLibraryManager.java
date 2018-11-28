package services;

import dto.Book;
import dto.Reader;
import io.ebean.Ebean;
import models.AuthorModel;
import models.BookModel;
import models.DVDModel;
import models.ReaderModel;
import models.PublisherModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

public class WestminsterLibraryManager implements LibraryManager {


    @Override
    public void addBook(int isbn, String itemName, String authorId, String readerId, String pageCount, Date date) {

        BookModel book = new BookModel();
        book.setIsbn(isbn);
        book.setName(itemName);
        book.setPageCount(Integer.parseInt(pageCount));
        book.setBorrowDate(date);

        ReaderModel reader = Ebean.find(ReaderModel.class).where().eq("id", 1).findOne();

        AuthorModel author = Ebean.find(AuthorModel.class).where().eq("id", 1).findOne();;

        book.setReader(reader);
        book.setAuthors(Arrays.asList(author));

        Ebean.save(book);

    }


    @Override
    public void addDvd(int isbn, String itemName, String publisherId, String readerId, String languages, Date date) {

        DVDModel dvd = new DVDModel();
        dvd.setIsbn(isbn);
        dvd.setName(itemName);
        dvd.setLanguages(languages);
        dvd.setBorrowDate(date);

        ReaderModel reader = Ebean.find(ReaderModel.class).where().eq("id", readerId).findOne();

        PublisherModel publisher = Ebean.find(PublisherModel.class).where().eq("id", publisherId).findOne();;

        dvd.setReader(reader);
        dvd.setPublisher(publisher);

        Ebean.save(dvd);

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


    @Override
    public List<Reader> getAllReaders() {
        List<ReaderModel> readerModels = Ebean.find(ReaderModel.class).findList();

        List<Reader> readers = new ArrayList<>();

        for (ReaderModel readerModel : readerModels) {
            Reader reader = getReaderDTObyModel(readerModel);
            readers.add(reader);
        }

        return readers;
    }

    private Book getBookDTObyModel(BookModel bookModel) {
        Book book = new Book();
        book.setItemName(bookModel.getName());
        book.setItemID(bookModel.getIsbn());
        book.setBorrowDate(bookModel.getBorrowDate());
        book.setPageCount(bookModel.getPageCount());

        Reader reader = getReaderDTObyModel(bookModel.getReader());
        book.setReader(reader);

        //TODO: write a method to get author list.

        return book;
    }

    private Reader getReaderDTObyModel(ReaderModel readerModel) {
        Reader reader = new Reader();
        reader.setReaderName(readerModel.getName());
        reader.setReaderID(readerModel.getId());
        reader.setReaderEmail(readerModel.getEmail());
        reader.setReaderMobile(readerModel.getMobile());

        return reader;
    }
}
