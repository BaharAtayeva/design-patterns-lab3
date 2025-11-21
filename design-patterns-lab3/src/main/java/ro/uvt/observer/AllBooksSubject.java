package ro.uvt.observer;

import org.springframework.stereotype.Component;
import ro.uvt.model.Book;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class AllBooksSubject implements Subject {

    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Book book) {
        for (Observer o : observers) {
            o.update(book);
        }
    }


    public void add(Book book) {
        notifyObservers(book);
    }
}
