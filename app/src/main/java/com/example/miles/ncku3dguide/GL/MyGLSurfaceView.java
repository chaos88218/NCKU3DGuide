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

    //是否雙指
    private boolean d_fing = false;

    private float th_dist;

    private float mPreviousX;
    private float mPreviousY;


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
                    float dx = (x - mPreviousX) * 2 / getWidth();
                    float dy = (y - mPreviousY) * 2 / getHeight();
                    float temp_dist = VectorCal.magnitude(VectorCal.getVecByPoint(new float[]{x, y, 0}, new float[]{px, py, 0}));
                    float zoom = temp_dist - th_dist;
                    mRenderer.setZoom(zoom * 2);
                    th_dist = temp_dist;

                    //計算位移
                    mRenderer.setDist(dx * 200, -dy * 200);

                } else {
                    //單指
                    float dx = (x - mPreviousX) * 2 / getWidth();
                    float dy = (y - mPreviousY) * 2 / getHeight();

                    //手指移動向量
                    float[] finger_vec = new float[]{dx, dy, 0};
                    float magnitude = VectorCal.magnitude(finger_vec);
                    //手指-畫面中心向量
                    float[] orientation_vec = VectorCal.getVecByPoint(new float[]{x, y, 0}, new float[]{getWidth()/2.0f, getHeight()/2.0f, 0});
                    orientation_vec = VectorCal.cross(orientation_vec, finger_vec);

                    float TOUCH_SCALE_FACTOR = (float) (180.0f / Math.PI);

                    //旋轉 0.12閥值避免爆轉
                    if(magnitude < 0.12){
                        if (orientation_vec[2] > 0) {
                            mRenderer.setAngleY(magnitude * TOUCH_SCALE_FACTOR);
                        }else{
                            mRenderer.setAngleY(-magnitude * TOUCH_SCALE_FACTOR);
                        }
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
        mRenderer.setUserPosition(position);
    }

    public void setRoutObject(Vector<Integer> in_number) {
        mRenderer.setRoutObject(in_number);
    }
    public void clearRoute() {
        mRenderer.clearRoute();
    }

}
