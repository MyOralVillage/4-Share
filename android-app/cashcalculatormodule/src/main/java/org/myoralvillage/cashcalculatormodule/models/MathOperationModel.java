package org.myoralvillage.cashcalculatormodule.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;

public class MathOperationModel implements Serializable {
    private MathOperationMode mode;
    private BigDecimal value;

    private MathOperationModel(MathOperationMode mode, BigDecimal value) {
        this.mode = mode;
        this.value = value;
    }

    public MathOperationMode getMode() {
        return mode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public MathOperationModel copyWithValue(BigDecimal value) {
        return new MathOperationModel(mode, value);
    }

    public static MathOperationModel createStandard(BigDecimal value) {
        return new MathOperationModel(MathOperationMode.STANDARD, value);
    }

    public static  MathOperationModel createAdd(BigDecimal value) {
        return new MathOperationModel(MathOperationMode.ADD, value);
    }

    public static MathOperationModel createSubtract(BigDecimal value) {
        return new MathOperationModel(MathOperationMode.SUBTRACT, value);
    }

    public static MathOperationModel createMultiply(BigDecimal value) {
        return new MathOperationModel(MathOperationMode.MULTIPLY, value);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof MathOperationModel))
            return false;

        MathOperationModel model = (MathOperationModel) obj;
        return mode == model.mode && value.equals(model.value);
    }

    public enum MathOperationMode {
        ADD,
        SUBTRACT,
        MULTIPLY,
        STANDARD
    }
}
