package com.marnixbarendregt.gamelib.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marnixbarendregt on 19/09/2017.
 */
public class File {

    public static String readText(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get("src/main/" + path)));
        } catch (IOException e) {
            System.err.println("Couldn't read file " + path);
        }

        return "";
    }

    public static List<String> readLines(String path) {
        try {
            return new ArrayList<String>(Files.readAllLines(Paths.get("src/main/" + path)));
        } catch (IOException e) {
            System.err.println("Couldn't read file " + path);
        }

        return new ArrayList<String>();
    }
}

