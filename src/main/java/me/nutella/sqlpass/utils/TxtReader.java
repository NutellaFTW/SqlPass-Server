package me.nutella.sqlpass.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class TxtReader {

    private File file;

    public List<String> lines;

    public TxtReader(File file) {
        this.file = file;
        deconstruct();
    }

    private void deconstruct() {
        try(Stream<String> stringStream = Files.lines(Paths.get(file.getName()))) {
            stringStream.forEach(string -> lines.add(string));
        } catch (IOException e) {
            Utils.sendError("Could not read file.", e.getStackTrace(), e.getCause().getMessage());
        }
    }

}
