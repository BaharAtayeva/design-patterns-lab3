package ro.uvt.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ro.uvt.observer.AllBooksSubject;
import ro.uvt.observer.SseObserver;

@RestController
@RequiredArgsConstructor
public class BooksSseController {

    private final AllBooksSubject allBooksSubject;

    @GetMapping("/books-sse")
    public SseEmitter getBooksSse() {

        SseEmitter emitter = new SseEmitter(0L);
        allBooksSubject.attach(new SseObserver(emitter));
        return emitter;
    }
}
