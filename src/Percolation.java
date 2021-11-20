import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] openStatus;
    private final int size;
    private final int virtualBottom;
    private final int virtualTop;
    private final WeightedQuickUnionUF weightedQuickUnion;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n has to be in range 1-n");
        }
        size = n;
        openStatus = new boolean[n*n];
        weightedQuickUnion = new WeightedQuickUnionUF(n*n + 2);
        virtualBottom = n*n;
        virtualTop = n*n + 1;

        for (int i = 0; i < openStatus.length; i++) {
            openStatus[i] = false;
        }
    }

    private int getSite(int row, int col) {
        int site = (size * (row-1)) + (col - 1);
        return site;
    }

    public void open(int row, int col) {
        int right;
        int left;
        int top;
        int bottom;
        int site = getSite(row, col);
        right = getSite(row, col+1);
        left = getSite(row, col-1);
        top = getSite(row-1, col);
        bottom = getSite(row + 1, col);
        int connectTop = getSite(1, col);
        int connectBottom = getSite(size, col);

        if (!(openStatus[site])) {
            openStatus[site] = true;
            if (col < size && openStatus[right]) {
                weightedQuickUnion.union(site, right);
            }
            if (col > 1 && openStatus[left]) {
                weightedQuickUnion.union(site, left);
            }
            if (row > 1 && openStatus[top]) {
                weightedQuickUnion.union(site, top);
            } else if (row == 1 && openStatus[connectTop]) {
                weightedQuickUnion.union(virtualTop, site);
            }
            if (row < size && openStatus[bottom]) {
                weightedQuickUnion.union(site, bottom);
            } else if (row == size && openStatus[connectBottom]) {
                weightedQuickUnion.union(virtualBottom, site);
            }
        }

    }

    public boolean isOpen(int row, int col) {
        int site = getSite(row, col);
        if (openStatus[site]) {
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        int site = getSite(row, col);
        if (weightedQuickUnion.find(site) == weightedQuickUnion.find(virtualTop)) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        int numberOfOpenSites = 0;
        for (int i = 0; i < openStatus.length; i++) {
            if (openStatus[i]) {
                numberOfOpenSites++;
            }
        }

        return numberOfOpenSites;
    }

    public boolean percolates() {
        if (weightedQuickUnion.find(virtualBottom) == weightedQuickUnion.find(virtualTop)) {
            return true;
        }
        return false;
    }

}
