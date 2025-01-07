package org.baibei.assis.repositories;

import org.baibei.tools.FileTools;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.baibei.tools.FileTools.createFile;

@Repository
public class MainDirectoriesRepository {

    private List<String> mainDirs;
    private String repoPath;

    public MainDirectoriesRepository() {
        this.mainDirs = new ArrayList<String>();
        this.repoPath = "dirs.txt";
        createFile(repoPath);
        read();
    }

    public MainDirectoriesRepository(String repoPath) {
        this.mainDirs = new ArrayList<>();
        this.repoPath = repoPath;
        createFile(repoPath);
        read();
    }

    public void read() {
        mainDirs = FileTools.readFile(repoPath);
    }

    public void save(String dirPath) {
        mainDirs.add(dirPath);
        FileTools.writeFile(repoPath, dirPath, true);
    }

    public void save() {
        FileTools.writeFile(repoPath, mainDirs.toArray(new String[0]), false);
    }

    public List<String> getMainDirs() {
        return mainDirs;
    }

    public boolean isEmpty() {
        return mainDirs.isEmpty();
    }
}
