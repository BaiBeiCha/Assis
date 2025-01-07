package org.baibei.assis.components;

import org.baibei.assis.obj.Command;
import org.baibei.assis.obj.CommandHandler;
import org.baibei.assis.repositories.AppsLocaleRepository;
import org.baibei.assis.repositories.MainDirectoriesRepository;
import org.baibei.assis.repositories.PropertiesRepository;
import org.baibei.assis.services.AppRunnerService;
import org.baibei.assis.services.FindAppPathService;
import org.baibei.console.Color;
import org.baibei.console.ConsoleOutput;
import org.baibei.tools.StringTools;
import org.springframework.stereotype.Component;

@Component
public class ConsoleHandlerComponent extends CommandHandler {

    private final SpeechToText speechToText;

    public ConsoleHandlerComponent(AppRunnerService appRunnerService,
                                   AppsLocaleRepository appsLocaleRepository,
                                   MainDirectoriesRepository mainDirectoriesRepository,
                                   FindAppPathService findAppPathService,
                                   PropertiesRepository propertiesRepository,
                                   SpeechToText speechToText) {
        super(appRunnerService,
                appsLocaleRepository,
                mainDirectoriesRepository,
                findAppPathService,
                propertiesRepository,
                propertiesRepository.getFindDepth());

        this.speechToText = speechToText;
    }

    public void start() {
        while (true) {
            String raw = ConsoleOutput.nextLine();
            ConsoleOutput.printLine();

            Command command = Command.createFull(raw);

            if (handle(command)) {
                break;
            }
        }
    }

    public boolean handle(Command command) {
        switch (command.getCommandType()) {
            case "?", "commands", "help":
                commands();
                break;

            case "run":
                run(command.getArgs());
                break;

            case "prun":
                prun(command.getArgs());
                break;

            case "lrun": {
                lrun(command.getArgs());
                break;
            }

            case "kill", "close":
                kill(command.getArgs());
                break;

            case "pkill":
                pkill(command.getArgs());
                break;

            case "lkill":
                lkill(command.getArgs());
                break;

            case "path":
                path(command.getArgs());
                break;

            case "find":
                find(command.getArgs());
                break;

            case "rename":
                rename(command.getArgs());
                break;

            case "copy":
                copy(command.getArgs());
                break;

            case "currentLoc":
                currentLoc();
                break;

            case "loc":
                loc(command.getArgs());
                break;

            case "isVoiceAlive":
                isVoiceAlive();
                break;

            case "voiceStop":
                voiceStop();
                break;

            case "type":
                type(StringTools.append(command.getArgs()));
                break;

            case "changeName":
                changeName(command.getArgs());
                break;

            case "findDepth",
                 "find_depth",
                 "find-depth",
                 "finddepth",
                 "fd": {
                findDepth(command.getArgs());
                break;
            }


            case "repo":
                switch (command.getArgs()[0]) {
                    case "save":
                        repoSave(command.getArgs());
                        break;

                    case "read":
                        repoRead();
                        break;

                    case "find":
                        repoFind(command.getArgs());
                        break;

                    case "list":
                        repoList();
                        break;

                    case "dirs":
                        switch (command.getArgs()[0]) {
                            case "save":
                                repoDirsSave(command.getArgs());
                                break;

                            case "read":
                                repoDirsRead(command.getArgs());
                                break;

                            default:
                                unknown(command);
                        }
                        break;

                    default:
                        unknown(command);
                }
                break;

            case "exit":
                System.exit(0);

            default:
                unknown(command);
        }

        return false;
    }

    private void currentLoc() {
        ConsoleOutput.print("Current location", speechToText.getModel().split("-")[1], Color.BLUE);
    }

    private void loc(String[] args) {
        speechToText.stop();
        propertiesRepository.save("model=" + args[0]);
        ConsoleOutput.info("Please, restart the application");
        System.exit(0);
    }

    private void voiceStop() {
        speechToText.stop();
    }
}
