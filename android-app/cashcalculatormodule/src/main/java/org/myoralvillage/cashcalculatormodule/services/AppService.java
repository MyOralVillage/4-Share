package org.myoralvillage.cashcalculatormodule.services;

import org.myoralvillage.cashcalculatormodule.models.AppStateModel;
import org.myoralvillage.cashcalculatormodule.models.MathOperationModel;
import org.myoralvillage.cashcalculatormodule.models.MathOperationModel.MathOperationMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AppService {
    private final static BigDecimal INITIAL_VALUE = new BigDecimal(0);
    private AppStateModel appState;

    public AppService() {
        appState = AppStateModel.getDefault();
        resetCurrentOperation();
    }

    public AppService(AppStateModel appState) {
        this.appState = appState;
        resetCurrentOperation();
    }

    public AppStateModel getAppState() {
        return appState;
    }

    public void switchAppMode() {
        if (appState.getAppMode() == AppStateModel.AppMode.IMAGE)
            appState.setAppMode(AppStateModel.AppMode.NUMERIC);
        else appState.setAppMode(AppStateModel.AppMode.IMAGE);
    }

    public void reset() {
        AppStateModel.AppMode mode = appState.getAppMode();
        appState = AppStateModel.getDefault();
        appState.setAppMode(mode);
        resetCurrentOperation();
    }

    private void resetCurrentOperation() {
        appState.setCurrentOperationIndex(appState.getOperations().size() - 1);
    }

    public MathOperationMode getOperationMode() {
        return appState.getCurrentOperation().getMode();
    }

    public BigDecimal getValue() {
        return appState.getCurrentOperation().getValue();
    }

    public void setValue(BigDecimal value) {
        MathOperationModel currentOperation = appState.getCurrentOperation();
        currentOperation.setValue(value);
    }

    public boolean isInHistorySlideshow() {
        return appState.getCurrentOperationIndex() < (appState.getOperations().size() - 1);
    }

    public void enterHistorySlideshow() {
        appState.setCurrentOperationIndex(0);
    }

    public void gotoNextHistorySlide() {
        if (isInHistorySlideshow())
            appState.setCurrentOperationIndex(appState.getCurrentOperationIndex() + 1);

        if (!isInHistorySlideshow())
            changeCalculation();
    }

    private void changeCalculation() {
        appState.getOperations().remove(appState.getOperations().size() - 1);
        calculate();
    }

    public void gotoPreviousHistorySlide() {
        if (isInHistorySlideshow() && appState.getCurrentOperationIndex() > 0)
            appState.setCurrentOperationIndex(appState.getCurrentOperationIndex() - 1);
    }

    public void add() {
        addOperation(MathOperationModel.createAdd(INITIAL_VALUE));
    }

    public void subtract() {
        addOperation(MathOperationModel.createSubtract(INITIAL_VALUE));
    }

    public void multiply() {
        addOperation(MathOperationModel.createMultiply(INITIAL_VALUE));
    }

    public void calculate() {
        BigDecimal result = calculateOperationsResult(appState.getOperations());
        addOperation(MathOperationModel.createStandard(result));
    }

    private void addOperation(MathOperationModel operation) {
        this.appState.getOperations().add(operation);
        resetCurrentOperation();
    }

    private static BigDecimal calculateOperationsResult(List<MathOperationModel> operations) {
        if (operations.size() == 0)
            return BigDecimal.ZERO;

        if (operations.size() == 1)
            return operations.get(0).getValue();

        int lastStandardIndex = findLastOperationOfMode(operations, MathOperationMode.STANDARD);
        if (lastStandardIndex > 0)
            return calculateOperationsResult(operations.subList(lastStandardIndex, operations.size()));

        int lastMultiplyIndex = findLastOperationOfMode(operations, MathOperationMode.MULTIPLY);
        if (lastMultiplyIndex >= 0)
            return calculateOperationsResult(collapseOperationAtIndex(operations, lastMultiplyIndex));

        int lastAddIndex = findLastOperationOfMode(operations, MathOperationMode.ADD);
        if (lastAddIndex >= 0)
            return calculateOperationsResult(collapseOperationAtIndex(operations, lastAddIndex));

        int lastSubtractIndex = findLastOperationOfMode(operations, MathOperationMode.SUBTRACT);
        if (lastSubtractIndex >= 0)
            return calculateOperationsResult(collapseOperationAtIndex(operations, lastSubtractIndex));

        return BigDecimal.ZERO;
    }

    private static int findLastOperationOfMode(List<MathOperationModel> operations, MathOperationMode mode) {
        int index = -1;

        for (int i = 0; i < operations.size(); i++)
            if (operations.get(i).getMode() == mode)
                index = i;

        return index;
    }

    private static List<MathOperationModel> collapseOperationAtIndex(List<MathOperationModel> operations, int index) {
        int prevIndex = index - 1;
        MathOperationModel current = operations.get(index);
        MathOperationModel previous = operations.get(prevIndex);

        BigDecimal result = BigDecimal.ZERO;
        switch (current.getMode()) {
            case MULTIPLY:
                result = previous.getValue().multiply(current.getValue());
                break;
            case ADD:
                result = previous.getValue().add(current.getValue());
                break;
            case SUBTRACT:
                result = previous.getValue().subtract(current.getValue());
                break;
        }

        List<MathOperationModel> updatedOperations = new ArrayList<>(operations);
        updatedOperations.set(prevIndex, previous.copyWithValue(result));
        updatedOperations.remove(index);

        return updatedOperations;
    }
}
