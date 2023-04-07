package com.problem;

import com.entities.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The <tt>NodeColorPair</tt> class represents a pair consisting of a {@link Document} object and an Integer number representing
 * it color.
 * Multiple <tt>NodeColorPair</tt> elements form a graph coloring.
 */
@Getter
@Setter
@AllArgsConstructor
public class NodeColorPair {
    private Document document;
    private int color;

    @Override
    public String toString() {
        return "Document with id " + document.getId() + " colored with " + color;
    }
}

