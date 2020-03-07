package org.myoralvillage.cashcalculatormodule.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *  A model class used to represent the modes that the Cash Calculator can be displayed as well as
 *  the operations each mode can execute.
 *
 * @author Peter Panagiotis Roubatsis
 * @author Hamza Mahfooz
 * @see java.lang.Object
 */
public class AppStateModel implements Serializable {
    /**
     * The current mode of the Cash Calculator. The available modes are either IMAGE or NUMERIC.
     *
     * @see AppMode
     */
    private AppMode appMode;

    /**
     * A List of operations performed by the Cash Calculator leading to the <code>appMode</code>. Each element in
     * the list contains a <code>MathOperationModel</code> class.
     *
     * @see MathOperationModel
     */
    private List<MathOperationModel> operations;

    /**
     * An integer used to record the index of the current operation after a list of operations are
     * performed. This integer is useful in traversing the history of the Cash Calculator module.
     */
    private int currentOperationIndex;

    /**
     * Constructs a new <code>AppStateModel</code> in the specified Cash Calculator mode and the list
     * of operations performed by the Cash Calculator.
     *
     * @param appMode The mode of the Cash Calculator.
     * @param operations A list of mathematical operations for the appMode.
     */
    public AppStateModel(AppMode appMode, List<MathOperationModel> operations) {
        this.appMode = appMode;
        this.operations = operations;
    }

    /**
     * Returns the mode associated with this model.
     *
     * @return The mode of this model.
     */
    public AppMode getAppMode() {
        return appMode;
    }

    /**
     * Returns the list of operations performed associated with this model.
     *
     * @return list of operations of this model.
     * @see MathOperationModel
     */
    public List<MathOperationModel> getOperations() {
        return operations;
    }

    /**
     * Marks the mode of the Cash Calculator to the specified <code>appMode</code>.
     *
     * @param appMode the Cash Calculator mode that this model should be set.
     * @see AppMode
     */
    public void setAppMode(AppMode appMode) {
        this.appMode = appMode;
    }

    /**
     * Returns the index of the current operation from the list of operations associated with this
     * model. Usually, this method is used during the traversal of the past operations performed by
     * the Cash Calculator.
     *
     * @return the index of the list of operations (Integer value).
     */
    public int getCurrentOperationIndex() {
        return currentOperationIndex;
    }

    /**
     * Changes the index of the current operation index from the list of operations. The index is
     * changed to update which operation the model is currently visiting.
     *
     * @param currentOperationIndex The index related to the list of <code>operations</code>.
     *                              Integer value.
     */
    public void setCurrentOperationIndex(int currentOperationIndex) {
        this.currentOperationIndex = currentOperationIndex;
    }

    /**
     * Returns the operation from the list, <code>operations</code>, based on the value of
     * the current Operation Index
     *
     * @return A mathematical operation performed by the Cash Calculator, an instance
     * of {@link MathOperationModel}.
     */
    public MathOperationModel getCurrentOperation() {
        return operations.get(currentOperationIndex);
    }

    /**
     * Creates a default Cash Calculator model. THe default <code>appMode</code> is the IMAGE mode
     * and the default <code>operations</code> is a list containing one <code>MathOperationModel</code>
     * with the STANDARD operation.
     *
     * @return A new <code>AppStateModel</code> in IMAGE mode and the standard operation.
     */
    public static AppStateModel getDefault() {
        List<MathOperationModel> operations = new ArrayList<>();
        operations.add(MathOperationModel.createStandard(new BigDecimal(0)));

        return new AppStateModel(AppMode.IMAGE, operations);
    }

    /**
     * Indicates whether some other <code>AppStateModel</code> is equal to this one.
     *
     * @param obj an <code>AppStateModel</code> with which to compare.
     * @return true if this <code>AppStateModel</code> is the same as the obj argument; false
     * otherwise.
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof AppStateModel))
            return false;

        AppStateModel state = (AppStateModel) obj;
        return operations.equals(state.operations) &&
                appMode == state.appMode &&
                currentOperationIndex == state.currentOperationIndex;
    }

    /**
     * An enum class that highlights the mode of the <code>AppStateModel</code>.
     *
     * @see AppStateModel
     */
    public enum AppMode {
        /**
         * Cash Calculator is in image mode.
         */
        IMAGE,

        /**
         * Cash Calculator is in numeric mode.
         */
        NUMERIC
    }
}
