import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] xArray;
    private final int t;

    public PercolationStats(int n, int trails) {
        xArray = new double[trails];
        t = trails;
        for (int i = 0; i < trails; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            xArray[i] = percolation.numberOfOpenSites()/(double)(n*n);
        }
    }

    public double mean() {
        return StdStats.mean(xArray);
    }

    public double stddev() {
        return StdStats.stddev(xArray);
    }

    public double confidenceLo() {
        double x = StdStats.mean(xArray);
        double s = StdStats.stddev(xArray);
        return (x - (s*1.96)/Math.sqrt(t));
    }

    public double confidenceHi() {
        double x = StdStats.mean(xArray);
        double s = StdStats.stddev(xArray);
        return (x + (s*1.96)/Math.sqrt(t));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trails);
        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = " + "[" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }

}

