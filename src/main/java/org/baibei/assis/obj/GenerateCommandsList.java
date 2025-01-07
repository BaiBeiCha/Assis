package org.baibei.assis.obj;

import org.baibei.tools.FileTools;

public class GenerateCommandsList {

    public static String generate() {
        return """
               "?", "commands", "help" -> print with information about the commands list
               also create the "commands.txt" file
               
               "run [name]" -> run a program
               "prun [path]" -> run a program by the path
               "lrun [name]" -> run a program by the variable from the "locales.txt" file
               
               "kill [name]", "close" -> close a program
               "pkill [path]" -> pkill a program by the path
               "lkill [name]" -> pkill a program by the variable from the "locales.txt" file
               
               "path [name]" -> path to the variable from the "locales.txt" file
               "find [name]" -> find a program and add to the "locales.txt" file
               "rename [old_name] [new_name]" -> rename the variable from the "locales.txt" file
               "copy [name]" -> copy a program and add to the "locales.txt" file, will be named: nameCopy
               
               "isVoiceAlive" -> print is Speech-to-Text model active
               "voiceStop" -> stop Speech-to-Text model
               "currentLoc" -> print current language of Speech-to-Text model
               "loc [name]" -> set language of Speech-to-Text model
               Languages:
                en -> English
                ru -> Russian
               
               "type [text]" -> types text in the active window
               "typeIn [time] [text]" -> types text in the active window after time
               
               "file [subcommand]":
                "add [name] [path]" -> add variable in the "locales.txt" file
                "addRun [name] [path]" -> add variable in the "locales.txt" file and run it
                "addDRun [name] [path]" -> add variable in the "locales.txt" file, decrypt and run it
                "run [name/path]" -> run commands list from this file
                "drun [name/path]" -> decrypt and run commands list from this file
                "encrypt [name/path]" -> encrypt commands list in this file
                "decrypt [name/path]" -> decrypt commands list in this file
               
               "changeName [name]" -> change the name of Speech-to-Text model in the "properties.txt" file
               "findDepth", "find_depth", "find-depth", "finddepth", "fd [amount]" ->
               change the How many subfolders could check the program when searching file;
               in the "properties.txt" file
               "typeSpeed [time]" -> time (ms) between typing keys save into the "properties.txt" file
               
               "repo [subcommand]":
                "save" -> save all variables in the "locales.txt" file
                "read" -> read all variables in the "locales.txt" file
                "find [name]" -> the same as "path [name]", but without checking is variable exists
                "repo list" -> print variables in the "locales.txt" file
                "dirs [subcommand]":
                 "save [path]" -> save directory where to search files in the "dirs.txt" file
                 "read" -> print directories where to search files from the "dirs.txt" file
               
               "scripts [subcommand]":
                "list" -> print scripts in the "locales.txt" file
                "add [name] [path]" -> add variable in the "locales.txt" file
                "run [name]" -> same as "file run [name]"
                "update" -> check "scripts" folder
               
               "wait [time]" -> stops program for a time
               "exit" -> exit the program""";
    }

    public static void generateFile(String path) {
        FileTools.createFile(path);
        FileTools.writeFile(path, generate(), false);
    }
}
