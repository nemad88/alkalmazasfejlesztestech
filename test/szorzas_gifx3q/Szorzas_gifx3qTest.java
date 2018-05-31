/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szorzas_gifx3q;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static szorzas_gifx3q.Szorzas_gifx3q.multiplyNaturals;

/**
 *
 * @author Adam Nemeth
 */
public class Szorzas_gifx3qTest {

    public Szorzas_gifx3qTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of multiplyNaturals method, of class Szorzas_gifx3q.
     */
    @Test
    public void testMultiplyNaturals1() {
        assertEquals(-1, multiplyNaturals(-10, -21));
    }

    @Test
    public void testMultiplyNaturals2() {
        assertEquals(-1, multiplyNaturals(-1, 2));       
    }

    @Test
    public void testMultiplyNaturals3() {
        assertEquals(-1, multiplyNaturals(2, -1));        
    }

    @Test
    public void testMultiplyNaturals4() {
        assertEquals(10, multiplyNaturals(1, 10));
    }

    @Test
    public void testMultiplyNaturals6() {
        assertEquals(10, multiplyNaturals(10, 1));
    }
}
