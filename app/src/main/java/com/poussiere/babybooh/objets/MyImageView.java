package com.poussiere.babybooh.objets;

/**
 * Created by poussiere on 26/07/17.
 */

public class MyImageView {
  
   public MyImageView (Context context) {
        super(context);
        setScaleType(ScaleType.MATRIX);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Drawable d = getDrawable();
        if (d != null) {
            Matrix matrix = new Matrix();
            RectF src = new RectF(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            RectF dst = new RectF(0, 0, w, h);
            matrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);
            float[] points = {
                    0, d.getIntrinsicHeight()
            };
            matrix.mapPoints(points);
            matrix.postTranslate(0, h - points[1]);
            setImageMatrix(matrix);
        }
    }
}
