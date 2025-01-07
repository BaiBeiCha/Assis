package org.baibei.assis.repositories;

import org.baibei.assis.obj.AppLocale;
import org.baibei.console.ConsoleOutput;
import org.baibei.tools.FileTools;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AppsLocaleRepository {

    private List<AppLocale> locales;
    private String repoPath;

    public AppsLocaleRepository() {
        this.locales = new ArrayList<AppLocale>();
        this.repoPath = "locales.txt";
        FileTools.createFile(repoPath);
        read();
    }

    public AppsLocaleRepository(String repoPath) {
        this.locales = new ArrayList<>();
        this.repoPath = repoPath;
        FileTools.createFile(repoPath);
        read();
    }

    public void read() {
        List<String> lines = FileTools.readFile(repoPath);

        for (String line : lines) {
            if (line.contains(" : ")) {
                String[] args = line.trim().split(" : ");
                AppLocale locale = new AppLocale(args[0], args[1]);
                this.locales.add(locale);
            }
        }
    }

    public void save(AppLocale locale) {
        if (locale.getName().contains(".exe")) {
            locale.setName(locale.getName().replaceAll(".exe", "").toLowerCase());
        }

        this.locales.removeIf(locale1 -> locale1.getName().equals(locale.getName()));

        this.locales.add(locale);
        FileTools.writeFile(repoPath, locale.toString(), true);
    }

    public void save() {
        FileTools.clearFile(repoPath);
        for (AppLocale locale : this.locales) {
            FileTools.writeFile(repoPath, locale.toString(), true);
        }
    }

    public void changeName(String oldName, String newName) {
        AppLocale locale = findByName(oldName);
        if (locale == null) {
            ConsoleOutput.error("Variable do not exist: " + oldName);
        } else {
            locale.setName(newName);
            save();
        }
    }

    public AppLocale findByName(String name) {
        for (AppLocale locale : locales) {
            if (locale.getName().equals(name)) {
                return locale;
            }
        }

        return null;
    }

    public List<AppLocale> getLocales() {
        return locales;
    }

    public String getRepoPath() {
        return repoPath;
    }

    public void setRepoPath(String repoPath) {
        this.repoPath = repoPath;
    }

    public void setRepoPathAndRead(String repoPath) {
        this.repoPath = repoPath;
        read();
    }

    public void setRepoPathAndSave(String repoPath) {
        this.repoPath = repoPath;
        save();
    }
}
