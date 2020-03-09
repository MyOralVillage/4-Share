package org.myoralvillage.cashcalculatormodule.models;

import androidx.annotation.Nullable;

import java.math.BigDecimal;

/**
 *  A model class used to represent the bills or coins of the type of currency being accessed.
 *
 * @author Alexander Yang
 * @author Peter Panagiotis Roubatsis
 * @see java.lang.Object
 */
public class DenominationModel implements Comparable<DenominationModel> {
    /**
     * The value of the bill or coin.
     * @see BigDecimal
     */
    private BigDecimal value;

    /**
     * The resource identifier for the bill or coin. This resource is mainly used in the
     * <code>CurrencyScrollbarView</code>.
     */
    private int imageResource;

    /**
     * The resource identifier for the bill or coin when it is represented as being folded. This
     * resource is mainly used in the <code>CountingTableView</code>. Note that
     * the coin, in reality, cannot be folded so it appears as the same image as if it is not folded.
     */
    private int imageResourceFolded;

    /**
     * A float value used to adjust the size of the images.
     */
    private float scaleFactor;

    /**
     * Constricts a new <code>DenominationModel</code> given its image resources and the value for
     * this bill or coin.
     *
     * @param value The value of the denomination.
     * @param imageResource The resource identifier for the denomination.
     * @param imageResourceFolded The resource identifier for the denomination when the denomination
     *                            is folded.
     * @param scaleFactor A float value used to adjust the size of the images.
     *
     * @see BigDecimal
     */
    public DenominationModel(BigDecimal value, int imageResource, int imageResourceFolded, float scaleFactor) {
        this.value = value;
        this.imageResource = imageResource;
        this.imageResourceFolded = imageResourceFolded;
        this.scaleFactor = scaleFactor;
    }

    /**
     * Returns the value associated with this model (the value that the bill or coin is representing).
     *
     * @return the value of this model.
     * @see BigDecimal
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Returns the resource identifier associated with this model (the image of the bill or coin).
     *
     * @return the resource identifier of this model.
     */
    public int getImageResource() {
        return imageResource;
    }

    /**
     * Returns the resource identifier associated with this model (the image of the bill or coin)
     * when this model is being represented in a folded state.
     *
     * @return the resource identifier of this model in a folded state.
     */
    public int getImageResourceFolded(){
        return imageResourceFolded;
    }

    /**
     * Returns the scale factor associated with this model.
     *
     * @return the scale factor of this model.
     */
    public float getScaleFactor() {
        return scaleFactor;
    }

    /**
     * Compares this object with the specified object for order. Returns a negative integer, zero,
     * or a positive integer as this object is less than, equal to, or greater than the specified
     * object.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     * to, or greater than the specified object.
     */
    @Override
    public int compareTo(DenominationModel o) {
        return this.value.compareTo(o.value);
    }

    /**
     * Indicates whether some other <code>DenominationModel</code> is equal to this one.
     *
     * @param obj a <code>DenominationModel</code> with which to compare.
     * @return true if this <code>DenominationModel</code> is the same as the obj argument; false
     * otherwise.
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof DenominationModel))
            return false;

        DenominationModel dm = (DenominationModel) obj;
        return value.equals(dm.value) && imageResource == dm.imageResource;
    }
}
