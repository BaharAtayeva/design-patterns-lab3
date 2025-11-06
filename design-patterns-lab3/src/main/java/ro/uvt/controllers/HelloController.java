package ro.uvt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.uvt.difexamples.ClientComponent;

@RestController
public class HelloController {
    private final ClientComponent client;

    public HelloController(ClientComponent client) {
        this.client = client;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello from Spring Boot\nClientComponent = " + client;
    }
}
