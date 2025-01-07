package org.baibei.assis;

import org.baibei.assis.obj.VoiceToKeyboard;
import org.baibei.assis.services.HandlersStarterService;
import org.baibei.console.ConsoleOutput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssisApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssisApplication.class, args);

        ConsoleOutput.standardLineLength = 100;
        VoiceToKeyboard.init();
        HandlersStarterService.start();
    }
}
