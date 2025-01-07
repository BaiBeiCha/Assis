package org.baibei.assis.services;

import org.baibei.assis.obj.AppLocale;
import org.baibei.assis.repositories.AppsLocaleRepository;
import org.baibei.assis.repositories.MainDirectoriesRepository;
import org.baibei.tools.FileTools;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FindAppPathService {

    private final MainDirectoriesRepository mainDirectoriesRepository;
    private final AppsLocaleRepository appsLocaleRepository;

    public FindAppPathService(MainDirectoriesRepository mainDirectoriesRepository,
                              AppsLocaleRepository appsLocaleRepository) {

        this.mainDirectoriesRepository = mainDirectoriesRepository;
        this.appsLocaleRepository = appsLocaleRepository;

        if (mainDirectoriesRepository.isEmpty()) {
            saveStdDirs();
        }
    }

    public String findPath(String appName, int maxDepth) {
        String[] dirs = mainDirectoriesRepository.getMainDirs().toArray(new String[0]);

        if (!appName.contains(".exe")) {
            appName += ".exe";
        }

        for (String dirPath : dirs) {
            try {
                File dir = new File(dirPath);
                String foundPath = searchInDirectory(dir, appName, maxDepth);
                if (foundPath != null) {
                    return foundPath;
                }
            } catch (Exception e) {
                // NOTHING :)
            }
        }

        return null;
    }

    private String searchInDirectory(File dir, String appName, int depth) {
        if (depth < 0 || !dir.isDirectory()) {
            return null;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                String foundPath = searchInDirectory(file, appName, depth - 1);
                if (foundPath != null) {
                    return foundPath;
                }
            } else {
                if (file.getName().equalsIgnoreCase(appName)) {
                    appsLocaleRepository.save(new AppLocale(file.getName(), file.getAbsolutePath()));
                    return file.getAbsolutePath();
                }
            }
        }

        return null;
    }

    private void saveStdDirs() {
        mainDirectoriesRepository.save("C:\\ProgramData\\");
        mainDirectoriesRepository.save("C:\\Program Files (x86)\\");
        mainDirectoriesRepository.save("C:\\Program Files\\");

        String user = FileTools.getUser();
        mainDirectoriesRepository.save("C:\\Users\\" + user + "\\AppData\\Local\\");
        mainDirectoriesRepository.save("C:\\Users\\" + user + "\\AppData\\LocalLow\\");
        mainDirectoriesRepository.save("C:\\Users\\" + user + "\\AppData\\Roaming\\");

        mainDirectoriesRepository.save("C:\\");
        mainDirectoriesRepository.save("D:\\");
        mainDirectoriesRepository.save("F:\\");
        mainDirectoriesRepository.save("G:\\");
    }
}
