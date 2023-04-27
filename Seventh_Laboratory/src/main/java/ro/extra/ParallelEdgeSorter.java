package ro.extra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/**
 * The <tt>ParallelEdgeSorter</tt> class implements a concurrent Merge Sort.
 */
@AllArgsConstructor
@Log4j2
public class ParallelEdgeSorter implements Runnable {
    private List<Edge> unorderedEdges;
    @Getter
    private volatile List<Edge> orderedEdges;

    public ParallelEdgeSorter(List<Edge> unorderedEdges) {
        this.unorderedEdges = unorderedEdges;
        this.orderedEdges = new ArrayList<>();
    }

    private List<Edge> mergeHalves(List<Edge> firstHalf, List<Edge> secondHalf) {
        int i = 0, j = 0;
        int n = firstHalf.size(), m = secondHalf.size();
        List<Edge> mergedEdges = new ArrayList<>();
        while (i < n && j < m) {
            Edge firstEdge = firstHalf.get(i);
            Edge secondEdge = secondHalf.get(j);
            if (firstEdge.getCost() <= secondEdge.getCost()) {
                mergedEdges.add(firstEdge);
                i++;
            } else {
                mergedEdges.add(secondEdge);
                j++;
            }
        }
        while (i < n) {
            mergedEdges.add(firstHalf.get(i));
            i++;
        }
        while (j < m) {
            mergedEdges.add(secondHalf.get(j));
            j++;
        }
        return mergedEdges;
    }

    @Override
    public void run() {
        if (unorderedEdges.size() <= 1) {
            orderedEdges = unorderedEdges;
            return;
        }

        int midPoint = unorderedEdges.size() / 2;
        ParallelEdgeSorter leftSorter = new ParallelEdgeSorter(unorderedEdges.subList(0, midPoint));
        ParallelEdgeSorter rightSorter = new ParallelEdgeSorter(unorderedEdges.subList(midPoint, unorderedEdges.size()));

        Thread leftThread = new Thread(leftSorter);
        Thread rightThread = new Thread(rightSorter);
        leftThread.start();
        rightThread.start();

        try {
            leftThread.join();
            rightThread.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        orderedEdges = mergeHalves(leftSorter.getOrderedEdges(), rightSorter.getOrderedEdges());
    }
}
