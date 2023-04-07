package com.exceptions;

public class InvalidHTMLFile extends Exception {
    public InvalidHTMLFile(String filePath) {
        super("The html file at path " + filePath + " can't be opened!");
    }
}
