package org.baibei.assis.services;

import org.baibei.console.ConsoleOutput;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AppRunnerService {

    public Process run(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            ConsoleOutput.success();
            return process;
        } catch (IOException e) {
            ConsoleOutput.error(e.getMessage());
            return null;
        }
    }

    public void kill(String processName) {
        List<ProcessHandle> appProcess =
                ProcessHandle.allProcesses()
                        .filter(process -> process
                                .info()
                                .command()
                                .orElse("")
                                .toLowerCase()
                                .contains(processName.toLowerCase()))
                        .toList();
        
        for (ProcessHandle process : appProcess) {
            process.destroyForcibly();
        }

        ConsoleOutput.success();
    }

    public void find(String processName) {

    }
}
