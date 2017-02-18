package com.example.miles.ncku3dguide.GL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.example.miles.ncku3dguide.Calculator.VectorCal;

import java.util.Vector;

/**
 * Created by miles on 2015/7/21.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(1);
        Log.d("GLS", "Build");
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);
    }

    private boolean d_fing = false;

    private float th_dist;

    private float mPreviousX;
    private float mPreviousY;


    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: {
                mPreviousX = e.getX();
                mPreviousY = e.getY();
            }
            break;

            case MotionEvent.ACTION_MOVE: {

                float x = e.getX();
                float y = e.getY();

                if (d_fing) {
                    x = e.getX(e.getPointerId(0));
                    y = e.getY(e.getPointerId(0));

                    float px = e.getX(e.getPointerId(1));
                    float py = e.getY(e.getPointerId(1));

                    float dx = (x - mPreviousX) * 2 / getWidth();
                    float dy = (y - mPreviousY) * 2 / getHeight();

                    float temp_dist = VectorCal.magnitude(VectorCal.getVecByPoint(new float[]{x, y, 0}, new float[]{px, py, 0}));
                    float zoom = temp_dist - th_dist;

                    mRenderer.setZoom(zoom*2);
                    th_dist = temp_dist;


                    mRenderer.setDist(dx * 200, -dy * 200);

                } else {
                    float dx = (x - mPreviousX) * 2 / getWidth();
                    float dy = (y - mPreviousY) * 2 / getHeight();
                    float TOUCH_SCALE_FACTOR = (float) (180.0f / Math.PI);
                    if ((Math.abs(dx) < 0.12) && (Math.abs(dy) < 0.12)) {
                        mRenderer.setAngleY(dx * TOUCH_SCALE_FACTOR);
                        mRenderer.setAngleX(dy * TOUCH_SCALE_FACTOR);
                    }
                }

                mPreviousX = x;
                mPreviousY = y;
            }
            break;

            case MotionEvent.ACTION_POINTER_DOWN: {
                float x = e.getX(e.getPointerId(0));
                float y = e.getY(e.getPointerId(0));
                float px = e.getX(e.getPointerId(1));
                float py = e.getY(e.getPointerId(1));
                th_dist = VectorCal.magnitude(VectorCal.getVecByPoint(new float[]{x, y, 0}, new float[]{px, py, 0}));
                d_fing = true;
            }
            break;

            case MotionEvent.ACTION_POINTER_UP: {
                d_fing = false;
            }
            break;
        }
        return true;
    }

    public void setTranslationIdentity(){
        mRenderer.setTIdentity();
    }

    public void setRotationIdendity() {
        mRenderer.setRIdendity();
    }

    public void setUserPosition(float[] position){ mRenderer.setUserPosition(position); }

    public void setRoutObject(Vector<Integer> in_number){mRenderer.setRoutObject(in_number);}

}
