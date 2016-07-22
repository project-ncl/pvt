package org.jboss.pvt2.log;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created by yyang on 7/11/16.
 */
public class PVTLogger {

    public static Logger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }

    public static Logger getLogger(String name) {
        Logger log = Logger.getLogger(name);
        log.setLevel(Level.ALL);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        log.addHandler(consoleHandler);

        try {
            FileHandler fileHandler = new FileHandler("pvt2.log");
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new MyLogHander());
            log.addHandler(fileHandler);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return log;
    }
}

class MyLogHander extends Formatter {
    @Override
    public String format(LogRecord record) {
        return record.getLevel() + ":" + record.getMessage() + "\n";
    }
}