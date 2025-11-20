package ro.uvt.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.uvt.model.Book;
import ro.uvt.persistence.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository repo;

    public List<Book> getAll() {
        return repo.findAll();
    }

    public Optional<Book> getById(Long id) {
        return repo.findById(id);
    }

    public Book create(Book b) {
        // id'yi DB üretecek
        return repo.save(b);
    }

    public Optional<Book> update(Long id, Book b) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(b.getTitle());
            existing.setAuthor(b.getAuthor());
            existing.setIsbn(b.getIsbn());
            return repo.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
