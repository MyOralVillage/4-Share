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
    }

    public AppService(AppStateModel appState) {
        this.appState = appState;
    }

    public AppStateModel getAppState() {
        return appState;
    }

    public void reset() {
        AppStateModel.AppMode mode = appState.getAppMode();
        appState = AppStateModel.getDefault();
        appState.setAppMode(mode);
    }

    public MathOperationMode getOperationMode() {
        int currentIndex = appState.getOperations().size() - 1;
        return appState.getOperations().get(currentIndex).getMode();
    }

    public BigDecimal getValue() {
        int currentIndex = appState.getOperations().size() - 1;
        return appState.getOperations().get(currentIndex).getValue();
    }

    public void setValue(BigDecimal value) {
        int currentIndex = appState.getOperations().size() - 1;
        MathOperationModel currentOperation = appState.getOperations().get(currentIndex);
        currentOperation.setValue(value);
    }

    public void add() {
        this.appState
                .getOperations()
                .add(MathOperationModel.createAdd(INITIAL_VALUE));
    }

    public void subtract() {
        this.appState
                .getOperations()
                .add(MathOperationModel.createSubtract(INITIAL_VALUE));
    }

    public void multiply() {
        this.appState
                .getOperations()
                .add(MathOperationModel.createMultiply(INITIAL_VALUE));
    }

    public void calculate() {
        List<MathOperationModel> operations = this.appState.getOperations();
        BigDecimal result = calculateOperationsResult(operations);
        operations.add(MathOperationModel.createStandard(result));
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
