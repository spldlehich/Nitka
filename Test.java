package tmp;

import static org.junit.Assert.assertTrue;

public class Test
{
    // for comparison
    private final static double exp = 0.00000000000001;
    
    // brick mass
    private final static double M = 1.;
    
    // the initial pressure on brick
    private final static double D = 0.;

    @org.junit.Test
    public void testFast()
    {       
      assertTrue(Math.abs(w(0, 0) - 0) < exp);
      assertTrue(Math.abs(w(1, 0) - 0.5) < exp);
      assertTrue(Math.abs(w(1, 1) - 0.5) < exp);
      assertTrue(Math.abs(w(2, 0) - 0.75) < exp);
      assertTrue(Math.abs(w(2, 1) - 1.5) < exp);
      assertTrue(Math.abs(w(2, 2) - 0.75) < exp);
      assertTrue(Math.abs(w(3, 0) - 0.875) < exp);
      assertTrue(Math.abs(w(3, 1) - 2.125) < exp);
      assertTrue(Math.abs(w(3, 2) - 2.125) < exp);
      assertTrue(Math.abs(w(3, 3) - 0.875) < exp);
      assertTrue(Math.abs(w(4, 0) - 0.9375) < exp);
      assertTrue(Math.abs(w(3, 2) - 2.125) < exp);
      assertTrue(Math.abs(w(322, 156) - 306.48749781747574) < exp);
    }

    @org.junit.Test
    public void testSlow()
    {       
        assertTrue(Math.abs(wSlow(0, 0) - 0) < exp);
        assertTrue(Math.abs(wSlow(1, 0) - 0.5) < exp);
        assertTrue(Math.abs(wSlow(1, 1) - 0.5) < exp);
        assertTrue(Math.abs(wSlow(2, 0) - 0.75) < exp);
        assertTrue(Math.abs(wSlow(2, 1) - 1.5) < exp);
        assertTrue(Math.abs(wSlow(2, 2) - 0.75) < exp);
        assertTrue(Math.abs(wSlow(3, 0) - 0.875) < exp);
        assertTrue(Math.abs(wSlow(3, 1) - 2.125) < exp);
        assertTrue(Math.abs(wSlow(3, 2) - 2.125) < exp);
        assertTrue(Math.abs(wSlow(3, 3) - 0.875) < exp);
        assertTrue(Math.abs(wSlow(4, 0) - 0.9375) < exp);
        assertTrue(Math.abs(wSlow(3, 2) - 2.125) < exp);
        
        // No not use assertTrue(Math.abs(w2(322, 156) - 306.48749781747574) < exp);
        // it's a slow method
    }
    

    /**
     * O(n^2), memory using.
     * Fastest way.
     *  
     * @param row
     * @param pos
     * @return The pressure on brick
     */
    private double w(int row, int pos)
    {
        if (pos > row)
            throw new IllegalArgumentException("pos > row");
        double[] prev = new double[1];
        prev[0] = M + D;
        for (int i = 1; i <= row; ++i)
        {
            double[] current = new double[i +1];
            for (int j = 0; j <= i; ++j)
            {
                if (j < i)
                    current[j] = (0.5 * prev[j]);
                if (j > 0)
                    current[j] += (0.5 * prev[j-1]);
                current[j] += M;
                if (row == i && pos == j)
                    return current[j] - M;
            }
            prev = current;
        }
        return 0;
    }

    /**
     * Recursion. O(2 ^ n), without the use of memory.
     * Slow way.
     * 
     * @param row
     * @param pos
     * @return The pressure on brick
     */
    private double wSlow(int row, int pos)
    {
        if (pos > row)
            throw new IllegalArgumentException("pos > row");
        return up(row, pos);
    }

    private double up(int currentRow, int currentPos)
    {
        if (currentRow == 0)
            return D;
        float result = 0;

        if (currentPos > 0)
            result += up(currentRow - 1, currentPos - 1) * 0.5 + M / 2.;
        if (currentPos <= currentRow - 1)
            result += up(currentRow - 1, currentPos) * 0.5 + M / 2.;
        return result;
    }
}
