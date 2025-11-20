package ro.uvt.commands;

import org.springframework.stereotype.Component;
import ro.uvt.persistence.BooksRepository;

@Component
public class CommandContext {
    private final BooksRepository booksRepository;

    public CommandContext(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public BooksRepository getBooksRepository() {
        return booksRepository;
    }
}
