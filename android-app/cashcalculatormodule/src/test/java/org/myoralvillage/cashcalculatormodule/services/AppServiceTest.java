package org.myoralvillage.cashcalculatormodule.services;

import org.junit.Test;
import org.myoralvillage.cashcalculatormodule.models.AppStateModel;
import org.myoralvillage.cashcalculatormodule.models.MathOperationModel;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AppServiceTest {
    @Test
    public void testDefaultState() {
        AppService service = new AppService();
        AppStateModel expected = AppStateModel.getDefault();

        assertEquals(expected, service.getAppState());
    }

    @Test
    public void testAddState() {
        AppService service = new AppService();
        service.add();

        AppStateModel expected = AppStateModel.getDefault();
        expected.getOperations().add(MathOperationModel.createAdd(BigDecimal.ZERO));
        expected.setCurrentOperationIndex(1);

        assertEquals(expected, service.getAppState());
    }

    @Test
    public void testSubtractState() {
        AppService service = new AppService();
        service.subtract();

        AppStateModel expected = AppStateModel.getDefault();
        expected.getOperations().add(MathOperationModel.createSubtract(BigDecimal.ZERO));
        expected.setCurrentOperationIndex(1);

        assertEquals(expected, service.getAppState());
    }

    @Test
    public void testMultiplyState() {
        AppService service = new AppService();
        service.multiply();

        AppStateModel expected = AppStateModel.getDefault();
        expected.getOperations().add(MathOperationModel.createMultiply(BigDecimal.ZERO));
        expected.setCurrentOperationIndex(1);

        assertEquals(expected, service.getAppState());
    }

    @Test
    public void testManyStates() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(7));
        service.add();
        service.setValue(new BigDecimal(10));
        service.subtract();
        service.setValue(new BigDecimal(3));
        service.setValue(new BigDecimal(2));
        service.add();
        service.setValue(new BigDecimal(1));

        AppStateModel expected = AppStateModel.getDefault();
        expected.getOperations().set(0, MathOperationModel.createStandard(new BigDecimal(5)));
        expected.getOperations().add(MathOperationModel.createMultiply(new BigDecimal(7)));
        expected.getOperations().add(MathOperationModel.createAdd(new BigDecimal(10)));
        expected.getOperations().add(MathOperationModel.createSubtract(new BigDecimal(2)));
        expected.getOperations().add(MathOperationModel.createAdd(new BigDecimal(1)));
        expected.setCurrentOperationIndex(4);

        assertEquals(expected, service.getAppState());
    }

    @Test
    public void testCalculation() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(7));
        service.add();
        service.setValue(new BigDecimal(10));
        service.subtract();
        service.setValue(new BigDecimal(3));
        service.setValue(new BigDecimal(2));
        service.add();
        service.setValue(new BigDecimal(1));
        service.calculate();

        BigDecimal expected = new BigDecimal(44);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testCalculateState() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(7));
        service.add();
        service.setValue(new BigDecimal(10));
        service.subtract();
        service.setValue(new BigDecimal(3));
        service.setValue(new BigDecimal(2));
        service.add();
        service.setValue(new BigDecimal(1));
        service.calculate();

        AppStateModel expected = AppStateModel.getDefault();
        expected.getOperations().set(0, MathOperationModel.createStandard(new BigDecimal(5)));
        expected.getOperations().add(MathOperationModel.createMultiply(new BigDecimal(7)));
        expected.getOperations().add(MathOperationModel.createAdd(new BigDecimal(10)));
        expected.getOperations().add(MathOperationModel.createSubtract(new BigDecimal(2)));
        expected.getOperations().add(MathOperationModel.createAdd(new BigDecimal(1)));
        expected.getOperations().add(MathOperationModel.createStandard(new BigDecimal(44)));
        expected.setCurrentOperationIndex(5);

        assertEquals(expected, service.getAppState());
    }

    @Test
    public void testAdditionalCalculation() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(7));
        service.add();
        service.setValue(new BigDecimal(10));
        service.subtract();
        service.setValue(new BigDecimal(3));
        service.setValue(new BigDecimal(2));
        service.add();
        service.setValue(new BigDecimal(1));
        service.calculate();
        service.setValue(new BigDecimal(50));
        service.add();
        service.setValue(new BigDecimal(9));
        service.calculate();

        BigDecimal expected = new BigDecimal(59);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testOrderOfOperations() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(5));
        service.add();
        service.setValue(new BigDecimal(7));
        service.add();
        service.setValue(new BigDecimal(10));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.setValue(new BigDecimal(2));
        service.subtract();
        service.setValue(new BigDecimal(1));
        service.calculate();

        BigDecimal expected = new BigDecimal(31);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testHistoryStates() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(5));
        service.multiply();
        service.setValue(new BigDecimal(7));
        service.add();
        service.setValue(new BigDecimal(10));
        service.subtract();
        service.setValue(new BigDecimal(3));
        service.setValue(new BigDecimal(2));
        service.add();
        service.setValue(new BigDecimal(1));

        AppStateModel expected = AppStateModel.getDefault();
        expected.getOperations().set(0, MathOperationModel.createStandard(new BigDecimal(5)));
        expected.getOperations().add(MathOperationModel.createMultiply(new BigDecimal(7)));
        expected.getOperations().add(MathOperationModel.createAdd(new BigDecimal(10)));
        expected.getOperations().add(MathOperationModel.createSubtract(new BigDecimal(2)));
        expected.getOperations().add(MathOperationModel.createAdd(new BigDecimal(1)));
        expected.setCurrentOperationIndex(4);

        assertEquals(expected, service.getAppState());

        service.enterHistorySlideshow();
        assertEquals(new BigDecimal(5), service.getValue());
        service.gotoNextHistorySlide();
        assertEquals(new BigDecimal(7), service.getValue());
        service.gotoNextHistorySlide();
        assertEquals(new BigDecimal(10), service.getValue());
        service.gotoNextHistorySlide();
        assertEquals(new BigDecimal(2), service.getValue());
        service.gotoPreviousHistorySlide();
        assertEquals(new BigDecimal(10), service.getValue());
        service.gotoPreviousHistorySlide();
        assertEquals(new BigDecimal(7), service.getValue());
        service.gotoPreviousHistorySlide();
        assertEquals(new BigDecimal(5), service.getValue());

        // Before first slide
        service.gotoPreviousHistorySlide();
        assertEquals(new BigDecimal(5), service.getValue());
    }

    @Test
    public void testChainChangedHistory() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(5));
        service.add();
        service.setValue(new BigDecimal(7));
        service.add();
        service.setValue(new BigDecimal(10));
        service.multiply();
        service.setValue(new BigDecimal(3));
        service.setValue(new BigDecimal(2));
        service.subtract();
        service.setValue(new BigDecimal(1));
        service.calculate();

        BigDecimal expected = new BigDecimal(31);
        assertEquals(expected, service.getValue());

        service.enterHistorySlideshow();
        service.gotoNextHistorySlide();
        service.gotoNextHistorySlide();
        service.gotoNextHistorySlide();
        service.setValue(new BigDecimal(5));
        service.gotoNextHistorySlide();
        service.gotoNextHistorySlide();

        expected = new BigDecimal(61);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testSequentialChangedHistory() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(100));
        service.add();
        service.setValue(new BigDecimal(50));
        service.calculate();
        service.multiply();
        service.setValue(new BigDecimal(2));
        service.calculate();
        service.subtract();
        service.setValue(new BigDecimal(20));
        service.calculate();

        BigDecimal expected = new BigDecimal(280);
        assertEquals(expected, service.getValue());

        service.enterHistorySlideshow();
        service.gotoNextHistorySlide();
        service.setValue(new BigDecimal(30));
        service.gotoNextHistorySlide();

        expected = new BigDecimal(130);
        assertEquals(expected, service.getValue());

        service.gotoNextHistorySlide();
        service.setValue(new BigDecimal(3));
        service.gotoNextHistorySlide();

        expected = new BigDecimal(390);
        assertEquals(expected, service.getValue());

        service.gotoNextHistorySlide();
        service.gotoNextHistorySlide();

        expected = new BigDecimal(370);
        assertEquals(expected, service.getValue());
    }


    @Test
    public void testSubtractionCalculation() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(10000));
        service.subtract();
        service.setValue(new BigDecimal(2000));
        service.subtract();
        service.setValue(new BigDecimal(500));
        service.subtract();
        service.setValue(new BigDecimal(10000));
        service.setValue(new BigDecimal(5000));
        service.subtract();
        service.setValue(new BigDecimal(1000));
        service.calculate();

        BigDecimal expected = new BigDecimal(1500);
        assertEquals(expected, service.getValue());
    }

    @Test
    public void testSubtractionAdditionalCalculation() {
        AppService service = new AppService();
        service.setValue(new BigDecimal(10000));
        service.subtract();
        service.setValue(new BigDecimal(2000));
        service.subtract();
        service.setValue(new BigDecimal(500));
        service.subtract();
        service.setValue(new BigDecimal(10000));
        service.setValue(new BigDecimal(5000));
        service.subtract();
        service.setValue(new BigDecimal(1000));
        service.calculate();
        service.setValue(new BigDecimal(5000));
        service.subtract();
        service.setValue(new BigDecimal(200));
        service.calculate();

        BigDecimal expected = new BigDecimal(4800);
        assertEquals(expected, service.getValue());
    }
}
