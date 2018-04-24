package me.nutella.sqlpass.utils;

import java.time.LocalTime;

public class Utils {

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void sendError(String error, StackTraceElement[] stackTrace, String cause) {
        System.out.println("[ERROR]: " + error + " (Logged Errors)");
        new Log(LocalTime.now().toString()).addStackTrace(stackTrace, cause).build();
    }

    public static void sendError(String error) {
        System.out.println("[ERROR]: " + error);
    }

    public static void sendInfo(String info) {
        System.out.println("[INFO]: " + info);
    }

}
