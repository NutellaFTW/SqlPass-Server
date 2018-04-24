package me.nutella.sqlpass.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {

    public String name;

    public File log;

    public Log(String name) {
        this.name = name;
        File logs = new File("logs");
        if(!logs.exists())
            Utils.sendInfo("Created Log Directory: " + logs.mkdir());
        log = new File("logs", name + "_error.txt");
    }

    public Log addStackTrace(StackTraceElement[] stackTraceElements, String cause) {

        try {

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(log));

            StringBuilder stringBuilder = new StringBuilder("Caused by: " + cause + "\r\n");

            for (StackTraceElement element : stackTraceElements)
                stringBuilder.append("\tat ").append(element).append("\r\n");

            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
            bufferedWriter.close();


        } catch (IOException e) {
            Utils.sendError("Could not write to log.");
        }

        return this;

    }

    public void build() {
        if(!log.exists())
            try {
                log.createNewFile();
            } catch (IOException e) {
                Utils.sendError("Could not create log.");
            }
    }

}
