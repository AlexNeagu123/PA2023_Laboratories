package com.lab2.compulsory.exceptions;
public class InvalidLength extends Exception {
    public InvalidLength() {
        super("The length of the road should be at least equal to the euclidean distance between endpoints");
    }
}