package org.baibei.assis.services;

import org.baibei.assis.components.ConsoleHandlerComponent;
import org.baibei.assis.components.SpeechToText;
import org.springframework.stereotype.Service;

@Service
public class HandlersStarterService {

    private static ConsoleHandlerComponent consoleHandlerComponent;
    private static SpeechToText speechToText;

    public HandlersStarterService(ConsoleHandlerComponent consoleHandlerComponent,
                                  SpeechToText speechToText) {
        HandlersStarterService.consoleHandlerComponent = consoleHandlerComponent;
        HandlersStarterService.speechToText = speechToText;
    }

    public static void start() {
        Thread t1 = new Thread(() -> speechToText.start());
        t1.setName("SpeechToText");

        t1.start();
        consoleHandlerComponent.start();
    }
}
