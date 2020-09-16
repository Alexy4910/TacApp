package projet.master.weatherapp.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class MyButtonViewForHome extends androidx.appcompat.widget.AppCompatButton {
    public MyButtonViewForHome(Context context) {
        super(context);
    }

    public MyButtonViewForHome(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButtonViewForHome(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(size, size);

    }
}
