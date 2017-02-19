package com.example.miles.ncku3dguide.Model;

import java.nio.FloatBuffer;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

//導航路徑繪圖物件

public class Route {
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;

    //STL and render data
    private float[] vertex;
    private float color[] = new float[]{1, 0, 0};
    private int last_index;

    public Route(Vector<Integer> all_nodes) {
        vertex = new float[all_nodes.size() * 3];
        for (int i = 0; i < all_nodes.size(); i++) {
            vertex[3 * i] = (float) AllCampusData.myMap.myNodes.get(all_nodes.get(i)).data.x;
            vertex[3 * i + 1] = (float) AllCampusData.myMap.myNodes.get(all_nodes.get(i)).data.y;
            vertex[3 * i + 2] = 10;
        }

        last_index = all_nodes.get(all_nodes.size() - 1);

        vertexBuffer = RenderUtils.buildFloatBuffer(vertex);
        color = new float[]{color[0], color[1], color[2], 1.0f};
        float[] color1 = new float[vertex.length / 3 * 4];

        for (int i = 0; i < color.length; i++) {
            color1[i] = color[i % 4];
        }
        colorBuffer = RenderUtils.buildFloatBuffer(color1);
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

    public float[] getDestination() {
        if (!AllCampusData.myMap.myNodes.isEmpty()) {
            return new float[]{(float) AllCampusData.myMap.myNodes.get(last_index).data.x
                    , (float) AllCampusData.myMap.myNodes.get(last_index).data.y};
        }
        return new float[]{0, 0};
    }

}
