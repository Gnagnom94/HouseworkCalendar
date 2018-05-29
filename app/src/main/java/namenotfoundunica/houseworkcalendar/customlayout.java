package namenotfoundunica.houseworkcalendar;

import android.R;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.Checkable;

public class customlayout extends ConstraintLayout implements Checkable{

    public static final int[] CHECKED_STATE = {R.attr.state_checked};
    private boolean mChecked;

    public customlayout(Context context) {
        super(context);
    }

    public customlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public customlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        refreshDrawableState();
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        mChecked = !mChecked;
        refreshDrawableState();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] states =  super.onCreateDrawableState(extraSpace + 1);
        if (mChecked){
            mergeDrawableStates(states, CHECKED_STATE);
        }
        return states;
    }
}
