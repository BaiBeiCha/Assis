package org.baibei.assis.obj;

public class AppLocale {

    private String name;
    private String path;

    public AppLocale(String name) {
        this.name = name;
    }

    public AppLocale(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public static AppLocale getLocale(String info) {
        String[] args = info.split(" : ");
        return new AppLocale(args[0].trim(), args[1].trim());
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

    public String toString() {
        return name + " : " + path;
    }
}
