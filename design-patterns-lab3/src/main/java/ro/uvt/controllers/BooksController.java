package ro.uvt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ro.uvt.commands.*;
import ro.uvt.model.Book;
import ro.uvt.services.BooksService;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksService service;
    private final CommandExecutor executor;

    public BooksController(BooksService service, CommandExecutor executor){
        this.service = service; this.executor = executor;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return executor.execute(new GetAllBooksCommand(service));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return executor.execute(new GetBookByIdCommand(service, id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Book book, UriComponentsBuilder uri){
        return executor.execute(new CreateBookCommand(service, book, uri));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book){
        return executor.execute(new UpdateBookCommand(service, id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return executor.execute(new DeleteBookCommand(service, id));
    }
}
