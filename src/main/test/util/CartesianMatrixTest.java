package main.test.util;

import main.Test;
import main.TestCase;
import main.util.Axis;

public class CartesianMatrixTest extends Test {

    /**
     * 
     * @return
     */
    @TestCase
    public boolean inverseTest() {
        Axis A = new Axis(true);
        A.x.x = 4;
        A.x.y = 2;
        A.x.z = 5;
        A.y.x = -4;
        A.y.y = 1;
        A.y.z = -3;
        A.z.x = 8;
        A.z.y = 2;
        A.z.z = 8;

        Axis B = new Axis(true);
        B.x.x = 14.0/-8.0;
        B.x.y = -6.0/-8.0;
        B.x.z = -11.0/-8.0;
        B.y.x = -1;
        B.y.y = 1;
        B.y.z = 1;
        B.z.x = 2;
        B.z.y = -1;
        B.z.z = 12.0/-8.0;

        return assertEquals(A.inv().toArray(),B.toArray(),1e-6);
    }
}