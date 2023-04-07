package com.geometry;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * The <tt>Point</tt> class represents a cartesian point with (x, y) coordinates
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Point implements Serializable {
    int x;
    int y;

    /**
     * @param point The vector corresponding to another point
     * @return The cross product between the vector corresponding to this point and the other vector
     */
    public int crossProduct(Point point) {
        return x * point.y - y * point.x;
    }

    /**
     * @param point The head of the vector
     * @return The magnitude and direction of the vector that has its tail in this point and its head in the specified {@code point}
     */
    public Point getVectorTo(Point point) {
        return new Point(point.x - x, point.y - y);
    }
}
