package com.company;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class FileIO {

    public static void out (String file, ArrayList<String> output) {
        Path path = Paths.get(FileSystems.getDefault().getPath("Histories/" + file + ".txt").toString());
        ArrayList<String> content = new ArrayList<>();
        output.add("******************************");
        try {
            Files.write(path, output, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void create (String name) {
        Path parent = Paths.get(FileSystems.getDefault().getPath(".").toString());
        try {
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path path = Paths.get(FileSystems.getDefault().getPath("Histories/" + name + ".txt").toString());
        System.out.println(path);
        ArrayList<String> header = new ArrayList<>();
        header.add("******************************");
        header.add(String.format("History for user: %s", name));
        header.add("******************************");
        try {
            Files.write(path, header, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
