package services;

import dto.*;
import io.ebean.Ebean;
import models.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WestminsterLibraryManager implements LibraryManager {

    private static DecimalFormat df2 = new DecimalFormat(".##");

    @Override
    public String addBook(int isbn, String itemName, String authorId, String readerId, String pageCount) {
        LibraryItem item = getItemByIsbn(isbn);

        BookModel book = new BookModel();
        book.setIsbn(isbn);

        if(item != null){
            if(item.isStatus()) {
                return "Item with this ISBN alrady exists";
            }else if(item.getType().equals("DVD")) {
                return "A DVD with same ISBN has added in history and it was removed later. Unable to add a DVD with same ISBN";
            }else {
                book = Ebean.find(BookModel.class).where().eq("isbn", isbn).findOne();
            }
        }

        book.setName(itemName);
        book.setStatus(true);
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
        return "Book added Successfully";
    }


    @Override
    public String addDvd(int isbn, String itemName, String publisherId, String readerId, String languages) {

        LibraryItem item = getItemByIsbn(isbn);
        DVDModel dvd = new DVDModel();
        dvd.setIsbn(isbn);
        if(item != null){
            if(item.isStatus()) {
                return "Item with this ISBN alrady exists";
            }else if(item.getType().equals("book")) {
                return "A book with same ISBN has added in history and it was removed later. Unable to add a DVD with same ISBN";
            }else {
                dvd = Ebean.find(DVDModel.class).where().eq("isbn", isbn).findOne();
            }
        }

        dvd.setName(itemName);
        dvd.setStatus(true);
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
        return "DVD added Successfully";
    }

    private LibraryItem getItemByIsbn(int isbn) {
        BookModel book = Ebean.find(BookModel.class).where().eq("isbn", isbn).findOne();
        if(book != null){
            return getBookDTObyModel(book);
        }else {
            DVDModel dvd = Ebean.find(DVDModel.class).where().eq("isbn", isbn).findOne();
            if(dvd != null){
                return getDVDDTObyModel(dvd);
            }
        }

        return null;
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
    public List<Author> getAllAuthors() {
        List<AuthorModel> authorModels = Ebean.find(AuthorModel.class).findList();

        List<Author> authors = new ArrayList<>();

        for (AuthorModel authorModel : authorModels) {
            Author author = getAuthorDTObyModel(authorModel);
            authors.add(author);
        }

        return authors;
    }

    @Override
    public List<Publisher> getAllPublishers() {
        List<PublisherModel> publisherModels = Ebean.find(PublisherModel.class).findList();

        List<Publisher> publishers = new ArrayList<>();

        for (PublisherModel publisherModel : publisherModels) {
            Publisher publisher = getPublisherDTObyModel(publisherModel);
            publishers.add(publisher);
        }

        return publishers;
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

    @Override
    public List<OverDueItem> getReport() {
        List<LibraryItem> items = getAllItems();
        List<OverDueItem> overDueItems = new ArrayList<>();

        for (LibraryItem item : items) {

            if (isOverDude(item)) {
                double fee = calculateFee(item);
                OverDueItem overDueItem = new OverDueItem(item.getItemID(), item.getItemName(), item.getReader(), item.getBorrowDate(), item.getBorrowDateText(),item.getType(), item.isStatus());
                overDueItems.add(overDueItem);
                Date returnDate = itemReturnDate(item);
                overDueItem.setReturnDateText(convertDateToString(returnDate));
                overDueItem.setReturnDate(returnDate);
                overDueItem.setFee(fee);
            }
            Collections.sort(overDueItems);
        }
        return overDueItems;
    }



    private Date itemReturnDate(LibraryItem overDueItem) {
        String className = overDueItem.getClass().getName();

        int maxDays = 0;
        if (className.equals("dto.Book")) {
            maxDays = 3;
        } else if (className.equals("dto.DVD")) {
            maxDays = 7;
        }

        Date borrowedDate = overDueItem.getBorrowDate();
        Calendar c = Calendar.getInstance();
        c.setTime(borrowedDate);
        c.add(Calendar.DAY_OF_MONTH, maxDays);
        return c.getTime();
    }

    private double calculateFee(LibraryItem item) {
        String className = item.getClass().getName();
        double fee;

        int maxDays = 0;
        if (className.equals("dto.Book")) {
            maxDays = 3;
        } else if (className.equals("dto.DVD")) {
            maxDays = 7;
        }

        Date now = new Date();
        Date borrowedDate = item.getBorrowDate();
        long diffInMillies = Math.abs(now.getTime() - borrowedDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        long hours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS) - maxDays * 24;
        double normalFee = 0.2;
        double extraFee = 0.5;
        int hoursFor3Days = 24 * 3;
        if (diff > 3) {
            fee = normalFee * hours;
        } else {
            fee = normalFee * hoursFor3Days + extraFee * (hours - hoursFor3Days);
        }
        return round(fee,2);
    }

    private boolean isOverDude(LibraryItem item) {
        String className = item.getClass().getName();
        if(item.getReader()==null){
            return false;
        }
        int maxDays = 0;
        if (className.equals("dto.Book")) {
            maxDays = 3;
        } else if (className.equals("dto.DVD")) {
            maxDays = 7;
        }

        Date now = new Date();
        Date borrowedDate = item.getBorrowDate();
        long diffInMillies = Math.abs(now.getTime() - borrowedDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return (diff > maxDays);
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
        book.setType("book");

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
        dvd.setType("DVD");
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


    private Author getAuthorDTObyModel(AuthorModel authorModel) {
        Author author = new Author(authorModel.getId(),authorModel.getName());

        return author;
    }
    private Publisher getPublisherDTObyModel(PublisherModel publisherModel) {
        Publisher publisher = new Publisher(publisherModel.getId(),publisherModel.getName());

        return publisher;
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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
