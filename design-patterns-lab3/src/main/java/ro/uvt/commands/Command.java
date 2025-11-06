package ro.uvt.commands;

import org.springframework.http.ResponseEntity;

public interface Command {
    ResponseEntity<?> execute();
}
