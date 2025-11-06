package ro.uvt.commands;

import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;

@Component
public class CommandExecutor {
    public ResponseEntity<?> execute(Command cmd){
        return cmd.execute(); // şimdilik senkron
    }
}
