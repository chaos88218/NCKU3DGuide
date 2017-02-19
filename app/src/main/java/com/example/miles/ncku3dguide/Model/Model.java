package com.example.miles.ncku3dguide.Model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;


import com.example.miles.ncku3dguide.Calculator.VectorCal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by miles on 2015/11/17.
 */
public class Model {

    //OpenGL Buffer
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private FloatBuffer normalBuffer;

    //STL and render data
    float[] vertex;
    private float color[];

    //Loaded flag
    private boolean Loaded = false;

    Model() {
    }

    //模型讀stl初始化
    Model(String string, float alpha, Context context) {
        vertex = ReadStlBinary(string, context);

        if (!Float.isNaN(vertex[0])) {
            float[] normals = VectorCal.getNormByPtArray(vertex);
            setColor(new float[]{1.0f, 1.0f, 1.0f}, alpha);
            Loaded = true;

            vertexBuffer = RenderUtils.buildFloatBuffer(vertex);
            normalBuffer = RenderUtils.buildFloatBuffer(normals);
            colorBuffer = RenderUtils.buildFloatBuffer(color);

        } else {
            Log.v(string + "loaded E*: ", "UnLoaded");
        }
    }

    //模型讀stl初始化加顏色
    Model(String string, float[] in_color, float alpha, Context context) {
        this(string, alpha, context);
        setColor(in_color, alpha);
    }

    public boolean isLoaded() {
        return Loaded;
    }

    protected void setColor(float[] in_color, float alpha) {
        in_color = new float[]{in_color[0], in_color[1], in_color[2], alpha};
        color = new float[vertex.length / 3 * 4];

        for (int i = 0; i < color.length; i++) {
            color[i] = in_color[i % 4];

        }

        colorBuffer = RenderUtils.buildFloatBuffer(color);
    }

    public void draw(GL10 gl) {
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);

        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        if (Loaded) {
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertex.length / 3);
        }

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

    }

    public float[] ReadStlBinary(String fileName, Context context) {
        float[] ospVert = new float[0];

        AssetManager am = context.getAssets();
        InputStream inputStream;
        try {
            inputStream = am.open(fileName);
            int count;
            byte[] buffer = new byte[84];
            inputStream.read(buffer);
            count = ByteBuffer.wrap(buffer, 80, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();

            ospVert = new float[count * 9];
            buffer = new byte[50 * count];
            inputStream.read(buffer);
            int num1 = 0;
            int num2 = 0;

            for (int Line = 0; Line < count; Line++) {
                ByteBuffer temp = ByteBuffer.wrap(buffer, num2 + 12, 36).order(ByteOrder.LITTLE_ENDIAN);
                for (int jjj = 0; jjj < 9; jjj++) {
                    ospVert[num1 + jjj] = temp.getFloat();
                }
                num1 += 9;
                num2 += 50;
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new float[]{Float.NaN};
        } catch (IOException e) {
            e.printStackTrace();
            return new float[]{Float.NaN};
        }

        return ospVert;
    }

    //清除暫存
    public void clearAllData() {
        if (Loaded) {
            Loaded = false;
            vertexBuffer.clear();
            colorBuffer.clear();
            normalBuffer.clear();

            vertex = null;
            color = null;
        }
    }
}
