/******************************************************************************
 *  Name:    Olga Soloveva
 *  NetID:   olgrit
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Model an n-by-n percolation system using the union-find
 *                data structure.
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{
   private double[] threshold;
   private int trials;
   public PercolationStats(int n, int trials)
   {
      if (n <= 0) 
         throw new IllegalArgumentException("System size should be a positive number");
      if (trials <= 0) 
         throw new IllegalArgumentException("Too little experiments");
      int random1, random2;
      this.trials = trials;
      this.threshold = new double[trials];
      for (int i = 1; i <= trials; i++)
      {
         Percolation elem = new Percolation(n); 
         while (!elem.percolates())
         {
            random1 = StdRandom.uniform(n)+1;
            random2 = StdRandom.uniform(n)+1;
            elem.open(random1, random2);
         }
      threshold[i-1] = (double) elem.numberOfOpenSites()/(n*n);
     }
   }

   public double mean()
   {
      return StdStats.mean(this.threshold);
   }

   public double stddev()
   { 
      if (this.trials <= 1)
        return Double.NaN;
      return StdStats.stddev(this.threshold);
   }

   public double confidenceLo()  
   {
      return this.mean()-1.96*this.stddev()/Math.sqrt(this.trials);
   }           

   public double confidenceHi()
   {
      return this.mean()+1.96*this.stddev()/Math.sqrt(this.trials);
   }

   public static void main(String[] args)
   {    
      int n = Integer.parseInt(args[0]);
      int trials = Integer.parseInt(args[1]);
      PercolationStats elem = new PercolationStats(n, trials);

      /* System.out.println(elem.mean());
      System.out.println(elem.stddev());
      System.out.println(elem.confidenceLo());
      System.out.println(elem.confidenceHi()); */
   }
}

