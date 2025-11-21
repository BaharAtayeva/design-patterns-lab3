package ro.uvt.observer;

import ro.uvt.model.Book;

public interface Observer {
    void update(Book book);
}
