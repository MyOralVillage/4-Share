package org.myoralvillage.cashcalculatormodule.views;

        import android.content.Context;
        import android.util.AttributeSet;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.LinearLayout;

        import androidx.annotation.Nullable;

        import org.myoralvillage.cashcalculatormodule.R;
        import org.myoralvillage.cashcalculatormodule.views.listeners.NumberPadListener;

        import java.math.BigDecimal;

/**
 * A view class used to monitor and render the display of the number pad.
 *
 * @author Hamza Mahfooz
 * @author Peter Panagiotis Roubatsis
 * @author Yujie Wu
 *
 * @see LinearLayout
 * @see android.view.View.OnTouchListener
 */
public class NumberPadView extends LinearLayout implements View.OnTouchListener {
    private NumberPadListener listener = null;
    private static final long MAX_TOUCH_DURATION = 250;
    private float touchDownX;
    private float touchDownY;

    /**
     * the value that is being entered in this view.
     *
     * @see StringBuilder
     */
    private final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Constructs a <code>CountingTableSurfaceView</code> in the given Android context with the
     * given attributes.
     *
     * @param context the context of the application.
     * @param attrs A collection of attributes found in the xml layout.
     */
    public NumberPadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.number_pad, this);
        initializeNumberpad();
        setOnTouchListener(this);
    }

    /**
     * Initializes the number pad in this view.
     */
    private void initializeNumberpad(){
        View.OnClickListener listener = v -> {
            String text;

            if (v instanceof Button) {
                text = ((Button) v).getText().toString();
            } else {
                text = v.getContentDescription().toString();
            }

            switch (text) {
                case "check":
                    check(stringBuilderToBigDecimal(stringBuilder));
                    stringBuilder.setLength(0);
                    return;
                case "back":
                    if (stringBuilder.length() > 0) {
                        stringBuilder.setLength(stringBuilder.length() - 1);
                        back(stringBuilderToBigDecimal(stringBuilder));
                    }
                    break;
                default:
                    if (stringBuilder.length() == 0 && text.equals("0")) {
                        return;
                    }
                    stringBuilder.append(text);
                    number(stringBuilderToBigDecimal(stringBuilder));
                    break;
            }
        };

        for (View button : new View[] {findViewById(R.id.zero), findViewById(R.id.one),
                findViewById(R.id.two), findViewById(R.id.three), findViewById(R.id.four),
                findViewById(R.id.five), findViewById(R.id.six), findViewById(R.id.seven),
                findViewById(R.id.eight), findViewById(R.id.nine), findViewById(R.id.check),
                findViewById(R.id.back)}) {
            button.setOnClickListener(listener);
        }
    }

    /**
     * Sets the value of this view.
     *
     * @param value the new value of this view.
     */
    public void setValue(BigDecimal value){
        stringBuilder.setLength(0);
        stringBuilder.append(value.toString());
    }

    /**
     * Handles the check operation, which affects the value.
     *
     * @param value the value of this view.
     */
    private void check(BigDecimal value) {
        if (listener != null)
            listener.onCheck(value);
    }

    /**
     * Handles the back operation, which affects the value.
     *
     * @param value the value of this view.
     */
    private void back(BigDecimal value) {
        if (listener != null)
            listener.onBack(value);
    }

    /**
     * Handles when a number is tapped.
     *
     * @param value the value of this view.
     */
    private void number(BigDecimal value) {
        if (listener != null)
            listener.onTapNumber(value);
    }

    /**
     * Converts a <code>StringBuilder</code>, to a <code>BigDecimal</code>.
     *
     * @param stringBuilder the <code>StringBuilder</code> to be converted.
     * @return the <code>StringBuilder</code> in a <code>BigDecimal</code> format.
     */
    private static BigDecimal stringBuilderToBigDecimal(StringBuilder stringBuilder) {
        return stringBuilder.length() > 0 ?
                new BigDecimal(Integer.valueOf(stringBuilder.toString())) : BigDecimal.ZERO;
    }

    /**
     * Sets the listener to allow the view's events to be handled in the callbacks.
     *
     * @param listener the listener of this view.
     * @see NumberPadListener
     */
    public void setListener(NumberPadListener listener) {
        this.listener = listener;
    }

    /**
     * Called when a touch event is dispatched to the view, <code>NumberPadView</code>. This
     * allows listeners to get a chance to respond before the target view.
     *
     * @param v the view the touch event has been passed to.
     * @param event The MotionEvent object containing full information about the event.
     * @return true if the listener has consumed the event; false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (listener != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touchDownX = event.getX();
                touchDownY = event.getY();
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP &&
                    (event.getEventTime() - event.getDownTime()) <= MAX_TOUCH_DURATION &&
                    Math.abs(event.getY() - touchDownY) > 2 * Math.abs(event.getX() - touchDownX)) {
                listener.onVerticalSwipe();
                return true;
            }
        }
        return false;
    }
}