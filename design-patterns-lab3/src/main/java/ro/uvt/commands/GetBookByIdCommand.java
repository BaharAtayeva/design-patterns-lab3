package ro.uvt.commands;

import org.springframework.http.ResponseEntity;
import ro.uvt.services.BooksService;

public class GetBookByIdCommand implements Command {
    private final BooksService service; private final Long id;
    public GetBookByIdCommand(BooksService s, Long id){ this.service=s; this.id=id; }
    public ResponseEntity<?> execute(){
        return service.getById(id).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
