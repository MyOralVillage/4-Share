package org.myoralvillage.cashcalculatormodule.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  A model class used to represent the operations that the Cash Calculator can perform.
 *
 * @author Hamza Mahfooz
 * @author Peter Panagiotis Roubatsis
 * @see java.lang.Object
 */
public class MathOperationModel implements Serializable {
    /**
     * The mathematical operations that the Cash Calculator can perform. The available modes are
     * addition, subtraction, multiplication and a standard mode, where the standard mode is the
     * screen when after or before each operation is being performed.
     *
     * @see MathOperationMode
     */
    private MathOperationMode mode;

    /**
     * A value used to represent the amount that the original total value will be affected according
     * to the type of mode this model is in.
     */
    private BigDecimal value;

    /**
     * Constructs a new <code>MathOperationModel</code> with a specified mode and a value.
     *
     * @param mode A <code>MathOperationMode</code> for which to set this model.
     * @param value The value that will affect the original total value.
     * @see MathOperationMode
     */
    private MathOperationModel(MathOperationMode mode, BigDecimal value) {
        this.mode = mode;
        this.value = value;
    }

    /**
     * Returns the <code>MathOperationMode</code> associated with this model. The available modes
     * are ADD, SUBTRACT, MULTIPLY STANDARD.
     *
     * @return the mode of this model.
     * @see MathOperationMode
     */
    public MathOperationMode getMode() {
        return mode;
    }

    /**
     * Returns the value associated with this model. Note that this value is not the same value as
     * the original total value before an operation is applied.
     *
     * @return the value of this model.
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Changes this value to the specified <code>value</code>.
     *
     * @param value the new value of this model.
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * Constructs a new <code>MathOperationModel</code> with the same mode of this model and
     * possibly a different value.
     *
     * @param value The value of the new <code>MathOperationModel</code> with the same mode as this
     *              model
     * @return a new <code>MathOperationModel</code> (copy of this model) with possibly a new value.
     */
    public MathOperationModel copyWithValue(BigDecimal value) {
        return new MathOperationModel(mode, value);
    }

    /**
     * Constructs a new <code>MathOperationModel</code> in the mode, standard, with the specified
     * <code>value</code>.
     *
     * @param value The value of the original amount.
     * @return A new <code>MathOperationModel</code> in the STANDARD mode.
     * @see MathOperationMode
     */
    public static MathOperationModel createStandard(BigDecimal value) {
        return new MathOperationModel(MathOperationMode.STANDARD, value);
    }

    /**
     * Constructs a new <code>MathOperationModel</code> in the addition mode with the specified
     * <code>value</code>.
     *
     * @param value The value that is being added to the original amount.
     * @return A new <code>MathOperationModel</code> in the ADD mode.
     * @see MathOperationMode
     */
    public static  MathOperationModel createAdd(BigDecimal value) {
        return new MathOperationModel(MathOperationMode.ADD, value);
    }

    /**
     * Constructs a new <code>MathOperationModel</code> in the subtraction mode with the specified
     * <code>value</code>.
     *
     * @param value The value that is being subtracted to the original amount.
     * @return A new <code>MathOperationModel</code> in the SUBTRACT mode.
     * @see MathOperationMode
     */
    public static MathOperationModel createSubtract(BigDecimal value) {
        return new MathOperationModel(MathOperationMode.SUBTRACT, value);
    }

    /**
     * Constructs a new <code>MathOperationModel</code> in the multiplication mode with the specified
     * <code>value</code>.
     *
     * @param value The value that is being multiplied to the original amount.
     * @return A new <code>MathOperationModel</code> in the MULTIPLY mode.
     * @see MathOperationMode
     */
    public static MathOperationModel createMultiply(BigDecimal value) {
        return new MathOperationModel(MathOperationMode.MULTIPLY, value);
    }

    /**
     * Indicates whether some other <code>MathOperationModel</code> is equal to this one.
     *
     * @param obj an <code>MathOperationModel</code> with which to compare.
     * @return true if this <code>MathOperationModel</code> is the same as the obj argument; false
     * otherwise.
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof MathOperationModel))
            return false;

        MathOperationModel model = (MathOperationModel) obj;
        return mode == model.mode && value.equals(model.value);
    }

    /**
     * An enum class that highlights the operation that can be performed.
     */
    public enum MathOperationMode {
        /**
         * The state allows for addition.
         */
        ADD,
        /**
         * The state allows for subtraction.
         */
        SUBTRACT,
        /**
         * The state allows for multiplication.
         */
        MULTIPLY,
        /**
         * The state allows for no operations.
         */
        STANDARD
    }
}
