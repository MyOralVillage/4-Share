package org.myoralvillage.cashcalculatormodule.services;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Based on the tests provided by Brett to verify the application is working to expectations.
 * P - Plus
 * M - Minus
 * X - Multiply
 */
public class AppServiceMathTest {

    @Test
    public void testP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(200));
        service.calculate();

        BigDecimal expected = new BigDecimal(300);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(1000));
        service.subtract();
        service.setValue(new BigDecimal(500));
        service.calculate();

        BigDecimal expected = new BigDecimal(600);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPPPPM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.add();
        service.setValue(new BigDecimal(500));
        service.add();
        service.setValue(new BigDecimal(200));
        service.add();
        service.setValue(new BigDecimal(200));
        service.subtract();
        service.setValue(new BigDecimal(1000));
        service.calculate();

        BigDecimal expected = new BigDecimal(500);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPPPPX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.add();
        service.setValue(new BigDecimal(200));
        service.add();
        service.setValue(new BigDecimal(500));
        service.add();
        service.setValue(new BigDecimal(200));
        service.multiply();
        service.setValue(new BigDecimal(6));
        service.calculate();

        BigDecimal expected = new BigDecimal(2500);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPXM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.multiply();
        service.setValue(new BigDecimal(4));
        service.subtract();
        service.setValue(new BigDecimal(250));
        service.calculate();

        BigDecimal expected = new BigDecimal(1850);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPMPMPM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.subtract();
        service.setValue(new BigDecimal(200));
        service.add();
        service.setValue(new BigDecimal(300));
        service.subtract();
        service.setValue(new BigDecimal(400));
        service.add();
        service.setValue(new BigDecimal(50));
        service.subtract();
        service.setValue(new BigDecimal(100));
        service.calculate();

        BigDecimal expected = new BigDecimal(250);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPMX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.subtract();
        service.setValue(new BigDecimal(300));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.calculate();

        BigDecimal expected = new BigDecimal(0);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPPPP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(10000));
        service.add();
        service.setValue(new BigDecimal(10));
        service.add();
        service.setValue(new BigDecimal(200));
        service.add();
        service.setValue(new BigDecimal(1000));
        service.calculate();

        BigDecimal expected = new BigDecimal(11310);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.multiply();
        service.setValue(new BigDecimal(10));
        service.calculate();

        BigDecimal expected = new BigDecimal(5100);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPMMMM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(1000));
        service.subtract();
        service.setValue(new BigDecimal(50));
        service.subtract();
        service.setValue(new BigDecimal(200));
        service.subtract();
        service.setValue(new BigDecimal(20));
        service.subtract();
        service.setValue(new BigDecimal(100));
        service.calculate();

        BigDecimal expected = new BigDecimal(730);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPXXX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(200));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.calculate();

        BigDecimal expected = new BigDecimal(6100);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPXMM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.multiply();
        service.setValue(new BigDecimal(4));
        service.subtract();
        service.setValue(new BigDecimal(250));
        service.subtract();
        service.setValue(new BigDecimal(600));
        service.calculate();

        BigDecimal expected = new BigDecimal(1250);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPXPXPX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(200));
        service.multiply();
        service.setValue(new BigDecimal(10));
        service.add();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.add();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.calculate();

        BigDecimal expected = new BigDecimal(2800);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPMMMMX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(1000));
        service.subtract();
        service.setValue(new BigDecimal(150));
        service.subtract();
        service.setValue(new BigDecimal(250));
        service.subtract();
        service.setValue(new BigDecimal(50));
        service.multiply();
        service.setValue(new BigDecimal(4));
        service.calculate();

        BigDecimal expected = new BigDecimal(500);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPMMMP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(1000));
        service.subtract();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.calculate();

        BigDecimal expected = new BigDecimal(1300);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPXMMMM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(200));
        service.multiply();
        service.setValue(new BigDecimal(10));
        service.subtract();
        service.setValue(new BigDecimal(50));
        service.subtract();
        service.setValue(new BigDecimal(200));
        service.subtract();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(200));
        service.calculate();

        BigDecimal expected = new BigDecimal(1550);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPXMPXM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(200));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.subtract();
        service.setValue(new BigDecimal(250));
        service.add();
        service.setValue(new BigDecimal(500));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.subtract();
        service.setValue(new BigDecimal(1000));
        service.calculate();

        BigDecimal expected = new BigDecimal(850);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testPMMXXX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(1000));
        service.subtract();
        service.setValue(new BigDecimal(200));
        service.subtract();
        service.setValue(new BigDecimal(50));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.calculate();

        BigDecimal expected = new BigDecimal(-600);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(20));
        service.calculate();

        BigDecimal expected = new BigDecimal(2000);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.subtract();
        service.setValue(new BigDecimal(50));
        service.calculate();

        BigDecimal expected = new BigDecimal(450);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXXM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(50));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.subtract();
        service.setValue(new BigDecimal(700));
        service.calculate();

        BigDecimal expected = new BigDecimal(50);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXXXXP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.add();
        service.setValue(new BigDecimal(100));
        service.calculate();

        BigDecimal expected = new BigDecimal(725);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXPM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.subtract();
        service.setValue(new BigDecimal(3000));
        service.calculate();

        BigDecimal expected = new BigDecimal(7500);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXMXMXM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(20));
        service.subtract();
        service.setValue(new BigDecimal(200));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.subtract();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.subtract();
        service.setValue(new BigDecimal(200));
        service.calculate();

        BigDecimal expected = new BigDecimal(500);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXMP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(10));
        service.subtract();
        service.setValue(new BigDecimal(1000));
        service.add();
        service.setValue(new BigDecimal(600));
        service.calculate();

        BigDecimal expected = new BigDecimal(-600);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXXXX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.multiply();
        service.setValue(new BigDecimal(4));
        service.calculate();

        BigDecimal expected = new BigDecimal(12000);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(10));
        service.add();
        service.setValue(new BigDecimal(500));
        service.calculate();

        BigDecimal expected = new BigDecimal(1500);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXXMM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(50));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.subtract();
        service.setValue(new BigDecimal(700));
        service.subtract();
        service.setValue(new BigDecimal(1100));
        service.calculate();

        BigDecimal expected = new BigDecimal(-1050);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXPPPP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(20));
        service.add();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(200));
        service.add();
        service.setValue(new BigDecimal(300));
        service.add();
        service.setValue(new BigDecimal(400));
        service.calculate();

        BigDecimal expected = new BigDecimal(3000);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXMPP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(10));
        service.subtract();
        service.setValue(new BigDecimal(1000));
        service.add();
        service.setValue(new BigDecimal(600));
        service.add();
        service.setValue(new BigDecimal(1000));
        service.calculate();

        BigDecimal expected = new BigDecimal(1600);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXMM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(200));
        service.multiply();
        service.setValue(new BigDecimal(20));
        service.subtract();
        service.setValue(new BigDecimal(500));
        service.calculate();

        BigDecimal expected = new BigDecimal(3500);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXMMMM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(1500));
        service.subtract();
        service.setValue(new BigDecimal(1000));
        service.subtract();
        service.setValue(new BigDecimal(2500));
        service.subtract();
        service.setValue(new BigDecimal(500));
        service.calculate();

        BigDecimal expected = new BigDecimal(4500);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXPMP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.subtract();
        service.setValue(new BigDecimal(3000));
        service.add();
        service.setValue(new BigDecimal(500));
        service.calculate();

        BigDecimal expected = new BigDecimal(8000);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXPMXP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.subtract();
        service.setValue(new BigDecimal(3000));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.add();
        service.setValue(new BigDecimal(500));
        service.calculate();

        BigDecimal expected = new BigDecimal(2000);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXMMX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(200));
        service.multiply();
        service.setValue(new BigDecimal(20));
        service.subtract();
        service.setValue(new BigDecimal(500));
        service.subtract();
        service.setValue(new BigDecimal(800));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.calculate();

        BigDecimal expected = new BigDecimal(1900);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXPXX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(10));
        service.add();
        service.setValue(new BigDecimal(200));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.calculate();

        BigDecimal expected = new BigDecimal(2200);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXPMX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.subtract();
        service.setValue(new BigDecimal(3000));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.calculate();

        BigDecimal expected = new BigDecimal(4500);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testXPMXPM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(500));
        service.subtract();
        service.setValue(new BigDecimal(3000));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.add();
        service.setValue(new BigDecimal(500));
        service.calculate();

        BigDecimal expected = new BigDecimal(2000);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(20));
        service.calculate();

        BigDecimal expected = new BigDecimal(80);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testMX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.calculate();

        BigDecimal expected = new BigDecimal(75);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testMMX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(10));
        service.subtract();
        service.setValue(new BigDecimal(1));
        service.multiply();
        service.setValue(new BigDecimal(20));
        service.calculate();

        BigDecimal expected = new BigDecimal(70);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testMXM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(10));
        service.subtract();
        service.setValue(new BigDecimal(20));
        service.calculate();

        BigDecimal expected = new BigDecimal(30);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testMPX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(50));
        service.add();
        service.setValue(new BigDecimal(10));
        service.multiply();
        service.setValue(new BigDecimal(6));
        service.calculate();

        BigDecimal expected = new BigDecimal(110);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testMXP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(500));
        service.subtract();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.add();
        service.setValue(new BigDecimal(200));
        service.calculate();

        BigDecimal expected = new BigDecimal(200);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testMM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(1));
        service.subtract();
        service.setValue(new BigDecimal(5));
        service.calculate();

        BigDecimal expected = new BigDecimal(94);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testMP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(10));
        service.add();
        service.setValue(new BigDecimal(5));
        service.calculate();

        BigDecimal expected = new BigDecimal(95);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testMXMX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(10));
        service.subtract();
        service.setValue(new BigDecimal(20));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.calculate();

        BigDecimal expected = new BigDecimal(10);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testMPMPMP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(20));
        service.add();
        service.setValue(new BigDecimal(10));
        service.subtract();
        service.setValue(new BigDecimal(50));
        service.add();
        service.setValue(new BigDecimal(40));
        service.subtract();
        service.setValue(new BigDecimal(15));
        service.add();
        service.setValue(new BigDecimal(30));
        service.calculate();

        BigDecimal expected = new BigDecimal(95);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testMXXP() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(500));
        service.subtract();
        service.setValue(new BigDecimal(100));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.add();
        service.setValue(new BigDecimal(200));
        service.calculate();

        BigDecimal expected = new BigDecimal(100);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testMXXXX() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(20));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.multiply();
        service.setValue(new BigDecimal(5));
        service.calculate();

        BigDecimal expected = new BigDecimal(-2900);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testMXXM() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.subtract();
        service.setValue(new BigDecimal(6));
        service.multiply();
        service.setValue(new BigDecimal(6));
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.subtract();
        service.setValue(new BigDecimal(10));
        service.calculate();

        BigDecimal expected = new BigDecimal(18);
        assertEquals(expected, service.getValue());
    }
}
