package org.myoralvillage.cashcalculatormodule.services;

import org.myoralvillage.cashcalculatormodule.models.AppStateModel;
import org.myoralvillage.cashcalculatormodule.models.MathOperationModel;
import org.myoralvillage.cashcalculatormodule.models.MathOperationModel.MathOperationMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A service class used to perform the main operations of the <code>CashCalculator</code>.
 * @author Hamza Mahfooz
 * @author Peter Panagiotis Roubatsis
 * @see java.lang.Object
 */
public class AppService {
    /**
     * A constant variable used to initialize the value of the app's state before the user does any operations.
     */
    private final static BigDecimal INITIAL_VALUE = new BigDecimal(0);

    /**
     * This variable is used to store the state of the <code>Cash Calculator</code>.
     *
     * @see AppStateModel
     */
    private AppStateModel appState;

    /**
     * Constructs a new <code>AppService</code> where the appState is in the default mode.
     *
     * @see AppStateModel
     */
    public AppService() {
        appState = AppStateModel.getDefault();
        resetCurrentOperation();
    }

    /**
     * Constructs a new <code>AppService</code> with the specified appState.
     *
     * @param appState the appState of this service.
     * @see AppStateModel
     */
    public AppService(AppStateModel appState) {
        this.appState = appState;
        resetCurrentOperation();
    }

    /**
     * Returns the appState associated with this service.
     *
     * @return the appState of this service.
     */
    public AppStateModel getAppState() {
        return appState;
    }

    /**
     * Changes the appState between Image mode and Numeric mode.
     *
     * @see AppStateModel
     */
    public void switchAppMode() {
        if (appState.getAppMode() == AppStateModel.AppMode.IMAGE)
            appState.setAppMode(AppStateModel.AppMode.NUMERIC);
        else appState.setAppMode(AppStateModel.AppMode.IMAGE);
    }

    /**
     * Reset to the initial state of the app while remaining in the current app mode
     */
    public void reset() {
        AppStateModel.AppMode mode = appState.getAppMode();
        appState = AppStateModel.getDefault();
        appState.setAppMode(mode);
        resetCurrentOperation();
    }

    /**
     * Reset the current operation to the latest one performed
     */
    private void resetCurrentOperation() {
        appState.setCurrentOperationIndex(appState.getOperations().size() - 1);
    }

    /**
     * Returns the mathematical operation of the current state of the <code>CashCalculator</code>.
     *
     * @return the mathematical operation of the <code>CashCalculator</code>.
     */
    public MathOperationMode getOperationMode() {
        return appState.getCurrentOperation().getMode();
    }

    /**
     * The current total value displayed on the <code>CashCalculator</code>.
     *
     * @return The total value of the <code>CashCalculator</code>.
     */
    public BigDecimal getValue() {
        return appState.getCurrentOperation().getValue();
    }

    /**
     * Changes the total value displayed on the <code>CashCalculator</code>.
     *
     * @param value the new total value of the <code>CashCalculator</code>.
     */
    public void setValue(BigDecimal value) {
        MathOperationModel currentOperation = appState.getCurrentOperation();
        currentOperation.setValue(value);
    }

    /**
     * Checks if the <code>CashCalculator</code> is in the history mode.
     *
     * @return True if the application is in the history mode; False otherwise
     */
    public boolean isInHistorySlideshow() {
        return appState.isInHistorySlideshow();
    }

    /**
     * Enter the history mode.
     */
    public void enterHistorySlideshow() {
        appState.setCurrentOperationIndex(0);
    }

    /**
     * Show the next operation performed on the <code>CashCalculator</code>. If this current history
     * slide was the last element in the history slides, return to the standard mode, removing the
     * most recent state that occurred before entering the history mode.
     */
    public void gotoNextHistorySlide() {
        if (isInHistorySlideshow())
            appState.setCurrentOperationIndex(appState.getCurrentOperationIndex() + 1);
            changeCalculation();
        if (!isInHistorySlideshow())
            changeCalculation();
    }

    /**
     * Recalculates the result of previous operations
     */
    private void changeCalculation() {
        int standardIndex = findFirstOperationOfMode(appState.getOperations(), MathOperationMode.STANDARD);
        for (int i = standardIndex + 1; i < appState.getOperations().size(); i++) {
            if (appState.getOperations().get(i).getMode() == MathOperationMode.STANDARD) {
                BigDecimal result = calculateOperationsResult(appState.getOperations().subList(standardIndex, i));
                appState.getOperations().remove(i);
                appState.getOperations().add(i, MathOperationModel.createStandard(result));
                standardIndex = i;
            }
        }
    }

