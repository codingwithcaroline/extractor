import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Extractor.java. Implements feature extraction for collinear points in
 * two dimensional data.
 *
 * @author  Caroline Kirkconnell (CarolineKirkconnell8@gmail.com)
 * @version final
 *
 */
public class Extractor {
   
   /** raw data: all (x,y) points from source data. */
   private Point[] points;
   
   /** lines identified from raw data. */
   private SortedSet<Line> lines;
  
   /**
    * Builds an extractor based on the points in the file named by filename. 
    */
   public Extractor(String filename) {
      try {
         Scanner scan = new Scanner(new File(filename));
         int size = scan.nextInt();
         points = new Point[size];
         int x;
         int y;
         for (int i = 0; i < size; i++) {
            x = scan.nextInt();
            y = scan.nextInt();
            points[i] = new Point(x, y);
         }
      }
      catch (java.io.IOException here) {
         System.err.println("File not found");
      }
      catch (Exception here) {
         System.err.println("Mistake");
      }
   }
  
   /**
    * Builds an extractor based on the points in the Collection named by pcoll. 
    *
    * THIS METHOD IS PROVIDED FOR YOU AND MUST NOT BE CHANGED.
    */
   public Extractor(Collection<Point> pcoll) {
      points = pcoll.toArray(new Point[]{});
   }
  
   /**
    * Returns a sorted set of all line segments of exactly four collinear
    * points. Uses a brute-force combinatorial strategy. Returns an empty set
    * if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesBrute() {
      Line brute = new Line();
      lines = new TreeSet<Line>();
      for (int a = 0; a < points.length; a++) {
         for (int b = a + 1; b < points.length; b++) {
            for(int c = b + 1; c < points.length; c++) {
               for(int d = c + 1; d < points.length; d++) {
                  brute.add(points[a]);
                  brute.add(points[b]);
                  if (brute.add(points[c]) && brute.add(points[d]) && brute.length() == 4) {
                     lines.add(brute);
                  }
                  brute = new Line();
               }
            }
         }
      }
      return lines;
   }
  
   /**
    * Returns a sorted set of all line segments of at least four collinear
    * points. The line segments are maximal; that is, no sub-segments are
    * identified separately. A sort-and-scan strategy is used. Returns an empty
    * set if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesFast() {
      Point[] sorted = Arrays.copyOf(points, points.length);
      lines = new TreeSet<Line>();
      Line brute = new Line();
      boolean canAdd = true;
      for (int a = 0; a < points.length; a++) {
         Arrays.sort(sorted, points[a].slopeOrder);
         for (int b = 0; b < points.length; b++) {
            brute.add(sorted[0]);
            canAdd = brute.add(sorted[b]);
            if (!canAdd) {
               if(brute.length() >= 4) {
                  lines.add(brute);
               }
               brute = new Line();
               brute.add(sorted[b]);
             }
           }
         }
      return lines;
   }
   
}
