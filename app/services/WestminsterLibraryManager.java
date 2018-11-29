package services;

import dto.Book;
import dto.DVD;
import dto.LibraryItem;
import dto.Reader;
import io.ebean.Ebean;
import models.*;

import java.text.DecimalFormat;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class WestminsterLibraryManager implements LibraryManager {

    private static DecimalFormat df2 = new DecimalFormat(".##");

    @Override
    public void addBook(int isbn, String itemName, String authorId, String readerId, String pageCount) {

        BookModel book = new BookModel();
        book.setIsbn(isbn);
        book.setName(itemName);
        book.setPageCount(Integer.parseInt(pageCount));

        ReaderModel reader = Ebean.find(ReaderModel.class).where().eq("id", readerId).findOne();

        AuthorModel author = Ebean.find(AuthorModel.class).where().eq("id", authorId).findOne();

        if (reader != null)
            book.setBorrowDate(new Date());
        else
            book.setBorrowDate(null);


        book.setReader(reader);
        book.setAuthors(Arrays.asList(author));

        Ebean.save(book);

    }


    @Override
    public void addDvd(int isbn, String itemName, String publisherId, String readerId, String languages) {

        DVDModel dvd = new DVDModel();
        dvd.setIsbn(isbn);
        dvd.setName(itemName);
        dvd.setLanguages(languages);
        ReaderModel reader = Ebean.find(ReaderModel.class).where().eq("id", readerId).findOne();

        PublisherModel publisher = Ebean.find(PublisherModel.class).where().eq("id", publisherId).findOne();
        if (reader != null)
            dvd.setBorrowDate(new Date());
        else
            dvd.setBorrowDate(null);

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
    public List<LibraryItem> getAllItems() {
        List<LibraryItem> items = new ArrayList<>();
        items.addAll(getAllDvds());
        items.addAll(getAllBooks());

        return items;
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

    @Override
    public String returnItem(long isbn) {
        BookModel book = Ebean.find(BookModel.class).where().eq("isbn", isbn).eq("status", true).findOne();
        String message = "";
        if (book != null) {
            if (book.getReader() != null) {
                Date now = new Date();
                Date borrowedDate = book.getBorrowDate();
                long diffInMillies = Math.abs(now.getTime() - borrowedDate.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                book.setReader(null);
                book.setBorrowDate(null);

                Ebean.save(book);

                if (diff > 7) {
                    double fee = calculateFee(diffInMillies, diff, 7);
                    message = "You have exceeded the time, your fee is £ " + df2.format(fee);
                } else {
                    message = "Item Returned Successfully!";
                }
            } else {
                message = "Item already returned";
            }

        } else {
            DVDModel dvd = Ebean.find(DVDModel.class).where().eq("isbn", isbn).eq("status", true).findOne();
            if (dvd != null) {
                if (dvd.getReader() != null) {
                    Date now = new Date();
                    Date borrowedDate = dvd.getBorrowDate();
                    long diffInMillies = Math.abs(now.getTime() - borrowedDate.getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                    dvd.setReader(null);
                    dvd.setBorrowDate(null);
                    Ebean.save(dvd);
                    if (diff > 3) {
                        double fee = calculateFee(diffInMillies, diff, 3);
                        message = "You have exceeded the time, your fee is £ " + df2.format(fee);
                    } else {
                        message = "Item Returned Successfully!";
                    }

                } else {
                    message = "Item already returned";
                }
            } else {
                message = "The isbn is invalid";
            }

        }
        return message;
    }

    @Override
    public String borrowItem(long isbn, String readerId) {
        String message = "";

        ReaderModel reader = Ebean.find(ReaderModel.class).where().eq("id", readerId).findOne();
        if (reader == null) {
            return "Invalid reader Id";
        }
        BookModel book = Ebean.find(BookModel.class).where().eq("isbn", isbn).eq("status", true).findOne();
        if (book != null) {
            if (book.getReader() != null) {
                Date expectedDate = addDaysToDate(book.getBorrowDate(), 7);
                return "The book has been already borrowed. However it should be returned on " + convertDateToString(expectedDate);
            }
            book.setReader(reader);
            book.setBorrowDate(new Date());
            Ebean.save(book);
            message = "Book marked as Borrowed Successfully!";
        } else {
            DVDModel dvd = Ebean.find(DVDModel.class).where().eq("isbn", isbn).eq("status", true).findOne();
            if (dvd != null) {
                if (dvd.getReader() != null) {
                    Date expectedDate = addDaysToDate(dvd.getBorrowDate(), 3);
                    return "The dvd has been already borrowed. However it should be returned on " + convertDateToString(expectedDate);
                }
                dvd.setReader(reader);
                dvd.setBorrowDate(new Date());
                Ebean.save(dvd);
                message = "The DVD marked as Borrowed Successfully!";

            }
        }


        return message;
    }

    private Date addDaysToDate(Date borrowDate, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(borrowDate);
        c.add(Calendar.DAY_OF_MONTH, days);

        return c.getTime();
    }

    private Book getBookDTObyModel(BookModel bookModel) {
        Book book = new Book();
        book.setItemName(bookModel.getName());
        book.setItemID(bookModel.getIsbn());

        book.setPageCount(bookModel.getPageCount());
        book.setStatus(bookModel.getStatus());
        ReaderModel r = bookModel.getReader();
        if (r != null) {
            Reader reader = getReaderDTObyModel(r);
            book.setReader(reader);
            book.setBorrowDate(bookModel.getBorrowDate());
            book.setBorrowDateText(convertDateToString(bookModel.getBorrowDate()));
        }

        return book;
    }

    private DVD getDVDDTObyModel(DVDModel dvdModel) {
        DVD dvd = new DVD();
        dvd.setItemName(dvdModel.getName());
        dvd.setItemID(dvdModel.getIsbn());
        dvd.setStatus(dvdModel.getStatus());
        ReaderModel r = dvdModel.getReader();
        if (r != null) {
            Reader reader = getReaderDTObyModel(r);
            dvd.setReader(reader);
            dvd.setBorrowDate(dvdModel.getBorrowDate());
            dvd.setBorrowDateText(convertDateToString(dvdModel.getBorrowDate()));
        }
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

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    private double calculateFee(long difference, long diff, int max) {
        double fee = 0;
        long hours = TimeUnit.HOURS.convert(difference, TimeUnit.MILLISECONDS) - max * 24;
        double normalFee = 0.2;
        double extraFee = 0.5;
        int hoursFor3Days = 24 * 3;
        if (diff > 3) {
            fee = normalFee * hours;
        } else {
            fee = normalFee * hoursFor3Days + extraFee * (hours - hoursFor3Days);
        }
        return fee;
    }
}