    /**
     * Shows the previous operation performed on the <code>CashCalculator</code>.
     */
    public void gotoPreviousHistorySlide() {
        if (isInHistorySlideshow() && appState.getCurrentOperationIndex() > 0)
            appState.setCurrentOperationIndex(appState.getCurrentOperationIndex() - 1);
    }

    /**
     * Adds a new ADD state to the list of mathematical operations in the variable, <code>appState</code>
     */
    public void add() {
        addOperation(MathOperationModel.createAdd(INITIAL_VALUE));
    }

    /**
     * Adds a new SUBTRACT state to the list of mathematical operations in the variable, <code>appState</code>
     */
    public void subtract() {
        addOperation(MathOperationModel.createSubtract(INITIAL_VALUE));
    }

    /**
     * Adds a new MULTIPLY state to the list of mathematical operations in the variable, <code>appState</code>
     */
    public void multiply() {
        addOperation(MathOperationModel.createMultiply(INITIAL_VALUE));
    }

    /**
     * Adds a new STANDARD state to the list of mathematical operations in the variable, <code>appState</code>
     */
    public void calculate() {
        BigDecimal result = calculateOperationsResult(appState.getOperations());
        addOperation(MathOperationModel.createStandard(result));
    }

    /**
     * Inserts a mathematical operation to the list of operations in the
     * variable, <code>appState</code>.
     *
     * @param operation The mathematical operation to be added
     *
     * @see MathOperationModel
     */
    private void addOperation(MathOperationModel operation) {
        this.appState.getOperations().add(operation);
        resetCurrentOperation();
    }

    /**
     * Performs the calculations of the operations that have occurred.
     *
     * @param operations the list of operations performed.
     * @return the result of the operations.
     * @see MathOperationModel
     */
    private static BigDecimal calculateOperationsResult(List<MathOperationModel> operations) {
        if (operations.size() == 0)
            return BigDecimal.ZERO;

        if (operations.size() == 1)
            return operations.get(0).getValue();

        int standardIndex = findLastStandardOperation(operations);
        if (standardIndex > 0)
            return calculateOperationsResult(operations.subList(standardIndex, operations.size()));

        int multiplyIndex = findFirstOperationOfMode(operations, MathOperationMode.MULTIPLY);
        if (multiplyIndex >= 0)
            return calculateOperationsResult(collapseOperationAtIndex(operations, multiplyIndex));

        int addIndex = findFirstOperationOfMode(operations, MathOperationMode.ADD);
        int subtractIndex = findFirstOperationOfMode(operations, MathOperationMode.SUBTRACT);

        // Do addition/subtraction left-to-right in the order they appear
        if (addIndex >=0 && subtractIndex >= 0) {
            if (addIndex < subtractIndex)
                return calculateOperationsResult(collapseOperationAtIndex(operations, addIndex));
            else
                return calculateOperationsResult(collapseOperationAtIndex(operations, subtractIndex));
        }

        if (addIndex >= 0)
            return calculateOperationsResult(collapseOperationAtIndex(operations, addIndex));

        if (subtractIndex >= 0)
            return calculateOperationsResult(collapseOperationAtIndex(operations, subtractIndex));

        return BigDecimal.ZERO;
    }

    /**
     * Returns the index of the most recent occurrence of the specified mode in the list of operations.
     *
     * @param operations the list of operations to be searched.
     * @param mode the mode that is being searched for.
     * @return the index of the most recent occurrence of the mode.
     *
     * @see MathOperationModel
     */
    private static int findLastOperationOfMode(List<MathOperationModel> operations, MathOperationMode mode) {
        int index = -1;

        for (int i = 0; i < operations.size(); i++)
            if (operations.get(i).getMode() == mode)
                index = i;

        return index;
    }

    private static int findLastStandardOperation(List<MathOperationModel> operations) {
        return findLastOperationOfMode(operations, MathOperationMode.STANDARD);
    }


    private static int findFirstOperationOfMode(List<MathOperationModel> operations, MathOperationMode mode) {
        int index = findLastStandardOperation(operations);

        //if (mode == MathOperationMode.STANDARD){
        //    return index;
        //}
        for (int i = 0; i < operations.size(); i++)
            if (operations.get(i).getMode() == mode)
                return i;
        return -1;
    }

    /**
     * Helper function used in <code>calculateOperationResults</code> that performs the calculation
     * and updates the list of operations.
     *
     * @param operations the list of mathematical operations.
     * @param index the index of the current operation to be performed.
     * @return the updated list of mathematical operations.
     */
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
