package ro.extra;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Edge {
    private int from;
    private int to;
    private double cost;
}
