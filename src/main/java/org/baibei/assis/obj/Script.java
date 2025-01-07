package org.baibei.assis.obj;

import org.baibei.assis.services.FileCommandExecutorService;
import org.baibei.tools.FileTools;

import java.io.File;
import java.util.List;

public class Script {

    private String name;
    private String path;
    private String code;
    private List<Command> scripts;

    public Script(File file) {
        this.name = file.getName().replace(".txt", "");

        this.path = file.getAbsolutePath();
        if(path.contains("scripts")) {
            path = path.split("scripts")[1];
            path = "scripts" + path;
        }

        code = FileTools.readFileAsString(path);
    }

    public Script(String name, String path, String code) {
        this.name = name;
        this.path = path;
        this.code = code;
    }

    public Script(String name, String code) {
        this.name = name;
        this.code = code;
        this.path = "scripts\\" + name + ".txt";
    }

    public void make() {
        this.scripts = FileCommandExecutorService.execute(path);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Command> getScripts() {
        return scripts;
    }

    public void setScripts(List<Command> scripts) {
        this.scripts = scripts;
    }

    public void addScript(Command script) {
        this.scripts.add(script);
    }

    public void removeScript(Command script) {
        this.scripts.remove(script);
    }

    public void removeAllScripts() {
        this.scripts.clear();
    }

    public Command getScript(int index) {
        return this.scripts.get(index);
    }

    public String toString() {
        return name + " : " + path;
    }
}
