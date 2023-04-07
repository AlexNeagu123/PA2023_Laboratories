package com.geometry;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * The <tt>Line</tt> class represents a line between two distinct {@link Point} objects.
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Line implements Serializable {
    Point x;
    Point y;

    /**
     * Gets a scaled distance a specified point to this line using cross product properties
     *
     * @param point A specified {@link Point} object
     * @return A scaled distance between the specified {@code point} and this line
     */
    public int getCloseness(Point point) {
        Point firstVector = x.getVectorTo(y);
        Point secondVector = x.getVectorTo(point);
        return Math.abs(firstVector.crossProduct(secondVector));
    }

    public Line reversedLine() {
        return new Line(y, x);
    }
}
