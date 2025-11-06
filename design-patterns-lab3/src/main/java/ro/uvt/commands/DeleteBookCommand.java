package ro.uvt.commands;

import org.springframework.http.ResponseEntity;
import ro.uvt.services.BooksService;

public class DeleteBookCommand implements Command {
    private final BooksService service; private final Long id;
    public DeleteBookCommand(BooksService s, Long id){ this.service=s; this.id=id; }

    public ResponseEntity<?> execute(){
        return service.delete(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
