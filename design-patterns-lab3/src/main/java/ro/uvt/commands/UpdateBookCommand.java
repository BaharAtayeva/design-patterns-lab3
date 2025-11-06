package ro.uvt.commands;

import org.springframework.http.ResponseEntity;
import ro.uvt.model.Book;
import ro.uvt.services.BooksService;

public class UpdateBookCommand implements Command {
    private final BooksService service; private final Long id; private final Book payload;
    public UpdateBookCommand(BooksService s, Long id, Book b){ this.service=s; this.id=id; this.payload=b; }

    public ResponseEntity<?> execute(){
        return service.update(id, payload).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
