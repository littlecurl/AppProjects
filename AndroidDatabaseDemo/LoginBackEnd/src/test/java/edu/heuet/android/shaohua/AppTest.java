package edu.heuet.android.shaohua;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test(){
       System.out.println(countsOfOne(7));
    }

    int countsOfOne(int number)
    {
        int counts = 0;
        while (number != 0)
        {
            counts++;
            number = number & (number - 1);
        }
        return counts;
    }
}
