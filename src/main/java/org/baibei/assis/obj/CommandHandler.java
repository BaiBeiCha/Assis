package org.baibei.assis.obj;

import org.baibei.assis.repositories.AppsLocaleRepository;
import org.baibei.assis.repositories.MainDirectoriesRepository;
import org.baibei.assis.repositories.PropertiesRepository;
import org.baibei.assis.services.AppRunnerService;
import org.baibei.assis.services.FindAppPathService;
import org.baibei.console.Color;
import org.baibei.console.ConsoleOutput;
import org.baibei.tools.StringTools;
import org.baibei.tools.ThreadTools;

public class CommandHandler {

    protected int findDepth;

    protected final AppRunnerService appRunnerService;
    protected final AppsLocaleRepository appsLocaleRepository;
    protected final MainDirectoriesRepository mainDirectoriesRepository;
    protected final FindAppPathService findAppPathService;
    protected final PropertiesRepository propertiesRepository;


    public CommandHandler(AppRunnerService appRunnerService,
                          AppsLocaleRepository appsLocaleRepository,
                          MainDirectoriesRepository mainDirectoriesRepository,
                          FindAppPathService findAppPathService,
                          PropertiesRepository propertiesRepository,
                          int findDepth) {
        this.appRunnerService = appRunnerService;
        this.appsLocaleRepository = appsLocaleRepository;
        this.mainDirectoriesRepository = mainDirectoriesRepository;
        this.findAppPathService = findAppPathService;
        this.propertiesRepository = propertiesRepository;
        this.findDepth = findDepth;;
    }


    public void setFindDepth(int newFindDepth) {
        findDepth = newFindDepth;
    }

    public int getFindDepth() {
        return findDepth;
    }

    protected void unknown(Command command) {
        ConsoleOutput.error("Unknown command: " + command);
    }

    protected void run(String[] args) {
        AppLocale locale = appsLocaleRepository.findByName(args[0]);
        if (locale == null) {
            find(args);
            locale = appsLocaleRepository.findByName(args[0]);
        }

        String path = locale.getPath();

        appRunnerService.run(path);
    }

    protected void prun(String[] args) {
        appRunnerService.run(args[0]);
    }

    protected void lrun(String[] args) {
        AppLocale locale = appsLocaleRepository.findByName(args[0]);
        String path = locale.getPath();
        appRunnerService.run(path);
    }

    protected void kill(String[] args) {
        AppLocale locale = appsLocaleRepository.findByName(args[0]);
        if (locale == null) {
            find(args);
            locale = appsLocaleRepository.findByName(args[0]);
        }

        String path = locale.getPath();

        appRunnerService.kill(path);
    }

    protected void pkill(String[] args) {
        appRunnerService.kill(args[0]);
    }

    protected void lkill(String[] args) {
        AppLocale locale = appsLocaleRepository.findByName(args[0]);
        String path = locale.getPath();
        appRunnerService.kill(path);
    }

    protected void path(String[] args) {
        AppLocale locale = appsLocaleRepository.findByName(args[0]);
        if (locale == null) {
            ConsoleOutput.error("Variable do not exist: " + args[0]);
        } else {
            ConsoleOutput.print("Path", locale.getPath(), Color.CYAN);
        }
    }

    protected void find(String[] args) {
        String result = findAppPathService.findPath(StringTools.append(args), findDepth);
        if (result != null) {
            ConsoleOutput.success(result);
        } else {
            ConsoleOutput.error("Path not found: " + args[0]);
        }
    }

    protected void findDepth(String[] args) {
        findDepth = Integer.parseInt(args[0]);
        propertiesRepository.save("find_depth=" + findDepth);
    }

    protected void repoSave(String[] args) {
        appsLocaleRepository.save(new AppLocale(
                args[1], StringTools.append(args, 2, args.length)));
        ConsoleOutput.success();
    }

    protected void repoRead() {
        appsLocaleRepository.read();
        repoList();
    }

    protected void repoFind(String[] args) {
        ConsoleOutput.info(appsLocaleRepository.findByName(args[1]).toString());
    }

    protected void repoList() {
        ConsoleOutput.printHeader("Locales", Color.PURPLE);
        ConsoleOutput.printLine(Color.PURPLE);
        for (AppLocale locale : appsLocaleRepository.getLocales()) {
            System.out.println(locale.toString());
            ConsoleOutput.printLine(Color.PURPLE);
        }
        System.out.println();
    }

    protected void repoDirsSave(String[] args) {
        mainDirectoriesRepository.save(StringTools.append(args, 1, args.length));
        ConsoleOutput.success();
    }

    protected void repoDirsRead(String[] args) {
        ConsoleOutput.info(mainDirectoriesRepository.getMainDirs().toString());
    }

    protected void rename(String[] args) {
        appsLocaleRepository.changeName(args[0], args[1]);
        repoList();
    }

    protected void copy(String[] args) {
        AppLocale locale = appsLocaleRepository.findByName(args[0]);
        if (locale == null) {
            ConsoleOutput.error("Variable do not exist: " + args[0]);
        } else {
            AppLocale copy = new AppLocale(locale.getName() + "Copy", locale.getPath());
            appsLocaleRepository.save(copy);
            repoList();
        }
    }

    protected void isVoiceAlive() {
        Thread t1 = ThreadTools.findThreadByName("SpeechToText");
        if (t1 != null) {
            ConsoleOutput.info(String.valueOf(t1.isAlive()));
        } else {
            ConsoleOutput.info(String.valueOf(false));
        }
    }

    protected void changeName(String[] args) {
        propertiesRepository.save("name=" + StringTools.append(args, 0, args.length));
    }

    protected void commands() {
        ConsoleOutput.print("commands", GenerateCommandsList.generate(), Color.CYAN);
        GenerateCommandsList.generateFile("commands.txt");
    }

    protected void type(String text) {
        int speed = propertiesRepository.getTypeSpeed();
        VoiceToKeyboard.type(text, speed);
    }

    protected void typeIn(String[] args) {
        try {
            int time = Integer.parseInt(args[0]);
            Thread.sleep(time);
            type(StringTools.append(args, 1, args.length));
        } catch (InterruptedException e) {
            ConsoleOutput.error(e.getMessage());
        } catch (Exception e) {
            type(StringTools.append(args));
        }
    }

    protected void setTypeSpeed(String[] args) {
        int speed = Integer.parseInt(args[0]);
        propertiesRepository.setTypeSpeed(speed);
    }

    protected void wait(String[] args) {
        long time = Long.parseLong(args[0]);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            ConsoleOutput.error(e.getMessage());
        }
    }
}
