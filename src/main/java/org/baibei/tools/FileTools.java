package org.baibei.tools;

import org.baibei.assis.obj.AppLocale;
import org.baibei.console.ConsoleOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileTools {

    public static void createFile(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            ConsoleOutput.error("Can't create file");
        }
    }

    public static void createFile(File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            ConsoleOutput.error("Can't create file");
        }
    }

    public static void writeFile(String path, String content, boolean append) {
        try(FileWriter writer = new FileWriter(path, append))
        {
            writer.write(content + "\n");
            writer.flush();
        }
        catch(Exception e){
            ConsoleOutput.error("Can't write file");
        }
    }

    public static void writeFile(String path, String[] content, boolean append) {
        try(FileWriter writer = new FileWriter(path, append))
        {
            for(String s : content) {
                writer.write(s + "\n");
            }
            writer.flush();
        }
        catch(Exception e){
            ConsoleOutput.error("Can't write file");
        }
    }

    public static List<String> readFile(String path) {
        List<String> list = new ArrayList<>();

        try(Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNextLine()) {
                list.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            ConsoleOutput.error(e.getMessage());
        }

        return list;
    }

    public static void clearFile(String path) {
        writeFile(path, "", false);
    }

    public static String getUser() {
        return System.getProperty("user.name");
    }
}
