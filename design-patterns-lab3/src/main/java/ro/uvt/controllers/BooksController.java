package ro.uvt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uvt.model.Book;
import ro.uvt.services.BooksService;
import ro.uvt.observer.AllBooksSubject;   // <- senin paketine göre düzelt

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final AllBooksSubject allBooksSubject;   // <- yeni alan

    // constructor injection
    public BooksController(BooksService booksService,
                           AllBooksSubject allBooksSubject) {
        this.booksService = booksService;
        this.allBooksSubject = allBooksSubject;
    }

    @GetMapping
    public List<Book> getAll() {
        return booksService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return booksService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book saved = booksService.create(book);

        // ŞİMDİLİK KAPATALIM:
        // allBooksSubject.notifyObservers(saved);
        // veya allBooksSubject.add(saved);

        return ResponseEntity.created(URI.create("/books/" + saved.getId()))
                .body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id,
                                       @RequestBody Book book) {
        return booksService.update(id, book)
                .map(updated -> {
                    // Güncellemede de push istersen:
                    allBooksSubject.notifyObservers(updated);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return booksService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
