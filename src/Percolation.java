import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[] openStatus;
    private int size;
    private int virtualBottom;
    private int virtualTop;
    private WeightedQuickUnionUF weightedQuickUnion;


    public Percolation(int n) {
        size = n;
        openStatus = new int[n*n];
        weightedQuickUnion = new WeightedQuickUnionUF(n*n + 2);
        virtualBottom = n*n;
        virtualTop = virtualBottom + 1;

        for (int i = 1; i < n; i ++) {
            int site = getSite(1, i);
            weightedQuickUnion.union(virtualTop, site);
            site = getSite(n, i);
            weightedQuickUnion.union(virtualBottom, site);
        }

        for (int i = 0; i < openStatus.length; i++) {
            openStatus[i] = 0; //initializing all sites blocked
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


        if (!(openStatus[site] == 1)) {
            openStatus[site] = 1;
            if (col < size && openStatus[right] == 1) { //open?
                weightedQuickUnion.union(site, right);
            }
            if (col > 1 && openStatus[left] == 1) {
                weightedQuickUnion.union(site, left);
            }
            if (row > 1 && openStatus[top] == 1) {
                weightedQuickUnion.union(site, top);
            }
            if (row < size && openStatus[bottom] == 1) {
                weightedQuickUnion.union(site, bottom);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        int site = getSite(row, col);
        if (openStatus[site] == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFull(int row, int col){
        int site = getSite(row, col);
        if (weightedQuickUnion.connected(site, virtualTop)) {
            return true;
        } else {
            return false;
        }
    }

    public int numberOfOpenSites() {
        int numberOfOpenSites = 0;
        for (int i = 0; i < openStatus.length; i ++) {
            if (openStatus[i] == 1) {
                numberOfOpenSites++;
            }
        }

        return numberOfOpenSites;
    }

    public boolean percolates() {
        if (weightedQuickUnion.connected(virtualBottom, virtualTop)) {
            return true;
        } else {
            return false;
        }
    }

}
