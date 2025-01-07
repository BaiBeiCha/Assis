package org.baibei.assis.services;

import org.baibei.assis.components.ConsoleHandlerComponent;
import org.baibei.assis.components.SpeechToText;
import org.baibei.assis.repositories.ScriptsRepository;
import org.springframework.stereotype.Service;

@Service
public class HandlersStarterService {

    private static ConsoleHandlerComponent consoleHandlerComponent;
    private static SpeechToText speechToText;
    private static ScriptsRepository scriptsRepository;

    public HandlersStarterService(ConsoleHandlerComponent consoleHandlerComponent,
                                  SpeechToText speechToText,
                                  ScriptsRepository scriptsRepository) {
        HandlersStarterService.consoleHandlerComponent = consoleHandlerComponent;
        HandlersStarterService.speechToText = speechToText;
        HandlersStarterService.scriptsRepository = scriptsRepository;
    }

    public static void start() {
        Thread t1 = new Thread(() -> speechToText.start());
        t1.setName("SpeechToText");

        t1.start();

        scriptsRepository.write();
        consoleHandlerComponent.start();
    }
}
