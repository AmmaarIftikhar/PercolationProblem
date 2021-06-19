import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

/* Ammaar Iftikhar */
public class PercolationStats
{
   // FIELDS
   private static final double CONS = 1.96;
   private final double[] thresholds;
   
   // CONSTRUCTOR 
   // perform independent trials on an n-by-n grid
   public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        // Initialisation
        int rowSize = n;  
        thresholds = new double[trials];

        for (int trial = 0; trial < thresholds.length; trial++) {
            Percolation percTest = new Percolation(rowSize);
            while (!percTest.percolates()) {
                int row = StdRandom.uniform(1, rowSize + 1);
                int col = StdRandom.uniform(1, rowSize + 1);
                percTest.open(row, col);
            }
            int openSites = percTest.numberOfOpenSites();
            double result = (double) openSites / (rowSize * rowSize);
            thresholds[trial] = result;
        }
        
    }

    // METHODS
    // sample mean of percTest threshold
    public double mean()
    {
       return StdStats.mean(thresholds);
    }

    // sample standard deviation of percTest threshold
    public double stddev()
    {
       return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
       return mean() - ((CONS * stddev()) / Math.sqrt(thresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
       return mean() + ((CONS * stddev()) / Math.sqrt(thresholds.length));
    }

   // test client (see below)
   public static void main(String[] args) {
        int rowSize = 10;
        int trialCount = 10;
        if (args.length >= 2) {
            rowSize = Integer.parseInt(args[0]);
            trialCount = Integer.parseInt(args[1]);
        }
        PercolationStats ps = new PercolationStats(rowSize, trialCount);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.printf("Mean                   = %.2f\n", ps.mean());
        StdOut.printf("Standard Deviation     = %.2f\n", ps.stddev());
        StdOut.println("95% confidence interval= " + confidence);
    }
}