package com.example.miles.ncku3dguide.GL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.example.miles.ncku3dguide.Calculator.VectorCal;

import java.util.Arrays;
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

    //是否雙指
    private boolean d_fing = false;

    private float th_dist;

    private float mPreviousX;
    private float mPreviousY;
    private float[] mPreviousVec = new float[3];


    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction() & MotionEvent.ACTION_MASK) {

            //觸碰記下單指的點
            case MotionEvent.ACTION_DOWN: {
                mPreviousX = e.getX();
                mPreviousY = e.getY();
            }
            break;

            case MotionEvent.ACTION_MOVE: {

                //移動紀錄移動後的點
                float x = e.getX();
                float y = e.getY();

                if (d_fing) {
                    //雙指

                    //取主要指螢幕座標
                    x = e.getX(e.getPointerId(0));
                    y = e.getY(e.getPointerId(0));
                    //取次要指螢幕座標
                    float px = e.getX(e.getPointerId(1));
                    float py = e.getY(e.getPointerId(1));

                    //計算拉近拉遠
                    float temp_dist = VectorCal.magnitude(VectorCal.getVecByPoint(new float[]{x, y, 0}, new float[]{px, py, 0}));
                    float zoom = temp_dist - th_dist;
                    mRenderer.setZoom(zoom * 2);
                    th_dist = temp_dist;


                    //手指移動向量
                    float[] finger_vec = new float[]{x - px, y - py, 0};
                    float magnitude = VectorCal.getAngleDeg(mPreviousVec, finger_vec);
                    //手指-畫面中心向量
                    float[] orientation_vec = VectorCal.cross(finger_vec, mPreviousVec);

                    //旋轉 0.12閥值避免爆轉
                    if (magnitude < 10) {
                        if (orientation_vec[2] > 0) {
                            mRenderer.addAngleY(magnitude);
                        } else {
                            mRenderer.addAngleY(-magnitude);
                        }
                    }
                    mPreviousVec = finger_vec;
                } else {
                    //單指
                    float dx = (x - mPreviousX) * 2 / getWidth();
                    float dy = (y - mPreviousY) * 2 / getHeight();

                    //計算位移
                    if ((dx < 0.25) && (dy < 0.25)) {
                        mRenderer.setDist(dx * 200, -dy * 200);
                        Log.d("Distance", dx + " " + dy);
                    }
                }

                mPreviousX = x;
                mPreviousY = y;
            }
            break;

            //次要指頭按下
            case MotionEvent.ACTION_POINTER_DOWN: {
                float x = e.getX(e.getPointerId(0));
                float y = e.getY(e.getPointerId(0));
                float px = e.getX(e.getPointerId(1));
                float py = e.getY(e.getPointerId(1));
                th_dist = VectorCal.magnitude(VectorCal.getVecByPoint(new float[]{x, y, 0}, new float[]{px, py, 0}));

                mPreviousVec = new float[]{x - px, y - py, 0};
                d_fing = true;
            }
            break;

            //次要指頭放開
            case MotionEvent.ACTION_POINTER_UP: {
                d_fing = false;
            }
            break;
        }
        return true;
    }

    public void setTranslationIdentity() {
        mRenderer.setTIdentity();
    }

    public void setRotationIdendity() {
        mRenderer.setRIdendity();
    }

    public void setUserPosition(float[] position) {
        setUserPosition(position, 0);
    }

    public void setUserPosition(float[] position, float direction) {
        final int animeIterCount = 50;

        float[] lastPosition = new float[2];
        mRenderer.getViewPosition(lastPosition);
        float lastDirection = mRenderer.getmAngleY();
        if (lastDirection >= 360) lastDirection -= 360;

        final float[] iterPosition = {(position[0] + lastPosition[0]) / animeIterCount, (position[1] + lastPosition[1]) / animeIterCount};
        final float iterDirection;
        if (Math.abs(direction - lastDirection) < Math.abs(direction - (lastDirection - 360)))
            iterDirection = (direction - lastDirection) / animeIterCount;
        else
            iterDirection = (direction - (lastDirection - 360)) / animeIterCount;
        final float[] finalPosition = {position[0], position[1]};
        final float finalDirection = direction;

        //perform animation thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = animeIterCount; i >= 0; i--) {
                    float[] animePos = {finalPosition[0] - (iterPosition[0] * i), finalPosition[1] - (iterPosition[1] * i)};
                    mRenderer.setUserPosition(animePos);
                    mRenderer.setAngleY(finalDirection - (iterDirection * i));
                    try {
                        Thread.sleep(13);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void setRoutObject(Vector<Integer> in_number) {
        mRenderer.setRoutObject(in_number);
    }

}
