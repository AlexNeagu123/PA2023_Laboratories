package ro.extra;

/**
 * The <tt>DisjointSetUnion</tt> class represents a <b>Disjoint Set Union</b> data structure.
 *
 * @see <a href="https://cp-algorithms.com/data_structures/disjoint_set_union.html">Disjoint Set Union</a>
 */
public class DisjointSetUnion {
    private final int[] parent;
    private final int[] size;

    public DisjointSetUnion(int nodeCount) {
        parent = new int[nodeCount];
        size = new int[nodeCount];
        for (int i = 0; i < nodeCount; ++i) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int getRoot(int node) {
        int root = node;
        while (root != parent[root]) {
            root = parent[root];
        }
        // Path Compression
        while (parent[node] != node) {
            int aux = parent[node];
            parent[node] = root;
            node = aux;
        }
        return root;
    }

    public boolean addEdge(int u, int v) {
        u = getRoot(u);
        v = getRoot(v);
        if (u == v) {
            return false;
        }
        if (size[u] > size[v]) {
            int aux = u;
            u = v;
            v = aux;
        }
        parent[u] = v;
        size[v] += size[u];
        return true;
    }
}
