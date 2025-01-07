package org.baibei.assis.obj;

import org.baibei.console.ConsoleOutput;
import org.baibei.tools.StringTools;

import java.util.Arrays;

public class Command {

    private final String command;
    private String commandType;
    private String[] args;

    public Command(String command) {
        this.command = command;
    }

    public void handle() {
        String[] parts = command.split(" ");
        commandType = parts[0];
        args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);
    }

    public static Command createFull(String command) {
        Command cmd = new Command(command);
        cmd.handle();

        if (cmd.commandType.isEmpty()) {
            ConsoleOutput.error("Command type is empty");
        }

        return cmd;
    }

    public static Command create(String command) {
        command = StringTools.replaceNums(command);

        Command cmd = createFull(command);

        if (cmd.getArgs()[0].length() == 1) {
            String word = StringTools.appendLetters(cmd.getArgs(), 0, cmd.getArgs().length);
            String[] args;
            args = Arrays.copyOfRange(cmd.getArgs(), 1, cmd.getArgs().length);
            args[0] = word;
            cmd.setArgs(args);
        } else {
            try {
                String word = StringTools.appendInOne(cmd.getArgs(), 0, cmd.getArgs().length);
                String[] args;
                args = Arrays.copyOfRange(cmd.getArgs(), 1, cmd.getArgs().length);
                args[0] = word;
                cmd.setArgs(args);
            } catch (Exception e) {
                //
            }
        }

        return cmd;
    }

    public String getCommand() {
        return command;
    }

    public String getCommandType() {
        return commandType;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String toString() {
        return commandType + " with args " + Arrays.toString(args);
    }
}
