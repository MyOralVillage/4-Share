package org.myoralvillage.cashcalculatormodule.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AppStateModel implements Serializable {
    private AppMode appMode;
    private List<MathOperationModel> operations;

    public AppStateModel(AppMode appMode, List<MathOperationModel> operations) {
        this.appMode = appMode;
        this.operations = operations;
    }

    public AppMode getAppMode() {
        return appMode;
    }

    public List<MathOperationModel> getOperations() {
        return operations;
    }

    public void setAppMode(AppMode appMode) {
        this.appMode = appMode;
    }

    public static AppStateModel getDefault() {
        List<MathOperationModel> operations = new ArrayList<>();
        operations.add(MathOperationModel.createStandard(new BigDecimal(0)));

        return new AppStateModel(AppMode.IMAGE, operations);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof AppStateModel))
            return false;

        AppStateModel state = (AppStateModel) obj;
        return operations.equals(state.operations) && appMode == state.appMode;
    }

    public enum AppMode {
        IMAGE,
        NUMERIC
    }
}
