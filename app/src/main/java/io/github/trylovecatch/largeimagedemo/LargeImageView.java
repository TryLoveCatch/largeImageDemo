package io.github.trylovecatch.largeimagedemo;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lipeng21 on 2017/4/17.
 */

public class LargeImageView extends View {

    private BitmapRegionDecoder mDecoder;
    private int mImageWidth;
    private int mImageHeight;

    private volatile Rect mRect ;
    private BitmapFactory.Options mOptions;
    private boolean mIsFirstMeasure;

    public LargeImageView(Context context) {
        super(context);
        init();
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mIsFirstMeasure = true;
        mRect = new Rect();
        mOptions = new BitmapFactory.Options();
        mOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
    }

    public void setImage(int pResourcesId){
        InputStream tIs = null;
        try {
            tIs = getResources().openRawResource(pResourcesId);
            mDecoder = BitmapRegionDecoder.newInstance(tIs, false);
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(tIs, null, tmpOptions);
            BitmapFactory.decodeResource(getResources(), pResourcesId, tmpOptions);
            mImageWidth = tmpOptions.outWidth;
            mImageHeight = tmpOptions.outHeight;

            requestLayout();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(tIs!=null){
                try {
                    tIs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void moveTo(int pMoveX, int pMoveY){
        mRect.offsetTo(pMoveX, pMoveY);
        checkHeight();
        checkWidth();
        invalidate();
    }
    public void moveBy(int pMoveX, int pMoveY){
        mRect.offset(pMoveX, pMoveY);
        checkHeight();
        checkWidth();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap tBmp = mDecoder.decodeRegion(mRect, mOptions);
        Log.e("largeimagedemo", "===========" + mRect.left + ", " + mRect.right);
        canvas.drawBitmap(tBmp, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        if(mIsFirstMeasure) {
            mRect.left = 0;
            mRect.top = 0;
            mRect.right = getMeasuredWidth();
            mRect.bottom = getMeasuredHeight();
            mIsFirstMeasure = false;
        }
    }


    private void checkWidth(){


        Rect rect = mRect;
        int imageWidth = mImageWidth;

        if (rect.right > imageWidth)
        {
            rect.right = imageWidth;
            rect.left = imageWidth - getWidth();
        }

        if (rect.left < 0)
        {
            rect.left = 0;
            rect.right = getWidth();
        }
    }


    private void checkHeight() {

        Rect rect = mRect;
        int imageHeight = mImageHeight;

        if (rect.bottom > imageHeight)
        {
            rect.bottom = imageHeight;
            rect.top = imageHeight - getHeight();
        }

        if (rect.top < 0)
        {
            rect.top = 0;
            rect.bottom = getHeight();
        }
    }

}
