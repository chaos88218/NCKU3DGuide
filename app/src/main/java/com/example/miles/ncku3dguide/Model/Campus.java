package com.example.miles.ncku3dguide.Model;

import android.content.Context;
import android.util.Log;

import com.example.miles.ncku3dguide.Calculator.VectorCal;

import javax.microedition.khronos.opengles.GL10;

//校區物件繼承自Model
public class Campus extends Model {

    private Context mContext;

    private int model_num;
    private String[] model_files;
    private Model[] all_models;
    private float[] model_color;
    private boolean[] allisLoaded;
    public float[] loactions;

    private float[] model_corner;

    public Campus() {
        super();
    }

    //匯入地板模型初始化
    public Campus(String string, float alpha, Context context, int in_num) {
        super(string, new float[]{50 / 255.0f, 205 / 255.0f, 50 / 255.0f}, alpha, context);

        mContext = context;
        model_num = in_num;
        all_models = new Model[in_num];
        allisLoaded = new boolean[in_num];
    }

    //設定校區內所有建築物模型
    public boolean set_Models(String[] in_files, float[] in_color) {
        if (in_files.length != model_num) {
            return false;
        }
        model_files = in_files;
        model_color = in_color;
        loadSTL.start();

        return true;
    }

    //設定建築物座標位置，以Jacob節點功能取代
    public boolean set_location(float[] in_location) {
        loactions = in_location;
        return true;
    }

    //設定校區四點座標位置，以Jacob節點功能取代
    public void set_corner(float[] in_model) {
        model_corner = in_model;
    }

    //偵測是否在校區內，以Jacob節點功能取代
    public boolean inside_this(float[] per_posi) {

        Log.d("Posi", per_posi[0] + " " + per_posi[1]);

        float[] vector = new float[8];
        for (int i = 0; i < 8; i++) {
            vector[i] = model_corner[i] - per_posi[i % 2] - AllCampusData.map_zero[i % 2];
        }

        for (int i = 0; i < 4; i++) {
            float[] direct = VectorCal.cross(new float[]{vector[2 * i], vector[2 * i + 1], 0}
                    , new float[]{vector[(2 * i + 2) % 8], vector[(2 * i + 3) % 8], 0});
            float deg = VectorCal.getAngleDeg(
                    new float[]{vector[2 * i], vector[2 * i + 1], 0}
                    , new float[]{vector[(2 * i + 2) % 8], vector[(2 * i + 3) % 8], 0}

            );

            if (direct[2] < 0) {
                deg = 360 - deg;
            }

            if (deg > 180) {
                return false;
            }
        }

        return true;
    }

    //偵測最近建築物，以Jacob節點功能取代
//    public String get_nearest(float[] per_posi) {
//        int min_index = 0;
//        float now_dist = 10000;
//        Log.d("Now_posi", (per_posi[0] + AllCampusData.map_zero[0]) + " " + (per_posi[1] + AllCampusData.map_zero[1]));
//        for (int i = 0; i < model_name.length; i++) {
//            float temp = VectorCal.magnitude(new float[]{per_posi[0] + AllCampusData.map_zero[0] - this.loactions[2 * i]
//                    , per_posi[1] + +AllCampusData.map_zero[1] - this.loactions[2 * i + 1], 0});
//            if (temp < now_dist) {
//                now_dist = temp;
//                min_index = i;
//            }
//        }
//        return this.model_name[min_index];
//    }

    //設定校區內所有建築物模型讀取
    private Thread loadSTL = new Thread(new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < model_num; i++) {
                all_models[i] = new Model(model_files[i], model_color, 1.0f, mContext);
                allisLoaded[i] = all_models[i].isLoaded();
            }
        }
    });

    //設定校區內所有建築物模型繪圖
    public void drawBuilding(GL10 gl) {
        for (int i = 0; i < model_num; i++) {
            if (allisLoaded[i]) {
                all_models[i].draw(gl);
            }
        }
    }

    //清除暫存資料
    @Override
    public void clearAllData() {
        super.clearAllData();
        for (int i = 0; i < model_num; i++) {
            if (allisLoaded[i]) {
                all_models[i].clearAllData();
            }
        }
        model_files = null;
        model_color = null;
        loactions = null;
        model_corner = null;
    }
}
