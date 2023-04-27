package ro.extra;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParallelEdgeSorterTest {
    @Test
    public void shouldSortAnEdgeListOf1000EdgesCorrectly() throws InterruptedException {
        List<Edge> orderedEdges = new ArrayList<>();
        for (int i = 0; i < 1000; ++i) {
            orderedEdges.add(new Edge(0, 1, i));
        }

        List<Edge> unorderedEdges = new ArrayList<>(orderedEdges);
        Collections.shuffle(unorderedEdges);

        ParallelEdgeSorter parallelEdgeSorter = new ParallelEdgeSorter(unorderedEdges);
        Thread t1 = new Thread(parallelEdgeSorter);

        t1.start();
        t1.join();

        Assert.assertEquals(parallelEdgeSorter.getOrderedEdges(), orderedEdges);
    }
}