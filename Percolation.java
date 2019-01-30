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

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation 
{
   private boolean[][] sites;
   private int numopensites;
   private int n;
   private WeightedQuickUnionUF arr;
    
   public Percolation(int n)    
   {
      if (n <= 0) 
         throw new IllegalArgumentException("System size should be a positive number");
      sites = new boolean[n][n];
      arr = new WeightedQuickUnionUF(n*n+2);
      this.n = n;
      for (int i = 1; i <= n; i++)
      {
         arr.union(0, i);
         arr.union(n*n+2-1, n*n+1-i);
      }
   }

   public void open(int row, int col)
   {
      if (row <= 0 || col > n) 
         throw new IllegalArgumentException("row index out of bounds");
      if (col <= 0 || col > n) 
         throw new IllegalArgumentException("col index out of bounds");
      if (isOpen(row, col))
         return;
      
      sites[row-1][col-1] = true;
      this.numopensites++;
      if (this.n == 1)
         return;
      
      if (row == 1)
      {
         if (col == 1) // left top corner
         {
            if (isOpen(1, 2))
               arr.union(1, 2);
            if (isOpen(2, 1))
               arr.union(1, n+1);
         }
         else
         {
            if (col == n)  // right top corner
            {
               if (isOpen(1, n-1))
                  arr.union(n-1, n);
               if (isOpen(2, n))
                  arr.union(n, 2*n);
            }
            else  //top row
            {
               if (isOpen(1, col-1))
                  arr.union(col, col-1);
               if (isOpen(1, col+1))
                  arr.union(col, col+1);
               if (isOpen(2, col))
                  arr.union(col, n+col); 
            }
         }
      }
      else 
         if (row == n)
         {
            if (col == 1)  // left bottom corner
            {
               if (isOpen(n-1, 1))
                  arr.union(n*(n-1)+1, n*(n-2)+2);
               if (isOpen(n, 2))
                  arr.union(n*(n-1)+1, n*(n-1)+2);
             }
             else
                if (col == n)  // right bottom corner
                { 
                   if (isOpen(n-1, n))
                      arr.union(n*n, n*(n-1));
                   if (isOpen(n, n-1))
                      arr.union(n*n, n*n-1);
                }
                else  //bottom row
                {
                   if (isOpen(row, col-1))
                      arr.union(n*(n-1)+col, n*(n-1)+col-1);
                   if (isOpen(row, col+1))
                      arr.union(n*(n-1)+col, n*(n-1)+col+1);
                   if (isOpen(row-1, col))
                      arr.union(n*(n-1)+col, n*(n-2)+col); 
                }
         }
         else  // row!=1 || n 
         {
            if (col == 1)  // left column
            {
               if (isOpen(row-1, col))
                  arr.union((row-1)*n+1, (row-2)*n+1);
               if (isOpen(row+1, col))
                  arr.union((row-1)*n+1, row*n+1);
               if (isOpen(row, col+1))
                  arr.union((row-1)*n+1, (row-1)*n+2);
            }
            else
               if (col == n)  // right column
               {
                  if (isOpen(row-1, col))
                     arr.union(row*n, (row-1)*n);
                  if (isOpen(row+1, col))
                     arr.union(row*n, (row+1)*n);
                  if (isOpen(row, col-1))
                     arr.union(row*n, row*n-1);
               }
               else // not boundary
               {
                  if (isOpen(row-1, col)) // top
                     arr.union((row-1)*n+col, (row-2)*n+col);
                  if (isOpen(row, col-1)) // left
                     arr.union((row-1)*n+col, (row-1)*n+col-1);
                  if (isOpen(row+1, col)) // bottom
                     arr.union((row-1)*n+col, row*n+col);
                  if (isOpen(row, col+1)) // right
                     arr.union((row-1)*n+col, (row-1)*n+col+1);
               }   
         }              
   }

   public boolean isOpen(int row, int col)
   {
      if (row <= 0 || col > n) 
         throw new IllegalArgumentException("row index out of bounds");
      if (col <= 0 || col > n) 
         throw new IllegalArgumentException("col index out of bounds");
      return sites[row-1][col-1];
   }

   public boolean isFull(int row, int col)
   {
      if (row <= 0 || col > n) 
         throw new IllegalArgumentException("row index out of bounds");
      if (col <= 0 || col > n) 
         throw new IllegalArgumentException("col index out of bounds");
      return arr.connected(0, (row-1)*n+col) && this.isOpen(row, col);
   }

   public int numberOfOpenSites() 
   {
      return numopensites;
   }

   public boolean percolates()
   {
      return arr.connected(0, n*n+1);
   }
}