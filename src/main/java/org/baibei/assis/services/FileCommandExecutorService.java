package org.baibei.assis.services;

import org.baibei.assis.obj.Command;
import org.baibei.tools.FileTools;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileCommandExecutorService {

    public static List<Command> execute(String path) {
        List<Command> commands = new ArrayList<Command>();
        List<String> lines = FileTools.readFile(path);

        for (String line : lines) {
            Command command = Command.createFull(line);
            commands.add(command);
        }

        return commands;
    }
}
