package com.utils;

import com.geometry.Line;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TriangleStatus implements Serializable {
    public int ownLines;
    public int opponentLines;
    public int nonExistentLines;
    public List<Line> freeLines;

    public TriangleStatus() {
        freeLines = new ArrayList<>();
    }
}
