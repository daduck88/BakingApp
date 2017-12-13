package com.bakingapp.android.base;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by destevancardozoj on 12/13/17.
 */

public class AspectRatioView extends FrameLayout {
  public AspectRatioView(@NonNull Context context) {
    super(context);
  }

  public AspectRatioView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public AspectRatioView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public AspectRatioView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
    int calculatedHeight = originalWidth * 9 / 16;
    int finalHeight;
    finalHeight = calculatedHeight;
    setMeasuredDimension(
        MeasureSpec.makeMeasureSpec(originalWidth, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
  }
}
