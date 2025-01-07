package org.baibei.assis.components;

import org.baibei.assis.obj.AppLocale;
import org.baibei.assis.obj.Command;
import org.baibei.assis.obj.CommandHandler;
import org.baibei.assis.obj.Script;
import org.baibei.assis.repositories.AppsLocaleRepository;
import org.baibei.assis.repositories.MainDirectoriesRepository;
import org.baibei.assis.repositories.PropertiesRepository;
import org.baibei.assis.repositories.ScriptsRepository;
import org.baibei.assis.services.AppRunnerService;
import org.baibei.assis.services.CryptService;
import org.baibei.assis.services.FileCommandExecutorService;
import org.baibei.assis.services.FindAppPathService;
import org.baibei.console.Color;
import org.baibei.console.ConsoleOutput;
import org.baibei.tools.FileTools;
import org.baibei.tools.StringTools;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleHandlerComponent extends CommandHandler {

    private final SpeechToText speechToText;
    private final FileCommandExecutorService fileCommandExecutorService;
    private final CryptService cryptService;
    private final ScriptsRepository scriptsRepository;

    public ConsoleHandlerComponent(AppRunnerService appRunnerService,
                                   AppsLocaleRepository appsLocaleRepository,
                                   MainDirectoriesRepository mainDirectoriesRepository,
                                   FindAppPathService findAppPathService,
                                   PropertiesRepository propertiesRepository,
                                   SpeechToText speechToText,
                                   FileCommandExecutorService fileCommandExecutorService,
                                   CryptService cryptService,
                                   ScriptsRepository scriptsRepository) {
        super(appRunnerService,
                appsLocaleRepository,
                mainDirectoriesRepository,
                findAppPathService,
                propertiesRepository,
                propertiesRepository.getFindDepth());

        this.speechToText = speechToText;
        this.fileCommandExecutorService = fileCommandExecutorService;
        this.cryptService = cryptService;
        this.scriptsRepository = scriptsRepository;
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

            case "typeIn":
                typeIn(command.getArgs());
                break;

            case "typeSpeed":
                setTypeSpeed(command.getArgs());
                break;

            case "changeName":
                changeName(command.getArgs());
                break;

            case "file": {
                String[] args = command.getArgs();
                switch (args[0]) {
                    case "add":
                        fileAdd(args);
                        break;

                    case "addRun":
                        fileAdd(args);
                        fileRun(args);
                        break;

                    case "addDRun":
                        fileAdd(args);
                        fileDecrypt(args);
                        fileRun(args);

                    case "run":
                        fileRun(args);
                        break;

                    case "drun":
                        fileDecrypt(args);
                        fileRun(args);
                        break;

                    case "encrypt":
                        fileEncrypt(args);
                        break;

                    case "decrypt":
                        fileDecrypt(args);
                        break;

                    default:
                        unknown(command);
                }
                break;
            }

            case "scripts":
                switch (command.getArgs()[0]) {
                    case "list":
                        scriptsList();
                        break;

                    case "update":
                        scriptsUpdate();
                        break;

                    case "add":
                        scriptsAdd(command.getArgs());
                        break;

                    case "run":
                        fileRun(command.getArgs());
                        break;

                    default:
                        unknown(command);
                }
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

            case "wait":
                wait(command.getArgs());
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

    private void fileAdd(String[] args) {
        appsLocaleRepository.save(new AppLocale(args[1],
                StringTools.append(args, 2, args.length)));
        repoList();
    }

    private void fileRun(String[] args) {
        AppLocale locale = appsLocaleRepository.findByName(args[1]);

        String path;

        if (locale == null) {
            path = StringTools.append(args, 1, args.length);
        } else {
            path = locale.getPath();
        }

        List<Command> commands = fileCommandExecutorService.execute(path);

        for (Command command : commands) {
            try {
                handle(command);
            } catch (Exception e) {
                ConsoleOutput.error("Can't handle command: " + command);
                ConsoleOutput.error(e.getMessage());
            }
        }
    }

    private void fileEncrypt(String[] args) {
        AppLocale locale = appsLocaleRepository.findByName(args[1]);

        String path;

        if (locale == null) {
            path = StringTools.append(args, 1, args.length);
        } else {
            path = locale.getPath();
        }

        String raw = FileTools.readFileAsString(path);
        String text = cryptService.encrypt(raw);
        FileTools.writeFile(path, text, false);
    }

    private void fileDecrypt(String[] args) {
        AppLocale locale = appsLocaleRepository.findByName(args[1]);

        String path;

        if (locale == null) {
            path = StringTools.append(args, 1, args.length);
        } else {
            path = locale.getPath();
        }

        String raw = FileTools.readFileAsString(path);
        String text = cryptService.decrypt(raw);
        FileTools.writeFile(path, text, false);
    }

    private void scriptsList() {
        ConsoleOutput.printHeader("Scripts", Color.PURPLE);
        ConsoleOutput.printLine(Color.PURPLE);
        for (Script script : scriptsRepository.find()) {
            System.out.println(script.toString());
            ConsoleOutput.printLine(Color.PURPLE);
        }
        System.out.println();
    }

    private void scriptsUpdate() {
        scriptsRepository.update();
    }

    private void scriptsAdd(String[] args) {
        scriptsRepository.save(args[1], StringTools.append(args, 2, args.length));
        scriptsRepository.update();
    }
}
