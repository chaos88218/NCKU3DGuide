package com.example.miles.ncku3dguide.Model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Miles on 2017/2/18.
 */

public class Route {
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;

    //STL and render data
    float[] vertex;
    private float color[] = new float[]{1, 0, 0};

    public Route(Vector<Integer> all_nodes) {
        vertex = new float[all_nodes.size() * 3];
        for (int i = 0; i < all_nodes.size(); i++) {
            vertex[3*i] = (float) AllCampusData.myMap.myNodes.get(all_nodes.get(i)).data.x;
            vertex[3*i + 1] = (float) AllCampusData.myMap.myNodes.get(all_nodes.get(i)).data.y;
            vertex[3*i + 2] = 10;
        }

        vertexBuffer = RenderUtils.buildFloatBuffer(vertex);
        color = new float[]{color[0], color[1], color[2], 1.0f};
        float[] color = new float[vertex.length / 3 * 4];

        for (int i = 0; i < color.length; i++) {
            color[i] = color[i % 4];
        }
        colorBuffer = RenderUtils.buildFloatBuffer(color);
    }

    public void draw(GL10 gl) {
        //line
        gl.glLineWidth(8);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, vertex.length / 3);

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }

}
