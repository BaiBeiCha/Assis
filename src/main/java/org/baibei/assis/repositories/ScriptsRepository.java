package org.baibei.assis.repositories;

import org.baibei.assis.obj.AppLocale;
import org.baibei.assis.obj.Script;
import org.baibei.tools.FileTools;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScriptsRepository {

    private List<Script> scripts;
    private String repoPath;

    private final AppsLocaleRepository appsLocaleRepository;

    public ScriptsRepository(AppsLocaleRepository appsLocaleRepository) {
        this.appsLocaleRepository = appsLocaleRepository;
        this.scripts = new ArrayList<>();
        this.repoPath = "scripts\\";
        FileTools.createDirectory(this.repoPath);
        read();
    }

    public void read() {
        File repo = new File(repoPath);
        if (repo.exists()) {
            File[] files = repo.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    scripts.add(new Script(file));
                }
            }
        } else {
            FileTools.createFile(repoPath);
            read();
        }
    }

    public void write() {
        for (Script script : scripts) {
            if (appsLocaleRepository.findByName(script.getName()) == null) {
                appsLocaleRepository.save(new AppLocale(script.getName(), script.getPath()));
            }
        }
    }

    public void save(String path, String name) {
        FileTools.createFile(repoPath + name + ".txt");
        String code = FileTools.readFileAsString(path);
        FileTools.writeFile(repoPath + name + ".txt", code, false);

        this.scripts.removeIf(script -> script.getName().equals(name));
        this.scripts.add(new Script(name, path, code));
    }

    public void update() {
        read();
        write();
    }

    public List<Script> find() {
        return scripts;
    }
}
