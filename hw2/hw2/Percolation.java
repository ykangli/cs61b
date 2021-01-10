package hw2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.knowm.xchart.XYChart;

import java.util.HashSet;
import java.util.Set;
import java.util.SplittableRandom;

public class Percolation {
    private int N; // N * N system
    private boolean[] openGridIn1d; // store opened gird
    private int nOpen = 0; // the number of gird which is open
    private Set<Integer> virtualTopSite = new HashSet<>(); // Virtual top site connected to all open items in top row.
    private Set<Integer> virtualBottomSite = new HashSet<>(); // Virtual bottom site connected to all open items in bottom row.
    private boolean percolated = false; // system is nor not percolated
    private WeightedQuickUnionUF unionGrids; // connect grids

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N is illegal");
        }
        this.N = N;
        openGridIn1d = new boolean[N * N];
        for (int i = 0; i < N * N; i++) {
            openGridIn1d[i] = false;
        }
        unionGrids = new WeightedQuickUnionUF(N * N);
    }

    // write an xyTo1D(int r, int c) method, e.g. xyTo1D(2, 4) = 14, xyTo1D(3, 4) = 19
    private int xyTo1D(int row, int col) {
        int num = N * row + col;
        return num;
    }

    private boolean isValidIndex(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            return false;
        }
        return true;
    }

    // update connection between a grid and its neighbor
    private void updateConnection(int row, int col, int neighborRow, int neighborCol) {
        int indexIn1D = xyTo1D(row, col);
        // 当该grid是open，而且邻居是open，那么需要联通该grid和其邻居
        if (isValidIndex(neighborRow, neighborCol) && isOpen(neighborRow, neighborCol)) {
            int rootId = unionGrids.find(indexIn1D);
            int neighborIndexIn1D = xyTo1D(neighborRow, neighborCol);
            int neighborRootId = unionGrids.find(neighborIndexIn1D);
            unionGrids.union(indexIn1D, neighborIndexIn1D);
            int newRootId = unionGrids.find(neighborIndexIn1D);

            // 更新top site中存储的rootId
            if (virtualTopSite.contains(rootId)) {
                virtualTopSite.remove(rootId);
                virtualTopSite.add(newRootId);
            }
            if (virtualTopSite.contains(neighborRootId)) {
                virtualTopSite.remove(neighborRootId);
                virtualTopSite.add(newRootId);
            }

            //更新bottom site中存储的rootId
            if (virtualBottomSite.contains(rootId)) {
                virtualBottomSite.remove(rootId);
                virtualBottomSite.add(newRootId);
            }
            if (virtualBottomSite.contains(neighborRootId)) {
                virtualBottomSite.remove(neighborRootId);
                virtualBottomSite.add(newRootId);
            }
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isValidIndex(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        int indexIn1D = xyTo1D(row, col);
        if (openGridIn1d[indexIn1D]) {
            return;
        }
        nOpen += 1;
        openGridIn1d[indexIn1D] = true;
        int rootId = unionGrids.find(indexIn1D);
        // Add grid's rootId to virtualTopSite if it's in the top layer,once grid's rootId is added in virtualTopSite(a set)
        // Then should not add same rootId,just focus on connect gird using weightquickunionuf
        if (indexIn1D < N) {
            virtualTopSite.add(rootId);
        }
        if (N * N - indexIn1D <= N) {
            virtualBottomSite.add(rootId);
        }
        // 将该grid设置为open后，检查上下左右，看是否有grid是open，更新与邻居之间的连通性
        // Update connection and full conditions both from top and bottom with neighbors around
        updateConnection(row, col, row - 1, col); // On the north
        updateConnection(row, col, row, col + 1); // On the east
        updateConnection(row, col, row + 1, col); // On the south
        updateConnection(row, col, row, col - 1); // On the west

        int newRootId = unionGrids.find(indexIn1D);
        if (virtualTopSite.contains(newRootId) && virtualBottomSite.contains(newRootId)) {
            percolated = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValidIndex(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        int indexIn1D = xyTo1D(row, col);
        return openGridIn1d[indexIn1D];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isValidIndex(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        int indexIn1D = xyTo1D(row, col);
        return virtualTopSite.contains(unionGrids.find(indexIn1D)); // 该格子是否与top site连通，如果连通就是isfull
    }

    // number of open sites
    public int numberOfOpenSites() {
        return nOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolated;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        // 有bug？？？？
        Percolation system = new PercolationFactory().make(3);
        system.open(0,0);
        system.open(1,0);
        system.open(2,0);
        System.out.println(system.percolates());
    }
}
