package lk.ac.cmb.ucsc.utils.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class FileLogger extends Logger {
    private static Logger logger = null;

    protected FileLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public static Logger getLogger() {
        try {
            if (logger == null) {
                logger = Logger.getLogger("application");
                final var fileHandler = new FileHandler("application.log");
                fileHandler.setFormatter(new FileLogFormatter());
                logger.addHandler(fileHandler);
                logger.setUseParentHandlers(false);
            }
        } catch (IOException e) {
            logger.severe("Failed to create log file: " + e.getMessage());
        }
        return logger;
    }
}
