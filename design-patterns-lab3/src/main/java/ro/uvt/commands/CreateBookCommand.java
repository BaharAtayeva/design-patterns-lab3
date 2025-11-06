package ro.uvt.commands;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import ro.uvt.model.Book;
import ro.uvt.services.BooksService;

public class CreateBookCommand implements Command {
    private final BooksService service; private final Book payload;
    private final UriComponentsBuilder uriBuilder;

    public CreateBookCommand(BooksService s, Book p, UriComponentsBuilder u){
        this.service=s; this.payload=p; this.uriBuilder=u;
    }
    public ResponseEntity<?> execute(){
        Book created = service.create(payload);
        return ResponseEntity.created(
                uriBuilder.path("/books/{id}").build(created.getId())).body(created);
    }
}
