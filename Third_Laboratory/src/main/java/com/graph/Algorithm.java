package com.graph;

/**
 * The <tt>Algorithm</tt> class provides a skeletal implementation for different graph related algorithms that may be
 * implemented on several <tt>Network</tt> instances.
 * <p>
 * {@link TarjanAlgorithm} class is inherited from this class.
 */
public abstract class Algorithm {
    protected Network network;
    protected int nodeCount;
    protected final boolean[] visited;

    /**
     * @param network The {@link Network} object that will be used by the algorithm as a <b>Graph Problem Instance</b>
     */
    public Algorithm(Network network) {
        this.network = network;
        this.nodeCount = network.getNodeCount();
        this.visited = new boolean[nodeCount];
    }
}
