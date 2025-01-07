package org.baibei.assis.components;

import org.baibei.assis.obj.Command;
import org.baibei.assis.repositories.PropertiesRepository;
import org.baibei.console.ConsoleOutput;
import org.baibei.tools.StringTools;
import org.springframework.stereotype.Component;
import org.vosk.LogLevel;
import org.vosk.LibVosk;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.IOException;

@Component
public class SpeechToText {

    private String model;
    private boolean active;
    private TargetDataLine currentLine;
    private Recognizer currentRecognizer;

    private final SpeechHandlerComponent speechHandlerComponent;
    private final PropertiesRepository propertiesRepository;

    public SpeechToText(SpeechHandlerComponent speechHandlerComponent,
                        PropertiesRepository propertiesRepository) {
        this.speechHandlerComponent = speechHandlerComponent;
        this.propertiesRepository = propertiesRepository;

        LibVosk.vosk_set_log_level(-1);

        this.model = propertiesRepository.getModel();
        active = true;
    }

    public void start() {
        active = true;

        try (Model model = new Model(this.model)) {
            Recognizer recognizer = new Recognizer(model, 16000);
            currentRecognizer = recognizer;

            AudioFormat format = new AudioFormat(16000, 16,
                    1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                ConsoleOutput.error("Audio Format is not supported");
                System.exit(1);
            }

            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            currentLine = line;

            String language = this.model.split("-")[1];

            switch (language) {
                case "en":
                    ConsoleOutput.info("Started speech recognizing");
                    break;

                case "ru":
                    ConsoleOutput.info("Начали распознавание речи");
                    break;

                default:
                    ConsoleOutput.error("Unknown language");
                    System.exit(1);
            }

            byte[] buffer = new byte[4096];

            while (active) {
                int bytesRead = line.read(buffer, 0, buffer.length);
                if (bytesRead > 0) {
                    String resultJson;
                    boolean isFinal;

                    if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                        resultJson = recognizer.getResult();
                        isFinal = true;
                    } else {
                        resultJson = recognizer.getPartialResult();
                        isFinal = false;
                    }

                    String recognizedText = StringTools.JsonToStringValue(resultJson);

                    if (isFinal) {
                        if (!recognizedText.isEmpty()  && recognizedText.split("\\s+").length > 1
                            && recognizedText.split("\\s+")[0]
                                .equals(propertiesRepository.getProperty("name"))) {
                            try {
                                recognizedText = recognizedText.replace(
                                        propertiesRepository.getProperty("name") + " ", "");

                                Command command = Command.create(recognizedText.trim());
                                ConsoleOutput.info("Processing command: " + command);

                                active = speechHandlerComponent.handle(command, language);
                            } catch (Exception e) {
                                ConsoleOutput.error("Command error: " + e.getMessage());
                            }
                        } else {

                        }
                    } else {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            ConsoleOutput.error("Speech handler interrupted");
                        }
                    }
                }
            }
        } catch (IOException | LineUnavailableException e) {
            ConsoleOutput.error(e.getMessage());
        }
    }

    public void stop() {
        active = false;

        if (currentLine != null && currentLine.isOpen()) {
            currentLine.stop();
            currentLine.close();
            currentLine = null;
        }

        if (currentRecognizer != null) {
            currentRecognizer.close();
            currentRecognizer = null;
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}

