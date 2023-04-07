package com.geometry;

import lombok.Data;

import java.io.Serializable;

@Data
public class Triangle implements Serializable {
    private Line[] lines;
    private Point[] points;

    public Triangle(Point firstPoint, Point secondPoint, Point thirdPoint) {
        points = new Point[]{
                firstPoint, secondPoint, thirdPoint
        };
        lines = new Line[]{
                new Line(firstPoint, secondPoint),
                new Line(firstPoint, thirdPoint),
                new Line(secondPoint, thirdPoint)
        };
    }
}
