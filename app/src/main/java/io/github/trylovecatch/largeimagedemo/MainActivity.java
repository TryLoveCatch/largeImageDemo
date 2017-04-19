package io.github.trylovecatch.largeimagedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private LargeImageView mLargeImageView;
    private int mLastMoveX;
    private int mLastMoveY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);this.getApplicationContext().getApplicationContext();

        mLargeImageView = (LargeImageView) findViewById(R.id.large_image_view);
        mLargeImageView.setImage(R.drawable.guide_page_bg_icon);

        mLargeImageView.setOnTouchListener(new View.OnTouchListener() {

            int mLastX;
            int mLastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mLastX = (int)event.getX();
                        mLastY = (int)event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int tMoveX = mLastX - (int)event.getX();
                        int tMoveY = mLastY - (int)event.getY();
                        mLargeImageView.moveBy(tMoveX - mLastMoveX, tMoveY - mLastMoveY);
                        mLastMoveX = tMoveX;
                        mLastMoveY = tMoveY;
                        break;
                    case MotionEvent.ACTION_UP:
                        mLastMoveX = 0;
                        mLastMoveY = 0;
                        break;
                }

                return true;
            }
        });
    }
}
