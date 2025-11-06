package ro.uvt.commands;

import org.springframework.http.ResponseEntity;
import ro.uvt.services.BooksService;

public class GetAllBooksCommand implements Command {
    private final BooksService service;
    public GetAllBooksCommand(BooksService service){ this.service = service; }
    public ResponseEntity<?> execute(){ return ResponseEntity.ok(service.getAll()); }
}
