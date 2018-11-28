package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dto.Book;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.LibraryManager;
import services.WestminsterLibraryManager;
import play.data.DynamicForm;


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

    public Result addBook() {

        JsonNode body = request().body().asJson();

        String readerName = body.get("readerName").asText();
        String itemName = body.get("itemName").asText();
        String authorName = body.get("authorName").asText();

        libraryManager.addBook(itemName, authorName, readerName);

        return ok("Adding new book successful");

    }


}
