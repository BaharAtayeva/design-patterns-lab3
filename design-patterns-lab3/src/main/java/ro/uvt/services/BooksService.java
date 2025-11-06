package ro.uvt.services;

import org.springframework.stereotype.Service;
import ro.uvt.model.Book;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BooksService {
    private final Map<Long, Book> db = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public List<Book> getAll(){ return new ArrayList<>(db.values()); }

    public Optional<Book> getById(Long id){ return Optional.ofNullable(db.get(id)); }

    public Book create(Book b){
        long id = idGen.getAndIncrement();
        b.setId(id);
        db.put(id, b);
        return b;
    }

    public Optional<Book> update(Long id, Book b){
        if(!db.containsKey(id)) return Optional.empty();
        b.setId(id);
        db.put(id, b);
        return Optional.of(b);
    }

    public boolean delete(Long id){ return db.remove(id) != null; }
}
