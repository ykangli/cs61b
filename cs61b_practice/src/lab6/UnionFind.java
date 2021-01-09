package lab6;

public class UnionFind {
    private int[] parent;
    private int[] size;

    // Creates a UnionFind data structure holding n vertices. Initially, all vertices are in disjoint sets.
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    // Throws an exception if v1 is not a valid index.
    public void validate(int v1) throws Exception {
        if (v1 < 0 || v1 >= parent.length) {
            throw new Exception("v1 is not a valid index");
        }
    }

    // Returns the size of the set v1 belongs to.
    public int sizeOf(int v1) {
        int root = find(v1);
        return size[root];
    }

    // Returns the parent of v1. If v1 is the root of a tree, returns the negative size of the tree for which v1 is the root.
    public int parent(int v1) {
        return parent[v1];
    }

    // Returns true if nodes v1 and v2 are connected.
    public boolean connected(int v1, int v2) {
        int rootV1 = find(v1);
        int rootV2 = find(v2);
        return rootV1 == rootV2;
    }

    // Connects two elements v1 and v2 together.
    public void union(int v1, int v2) throws Exception {
        validate(v1);
        validate(v2);
        int rootV1 = find(v1);
        int rootV2 = find(v2);
        if (size[rootV1] <= size[rootV2]) {
            parent[rootV1] = rootV2;
            size[rootV2] += size[rootV1];
        } else {
            parent[rootV2] = rootV1;
            size[rootV1] += size[rootV2];
        }
    }

    // Returns the root of the set v1 belongs to. Path-compression is employed allowing for fast search-time.
    public int find(int v1) {
        assert (v1 >= 0 && v1 < parent.length);

        if (v1 != parent[v1]) {
            parent[v1] = find(parent[v1]);
        }
        return parent[v1];
    }

    public static void main(String[] args) throws Exception {
        UnionFind a = new UnionFind(5);
        a.union(1,2);
        a.find(1);
        System.out.println(a.connected(2,3));
        System.out.println(a.parent(1));
    }
}
