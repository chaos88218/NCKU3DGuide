package com.example.miles.ncku3dguide.MapNavi;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;


import com.example.miles.ncku3dguide.Calculator.VectorCal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Vector;


public class MyMap {
    public int Node_num; /// 此地圖點數量
    String filepath;  // 讀取締圖路徑
    Double MaxDist = 10000000.0;
    // public Nodes[] myNodes;
    public ArrayList<Nodes> myNodes;
    public Vector<Double> Dist = new Vector<Double>();
    public Vector<Double> Dist2 = new Vector<Double>();
    Vector<Integer> tempbuf = new Vector<Integer>();

    boolean isLoaded;

    public MyMap() {
        isLoaded = false;
    }

    public Vector<Integer> setRoute(int p1, int p2) {
        tempbuf.clear();
        tempbuf.add(p1);
        Integer last = p1;
        Integer[] neiborbuffer = {-1, -1, -1, -1, -1};
        Integer tp1 = p1, tp2 = p2;
        Double minF = MaxDist;
        Double distance = 0.0;
        Double tempd = getDistance(Dist2, p1, p2, Node_num);
        Integer tempind = -1;
        Boolean finish = false;
        String tempstring = "";
        if (tempd >= MaxDist) {
            return tempbuf;
        }
        while (true) {
            minF = MaxDist;
            for (int i = 0; i < Node_num; i++) {
                if (i != tp1 && i != last && Dist.get(tp1 * Node_num + i) < MaxDist)// 不能是上一個//不能是不連通的點
                {
                    //for (int k = 0; k < tempbuf.size(); k++) // 不能是走過的節點
                    //{
                    //	if (i == tempbuf[k])
                    //}
                    //Do F = G + H // G = dist to next node, H = dist to end;
                    if (i == tp2) {
                        finish = true;
                        tempind = tp2;
                        break;
                    }
                    //tempd = distance;
                    Double g = getDistance(Dist, tp1, i, Node_num);
                    Double H = getDistance(Dist2, i, tp2, Node_num);
                    //tempd += getDistance(Dist, tp1, i, Node_num); // 原有distance 加上到分支個點距離 = F

                    if (minF >= g + H) // G + H
                    {
                        minF = g + H;
                        tempind = i;
                        //break;
                    }
                }
            }
            distance += minF;
            last = tp1;
            tempbuf.add(tempind);
            tempstring += String.valueOf(tempind) + ",";
            tp1 = tempind;
            if (finish == true) {
                //cout << "dist = " << distance;
                //ShowRoute_Text.setText(tempstring);
                break;
            }
        }
        return tempbuf;
    }

    public Double getDistance(Vector<Double> D, int p1, int p2, int size) {
        return D.get(p1 * size + p2);
    }

    public void LoadMap(String filename, Context context) {
        InputStream inputStream = null;
        try {
            AssetManager mngr = context.getAssets();
            inputStream = mngr.open(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = br.readLine();

            //Log.d("txt", line);
            //// 取名字的STRING
            String[] namestr = line.split(",");//[呼叫函式]將字串作切割,以空白" " 來切該字串
            //// 取座標點
            String line_2nd = br.readLine();
            String[] DataPoint = line_2nd.split(",");
            Node_num = DataPoint.length;
            myNodes = new ArrayList<Nodes>();
            for (int i = 0; i < Node_num; i++) {
                String[] tempxy = DataPoint[i].split(" ");
                Points p = new Points(Double.valueOf(tempxy[0]), Double.valueOf(tempxy[1]));
                Nodes tempnode = new Nodes();
                tempnode.setNodes(p, i);
                tempnode.setName(namestr[i]);
                myNodes.add(tempnode);

            }
            ///// 建立相鄰矩陣
            String showData = "";
            for (int i = 0; i < Node_num; i++) {
                String templine = br.readLine();
                String[] tempD = templine.split(",");
                Double tempvalue = 0.00;
                for (int j = 0; j < Node_num; j++) {
                    try {
//                        if(tempD[j].equals("x")) {
//                            tempvalue = MaxDist;
//                        }
                        if (tempD[j].equals("0"))
                            tempvalue = 0.0;
                        else
                            tempvalue = Double.parseDouble(tempD[j]);
                        // BigDecimal a = new BigDecimal(tempD[j]);
                        Dist.add(tempvalue);
                    } catch (NumberFormatException e) {
                        Log.e("IOE", e.toString());
                        isLoaded = false;
                    }
                    //showData = showData + tempD[j]+" ";

                }
                //showData += "\n";
            }
            for (int i = 0; i < Node_num; i++) {
                String templine = br.readLine();
                String[] tempD = templine.split(",");
                Double tempvalue = 0.00;
                for (int j = 0; j < Node_num; j++) {
                    try {
//                        if(tempD[j].equals("x")) {
//                            tempvalue = MaxDist;
//                        }
                        if (tempD[j].equals("0"))
                            tempvalue = 0.0;
                        else
                            tempvalue = Double.parseDouble(tempD[j]);
                        // BigDecimal a = new BigDecimal(tempD[j]);
                        Dist2.add(tempvalue);
                    } catch (NumberFormatException e) {
                        Log.e("IOE", e.toString());
                        isLoaded = false;
                    }
                    //showData = showData + tempD[j]+" ";

                }
                //showData += "\n";
            }
            //runDist();
            br.close();
            isLoaded = true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOE", e.toString());
            isLoaded = false;
        }
    }

    public void runDist() {
        Dist2 = (Vector) Dist.clone();
        for (int k = 0; k < Node_num; k++)
            for (int i = 0; i < Node_num; i++)
                for (int j = 0; j < Node_num; j++) {
                    if (Dist2.get(i * Node_num + j) > Dist2.get(i + k * Node_num) + Dist2.get(k * Node_num + j)) {
                        Dist2.set(i * Node_num + j, Dist2.get(i + k * Node_num) + Dist2.get(k * Node_num + j));
                    }
                }
        String tempstring = "";
        for (int i = 0; i < Node_num; i++) {
            for (int j = 0; j < Node_num; j++) {
                String valstring = String.valueOf(Dist2.get(i * Node_num + j));
                tempstring += valstring + " ";
            }
            tempstring += "\n";
        }
    }

    public int get_location_index(String name) {
        for (int i = 0; i < Node_num; i++) {
            if (myNodes.get(i).name.contains(name)) {
                return i;
            }
        }
        return -1;
    }

    public int get_nearest_location_index(float[] now_posi){
        float[] tempVec;
        float nearest_dist = Float.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < Node_num; i++) {
            tempVec = VectorCal.getVecByPoint(new float[]{now_posi[0], now_posi[1], 0}
                    , new float[]{(float) myNodes.get(i).data.x, (float) myNodes.get(i).data.y, 0});
            float tempDist = VectorCal.magnitude(tempVec);

            if(tempDist < nearest_dist){
                nearest_dist = tempDist;
                index = i;
            }
        }
        return index;
    }

    public void clearAllData() {
        myNodes.clear();
        Dist.clear();
        Dist2.clear();
        tempbuf.clear();
    }

    public boolean isLoaded() {
        return isLoaded;
    }
}
