import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* Ammaar Iftikhar
 * 2021 
 * Bilkent University*/
public class Percolation {
   private boolean[][] network;
   private int openCount;
   final private WeightedQuickUnionUF grid;
   
   // blocked = false, open = true;
   // creates n-by-n grid, with all sites initially blocked
   public Percolation(int n) {
      if (n < 1) throw new IllegalArgumentException();
      grid = new WeightedQuickUnionUF(n * n + 2);
      openCount = 0;
      // array initialisation
      network = new boolean[n][n];
      
      // all array elements are blocked
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++)  network[i][j] = false;
      }
   }

    // opens the site (row, col) if it is not open already
   public void open(int row, int col) {
      if (row < 1 || col < 1 || row > network.length || col > network.length) 
         throw new IllegalArgumentException();
      
      if (!network[row - 1][col - 1]) {
         openCount++;
         network[row - 1][col - 1] = true;
         if (row < network.length && network[row][col - 1])         grid.union((network.length) * (row - 1) + col - 1, (network.length) * row + col - 1);
         if ((row - 1) > 0 && network[row - 2][col -  1])           grid.union((network.length) * (row - 1) + col - 1, (network.length) * (row - 2) + col - 1);
         if (col < network.length && network[row - 1][col])         grid.union((network.length) * (row - 1) + col - 1, (network.length) * (row - 1) + col);
         if ((col - 1) > 0 && network[row - 1][col - 2])            grid.union((network.length) * (row - 1) + col - 1, (network.length) * (row - 1) + col - 2);
         
         if (row == 1)     grid.union(0, col - 1);
         if (row == network.length)   grid.union(row * row + 1, (row) * (row - 1) + col - 1);
      }
   }

    // is the site (row, col) open?
   public boolean isOpen(int row, int col) {
      if (row < 1 || col < 1 || row > network.length || col > network.length) 
         throw new IllegalArgumentException();
      
      return network[row - 1][col - 1];
   }
   
   
    // is the site (row, col) full?
   public boolean isFull(int row, int col) {
      // check validity
      if (row < 1 || col < 1 || row > network.length || col > network.length) 
         throw new IllegalArgumentException();
     
      // Variable declaration
      int size = network.length;

      // base case
      if (!network[row - 1][col - 1]) return false;
      else if (row == 1) return true;
      else {
        return grid.find(0) == grid.find((size) * (row - 1) + col - 1);
      }
   }

    // returns the number of open sites
   public int numberOfOpenSites() {
      return openCount;
   }

    // does the system percolate?
   public boolean percolates() {
      int size = network.length;
      
      return grid.find(0) == grid.find(size * size + 1);
   }

    // test client (optional)
   /* this method is not implemented */
   public static void main(String[] args) {
      // empty 
   }
}