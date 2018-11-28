package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dto.Book;
import dto.Reader;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.LibraryManager;
import services.WestminsterLibraryManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    LibraryManager libraryManager = new WestminsterLibraryManager();

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {

        List<Book> books = libraryManager.getAllBooks();

        return ok(Json.toJson(books));
    }

    public Result readers() {

        List<Reader> books = libraryManager.getAllReaders();

        return ok(Json.toJson(books));
    }

    public Result addBook() {

        JsonNode body = request().body().asJson();
        int isbn = Integer.parseInt(body.get("isbn").asText());
        String readerId = body.get("readerId").asText();
        String itemName = body.get("itemName").asText();
        String authorId = body.get("authorId").asText();
        String pageCount = body.get("pageCount").asText();
        String borrowDateText = body.get("borrowDate").asText();
        Date borrowDate = null;
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
             borrowDate = df.parse(borrowDateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        libraryManager.addBook(isbn, itemName, authorId, readerId, pageCount, borrowDate);

        return ok("Adding new book successful");

    }
    public Result addDvd() {

        JsonNode body = request().body().asJson();
        int isbn = Integer.parseInt(body.get("isbn").asText());
        String readerId = body.get("readerId").asText();
        String itemName = body.get("itemName").asText();
        String publisherId = body.get("publisherId").asText();
        String languages = body.get("languages").asText();
        String borrowDateText = body.get("borrowDate").asText();
        Date borrowDate = null;
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            borrowDate = df.parse(borrowDateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        libraryManager.addDvd(isbn, itemName, publisherId, readerId, languages, borrowDate);

        return ok("Adding new DVD successful");

    }


}
