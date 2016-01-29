package biz.mobinex.smartfaceplugin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsoluteLayout;

/**
 * Test class for Smartface Android UI plugin.
 * Extends View and implements WithGeometry interface.
 * Uses AbsoluteLayout.
 *
 * @author Olcay Ertaş
 * @version 1.0
 * @since 22.01.2016
 */
public class BlackBox extends View implements io.smartface.android.AndroidUI.WithGeometry {

    private int left = 20;
    private int top = 300;
    private int width = 300;
    private int height = 300;
    private int right = 320;
    private int bottom = 320;


    public BlackBox(Context context) {
        super(context);
        setBackgroundColor(Color.BLACK);
    }

    public BlackBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.BLACK);
    }

    public BlackBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundColor(Color.BLACK);
    }

    public void setLayoutParams(AbsoluteLayout.LayoutParams params) {
        super.setLayoutParams(params);
    }

    public void setLayoutParams(int left, int top, int width, int height) {
        setLayoutParams(new AbsoluteLayout.LayoutParams(width, height, left, top));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setTop(top);
        setBottom(bottom);
        setRight(right);
        setLeft(left);
        setMeasuredDimension(getLayoutParams().width, getLayoutParams().height);
    }

    @Override
    public void setPosition__N(int left, int top, int width, int height, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.right = right;
        this.bottom = bottom;
        requestLayout();
        invalidate();
    }

    @Override
    public void setVisible__N(boolean visible, boolean enabled, boolean showOnTop) {

        if (visible)
            this.setVisibility(VISIBLE);
        else this.setVisibility(GONE);

        setEnabled(enabled);
        requestLayout();
        invalidate();
    }

    @Override
    public void setElevation__N(float elevation) {

    }

    @Override
    public void resetZ__N(float alpha) {

    }
}
