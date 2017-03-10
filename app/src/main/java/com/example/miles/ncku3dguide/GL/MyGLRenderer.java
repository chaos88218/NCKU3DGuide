package com.example.miles.ncku3dguide.GL;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;


import com.example.miles.ncku3dguide.Calculator.VectorCal;
import com.example.miles.ncku3dguide.MainActivity;
import com.example.miles.ncku3dguide.Model.AllCampusData;
import com.example.miles.ncku3dguide.Model.RenderUtils;
import com.example.miles.ncku3dguide.Model.Route;

import java.nio.FloatBuffer;
import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by miles on 2015/7/21.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    //controls
    private float range = 1200;

    //light 0
    private float[] ambientLight = new float[]{0f, 0f, 0f, 1.0f};
    private float[] diffuseLight = new float[]{0.35f, 0.35f, 0.35f, 1.0f};
    private float[] lightPos = new float[]{400, -100, 800, 0.0f};
    //light 1
    private float[] lightPos1 = new float[]{-250, +300, -600, 0.0f};

    private float mAngleY = 0;
    private float mDistX = 0;
    private float mDistY = 0;
    private float mDistZ = 0;

    //personal posi
    private float[] per_posi = new float[]{0, 0};
    private Route route;

    public MyGLRenderer() {
        super();
        Log.d("Render", "Build");
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //Render settings
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glFrontFace(GL10.GL_CCW);

        gl.glMatrixMode(GL10.GL_PROJECTION);

        //Light setting and bind buffer
        FloatBuffer ambientBuffer = RenderUtils.buildFloatBuffer(ambientLight);
        FloatBuffer diffuseBuffer = RenderUtils.buildFloatBuffer(diffuseLight);
        FloatBuffer lightPosBuffer = RenderUtils.buildFloatBuffer(lightPos);

        //light
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, ambientBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, diffuseBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosBuffer);

        lightPosBuffer = RenderUtils.buildFloatBuffer(lightPos1);
        gl.glEnable(GL10.GL_LIGHT1);
        gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, ambientBuffer);
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, diffuseBuffer);
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuseBuffer);
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosBuffer);

        gl.glEnable(GL10.GL_COLOR_MATERIAL);

    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        float rate = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 30, rate, 1f, 10000);
    }


    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(223 / 255.0f, 1, 1, 1.0f);
        //gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();
        load_matrix(gl);
        float rotDegTemp = mAngleY;
        //先畫校區模型
        AllCampusData.cc.drawBuilding(gl);
        AllCampusData.ck.drawBuilding(gl);
        AllCampusData.sl.drawBuilding(gl);
        AllCampusData.kf.drawBuilding(gl);
        AllCampusData.jy.drawBuilding(gl);
        AllCampusData.ls.drawBuilding(gl);
        AllCampusData.cs.drawBuilding(gl);

        if (MainActivity.navi) {
            route.draw(gl);
        }

        //地板後畫不然會破圖
        if (AllCampusData.cc.isLoaded()) {
            AllCampusData.cc.draw(gl);
        }
        if (AllCampusData.ck.isLoaded()) {
            AllCampusData.ck.draw(gl);
        }
        if (AllCampusData.sl.isLoaded()) {
            AllCampusData.sl.draw(gl);
        }
        if (AllCampusData.kf.isLoaded()) {
            AllCampusData.kf.draw(gl);
        }
        if (AllCampusData.jy.isLoaded()) {
            AllCampusData.jy.draw(gl);
        }
        if (AllCampusData.ls.isLoaded()) {
            AllCampusData.ls.draw(gl);
        }
        if (AllCampusData.cs.isLoaded()) {
            AllCampusData.cs.draw(gl);
        }
        if (MainActivity.stop_info) {
            AllCampusData.stop_sign.draw(gl);
        }
        //定位使用者位置
        per_posi = VectorCal.getMapPosition(AllCampusData.gpsTracker.getLatitude(), AllCampusData.gpsTracker.getLongitude());
        gl.glPushMatrix();
        gl.glTranslatef(per_posi[0] + AllCampusData.map_zero[0], per_posi[1] + AllCampusData.map_zero[1], 10);
        gl.glRotatef(-rotDegTemp, 0, 0, 1);
        AllCampusData.loco.draw(gl);
        gl.glPopMatrix();

        if (MainActivity.navi) {
            float[] desti_location = route.getDestination();
            gl.glPushMatrix();
            gl.glTranslatef(desti_location[0], desti_location[1], 10);
            gl.glRotatef(-rotDegTemp, 0, 0, 1);
            AllCampusData.navi.draw(gl);
            gl.glPopMatrix();
        }

        gl.glPopMatrix();
    }

    private void load_matrix(GL10 gl) {
        //drawing Matrix
        //世界操作
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, mDistZ);

        //模型操作
        gl.glTranslatef(0, 0, -range);
        gl.glRotatef(-50, 1, 0, 0);
        gl.glRotatef(mAngleY, 0, 0, 1);
        gl.glTranslatef(mDistX, mDistY, 0);
        gl.glTranslatef(-AllCampusData.map_zero[0], -AllCampusData.map_zero[1], 0);
    }

    void setUserPosition(float[] position) {
        per_posi[0] = position[0];
        per_posi[1] = position[1];
        mDistX = -per_posi[0];
        mDistY = -per_posi[1];
    }
    void getUserPosition(float[] position){
        if (position.length < 2)return;
        position[0] = per_posi[0];
        position[1] = per_posi[1];
    }
    void getViewPosition(float[] position){
        if (position.length < 2)return;
        position[0] = mDistX;
        position[1] = mDistY;
    }
    void setTIdentity() {
        mDistX = 0;
        mDistY = 0;
        mDistZ = 0;
    }
    void setRIdendity() {
        mAngleY = 0;
    }

    //使用者旋轉
    void addAngleY(float angle) {
        mAngleY += angle;
        if (Math.abs(mAngleY) > 360) mAngleY -= 360;
        if (Math.abs(mAngleY) <= 0) mAngleY += 360;
    }
    void setAngleY(float angle){
        mAngleY = angle;
        if (Math.abs(mAngleY) > 360) mAngleY -= 360;
        if (Math.abs(mAngleY) <= 0) mAngleY += 360;
    }
    float getmAngleY(){return mAngleY;}
    //使用者平移
    void setDist(float in_x, float in_y) {
        float[] temp = new float[16];
        Matrix.setIdentityM(temp, 0);
        Matrix.rotateM(temp, 0, -mAngleY, 0, 0, 1);
        float[] result = new float[]{in_x, in_y, 0, 1};
        Matrix.multiplyMV(result, 0, temp, 0, result, 0);

        mDistX += result[0];
        mDistY += result[1];
    }

    void setZoom(float in_z) {
        mDistZ += in_z;
        if (mDistZ > 1100) mDistZ = 1100;
        if (mDistZ < -6000) mDistZ = -6000;
    }

    public void setRoutObject(Vector<Integer> in_number) {
        route = new Route(in_number);
    }
}
