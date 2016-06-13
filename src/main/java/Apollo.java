import actions.AddBook;
import actions.RateBook;
import actions.BooksActiondHandler;
import com.spotify.apollo.Environment;
import com.spotify.apollo.RequestContext;
import com.spotify.apollo.Response;
import com.spotify.apollo.Status;
import com.spotify.apollo.httpservice.HttpService;
import com.spotify.apollo.httpservice.LoadingException;
import com.spotify.apollo.route.Route;
import domain.Book;
import domain.Rating;
import okio.ByteString;


public class Apollo {

    private static Integer BOOKID = 1;
    private static BooksActiondHandler actiondHandler = new BooksActiondHandler();

    public static void main(String[] args) throws LoadingException {
        HttpService.boot(Apollo::init, "apollo", args);
    }

    static void init(Environment environment) {
        environment.routingEngine()
                .registerAutoRoute(Route.sync("GET", "/books/add", Apollo::addBook))
                .registerAutoRoute(Route.sync("GET", "/books/1/rate", Apollo::rateBook));
    }

    private static Response<ByteString> addBook(RequestContext context)  {
        Book book = new Book();
        actiondHandler.handle(new AddBook(book));
        return Response.forStatus(Status.CREATED);
    }

    private static Response<ByteString> rateBook(RequestContext context)  {
        Rating rating = new Rating("a description", 5, "a user id");
        actiondHandler.handle(new RateBook(BOOKID, rating));
        return Response.forStatus(Status.CREATED);
    }
}
