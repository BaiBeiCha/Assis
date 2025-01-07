package org.baibei.assis.components;

import org.baibei.assis.obj.Command;
import org.baibei.assis.obj.CommandHandler;
import org.baibei.assis.repositories.AppsLocaleRepository;
import org.baibei.assis.repositories.MainDirectoriesRepository;
import org.baibei.assis.repositories.PropertiesRepository;
import org.baibei.assis.services.AppRunnerService;
import org.baibei.assis.services.FindAppPathService;
import org.baibei.console.ConsoleOutput;
import org.springframework.stereotype.Component;

@Component
public class SpeechHandlerComponent extends CommandHandler {

    public SpeechHandlerComponent(AppRunnerService appRunnerService,
                                  AppsLocaleRepository appsLocaleRepository,
                                  MainDirectoriesRepository mainDirectoriesRepository,
                                  FindAppPathService findAppPathService,
                                  PropertiesRepository propertiesRepository) {
        super(appRunnerService,
                appsLocaleRepository,
                mainDirectoriesRepository,
                findAppPathService,
                propertiesRepository,
                propertiesRepository.getFindDepth()
        );
    }

    public boolean handle(Command command, String language) {
        switch (language) {
            case "en": {
                switch (command.getCommandType()) {
                    case "run", "open", "launch":
                        run(command.getArgs());
                        break;

                    case "stop", "kill", "close":
                        if (command.getArgs()[0].equals("self")) {
                            System.exit(0);
                        } else {
                            kill(command.getArgs());
                        }
                        break;

                    case "type":
                        type(command.getArgs()[0]);
                        break;

                    case "exit":
                        return false;

                    default:
                        ConsoleOutput.error("Unknown command type: " + command.getCommandType());
                        break;
                }
                break;
            }

            case "ru": {
                switch (command.getCommandType()) {
                    case "запустить", "запуск", "запусти", "включи", "включить", "вруби", "открой", "открыть":
                        run(command.getArgs());
                        break;

                    case "выключи", "выруби", "отключи", "закрой", "закрыть":
                        kill(command.getArgs());
                        break;

                    case "набери", "напечатай", "печать", "напиши", "пиши":
                        type(command.getArgs()[0]);
                        break;

                    case "выход":
                        return false;

                    default:
                        ConsoleOutput.error("Неизвестная команда: " + command.getCommandType());
                        break;
                }
                break;
            }

            default:
                ConsoleOutput.error("Illegal language: " + language);
        }
        return true;
    }
}
