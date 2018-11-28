package services;

import dto.Book;
import dto.DVD;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class WestminsterLibraryManager implements LibraryManager {


    @Override
    public void addBook(int isbn, String itemName, String authorId, String readerId, String pageCount, Date date) {

        BookModel book = new BookModel();
        book.setIsbn(isbn);
        book.setName(itemName);
        book.setPageCount(Integer.parseInt(pageCount));
        book.setBorrowDate(date);

        ReaderModel reader = Ebean.find(ReaderModel.class).where().eq("id", readerId).findOne();

        AuthorModel author = Ebean.find(AuthorModel.class).where().eq("id", authorId).findOne();
        ;

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

        PublisherModel publisher = Ebean.find(PublisherModel.class).where().eq("id", publisherId).findOne();
        ;

        dvd.setReader(reader);
        dvd.setPublisher(publisher);

        Ebean.save(dvd);

    }

    @Override
    public List<Book> getAllBooks() {
        List<BookModel> bookModels = Ebean.find(BookModel.class).where().eq("status", true).findList();

        List<Book> books = new ArrayList<>();

        for (BookModel bookModel : bookModels) {
            Book book = getBookDTObyModel(bookModel);
            books.add(book);
        }

        return books;
    }

    @Override
    public List<DVD> getAllDvds() {
        List<DVDModel> DVDModels = Ebean.find(DVDModel.class).where().eq("status", true).findList();

        List<DVD> dvds = new ArrayList<>();

        for (DVDModel dvdModel : DVDModels) {
            DVD dvd = getDVDDTObyModel(dvdModel);
            dvds.add(dvd);
        }

        return dvds;
    }

    @Override
    public void deleteItem(long isbn) {
        BookModel book = Ebean.find(BookModel.class).where().eq("isbn", isbn).findOne();
        if (book != null) {
            book.setStatus(false);
            Ebean.save(book);
        } else {
            DVDModel dvd = Ebean.find(DVDModel.class).where().eq("isbn", isbn).findOne();
            if (dvd != null) {
                dvd.setStatus(false);
                Ebean.save(dvd);
            }
        }
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
        book.setBorrowDateText(convertDateToString(bookModel.getBorrowDate()));

        book.setPageCount(bookModel.getPageCount());

        Reader reader = getReaderDTObyModel(bookModel.getReader());
        book.setReader(reader);

        return book;
    }

    private DVD getDVDDTObyModel(DVDModel dvdModel) {
        DVD dvd = new DVD();
        dvd.setItemName(dvdModel.getName());
        dvd.setItemID(dvdModel.getIsbn());
        dvd.setBorrowDate(dvdModel.getBorrowDate());
        dvd.setBorrowDateText(convertDateToString(dvdModel.getBorrowDate()));

        Reader reader = getReaderDTObyModel(dvdModel.getReader());
        dvd.setReader(reader);

        return dvd;
    }

    private Reader getReaderDTObyModel(ReaderModel readerModel) {
        Reader reader = new Reader();
        reader.setReaderName(readerModel.getName());
        reader.setReaderID(readerModel.getId());
        reader.setReaderEmail(readerModel.getEmail());
        reader.setReaderMobile(readerModel.getMobile());

        return reader;
    }

    private String convertDateToString(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        System.out.println(5555);
        return strDate;
    }
}
