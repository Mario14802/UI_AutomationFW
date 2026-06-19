package com.automationExercise.utils.logsmanager;

import com.automationExercise.report.AllureConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogsManager {
    public static final String LOGS_PATH = AllureConstants.USER_DIR + "/test-output/Logs/";

    // private constructor to prevent object creation
    private LogsManager() {
    }

    // get logger dynamically based on caller class
    private static Logger logger() {
        return LogManager.getLogger(
                Thread.currentThread().getStackTrace()[3].getClassName()
        );
    }

    // info logs
    public static void info(String... message) {
        logger().info(String.join(" ", message));
    }

    // error logs
    public static void error(String... message) {
        logger().error(String.join(" ", message));
    }

    // warning logs
    public static void warn(String... message) {
        logger().warn(String.join(" ", message));
    }

    // fatal logs
    public static void fatal(String... message) {
        logger().fatal(String.join(" ", message));
    }

    // debug logs
    public static void debug(String... message) {
        logger().debug(String.join(" ", message));
    }
}