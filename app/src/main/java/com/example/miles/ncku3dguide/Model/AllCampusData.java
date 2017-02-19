package com.example.miles.ncku3dguide.Model;

import android.content.Context;

import com.example.miles.ncku3dguide.GPSTracker.GPSTracker;
import com.example.miles.ncku3dguide.MapNavi.MyMap;


//綁定所有STL的Global物件

public class AllCampusData {
    //Models
    private Context loco_Context;
    public static Model loco;
    public static Model navi;
    public static Model stop_sign;

    public static Campus cc;
    public static Campus ck;
    public static Campus sl;
    public static Campus kf;
    public static Campus jy;
    public static Campus ls;
    public static Campus cs;
    public static MyMap myMap;

    public static float[] map_zero = new float[]{894.598267f, 810.448181f};

    //GPS
    public static GPSTracker gpsTracker;


    //campus cc
    private String cc_filepath = "CC_M/";
    private String[] cc_files = new String[]{
            cc_filepath + "M_B.STL", cc_filepath + "M_C.STL",
            cc_filepath + "M_D.STL", cc_filepath + "M_E.STL",
            cc_filepath + "M_F.STL", cc_filepath + "M_G.STL",
            cc_filepath + "M_H.STL", cc_filepath + "M_I.STL",
            cc_filepath + "M_J.STL", cc_filepath + "M_K.STL",
            cc_filepath + "M_L.STL", cc_filepath + "M_M.STL",
            cc_filepath + "M_N.STL", cc_filepath + "M_O.STL"
    };

    //campus ck
    private String ck_filepath = "CK_S/";
    private String[] ck_files = new String[]{
            ck_filepath + "S_A.STL", ck_filepath + "S_B.STL",
            ck_filepath + "S_C.STL", ck_filepath + "S_D.STL",
            ck_filepath + "S_E.STL", ck_filepath + "S_F.STL",
            ck_filepath + "S_G.STL", ck_filepath + "S_H.STL",
            ck_filepath + "S_I.STL", ck_filepath + "S_J.STL",
            ck_filepath + "S_K.STL", ck_filepath + "S_L.STL",
            ck_filepath + "S_M.STL", ck_filepath + "S_N.STL",
            ck_filepath + "S_O.STL", ck_filepath + "S_P.STL",
            ck_filepath + "S_Q.STL", ck_filepath + "S_R.STL",
            ck_filepath + "S_S.STL", ck_filepath + "S_T.STL",
            ck_filepath + "S_U.STL", ck_filepath + "S_W.STL",
            ck_filepath + "S_X.STL", ck_filepath + "S_Y.STL",
            ck_filepath + "S_Z.STL"

    };

    //campus sl
    private String sl_filepath = "SL_V/";
    private String[] sl_files = new String[]{
            sl_filepath + "V_A.STL", sl_filepath + "V_B.STL",
            sl_filepath + "V_C.STL", sl_filepath + "V_D.STL",
            sl_filepath + "V_E.STL", sl_filepath + "V_F.STL",
            sl_filepath + "V_G.STL", sl_filepath + "V_H.STL",
            sl_filepath + "V_I.STL", sl_filepath + "V_J.STL",
            sl_filepath + "V_K.STL", sl_filepath + "V_L.STL",
            sl_filepath + "V_M.STL"
    };

    //campus kf
    private String kf_filepath = "KF_L/";
    private String[] kf_files = new String[]{
            kf_filepath + "L_A.STL", kf_filepath + "L_B.STL",
            kf_filepath + "L_C.STL", kf_filepath + "L_D.STL",
            kf_filepath + "L_E.STL", kf_filepath + "L_F.STL",
            kf_filepath + "L_G.STL", kf_filepath + "L_H.STL",
            kf_filepath + "L_I.STL", kf_filepath + "L_J.STL",
            kf_filepath + "L_K.STL", kf_filepath + "L_L.STL",
            kf_filepath + "L_M.STL", kf_filepath + "L_N.STL",
            kf_filepath + "L_O.STL", kf_filepath + "L_P.STL",
            kf_filepath + "L_Q.STL", kf_filepath + "L_R.STL",
            kf_filepath + "L_S.STL", kf_filepath + "L_T.STL",
            kf_filepath + "L_U.STL", kf_filepath + "L_W.STL",
            kf_filepath + "L_X.STL", kf_filepath + "L_Y.STL",
            kf_filepath + "L_Z.STL", kf_filepath + "L_1.STL"
    };

    //campus ls
    private String ls_filepath = "LS_C/";
    private String[] ls_files = new String[]{
            ls_filepath + "C_A.STL",
            ls_filepath + "C_B.STL",
            ls_filepath + "C_C.STL",
            ls_filepath + "C_D.STL",
            ls_filepath + "C_E.STL"
    };

    //campus jy
    private String jy_filepath = "JY_Y/";
    private String[] jy_files = new String[]{
            jy_filepath + "Y_A.STL", jy_filepath + "Y_B.STL",
            jy_filepath + "Y_C.STL", jy_filepath + "Y_D.STL",
            jy_filepath + "Y_E.STL", jy_filepath + "Y_F.STL",
            jy_filepath + "Y_G.STL", jy_filepath + "Y_H.STL",
            jy_filepath + "Y_I.STL", jy_filepath + "Y_J.STL"
    };

    //campus cs
    private String cs_filepath = "CS_J/";
    private String[] cs_files = new String[]{
            cs_filepath + "J_A.STL",
            cs_filepath + "J_B.STL",
            cs_filepath + "J_C.STL",
            cs_filepath + "J_D.STL",
            cs_filepath + "J_E.STL",
            cs_filepath + "J_F.STL"
    };

    public AllCampusData(Context mContext) {
        loco_Context = mContext;

        loco = new Model("loco.STL", new float[]{1, 0, 0}, 1.0f, loco_Context);
        navi = new Model("loco.STL", new float[]{0, 0, 1}, 1.0f, loco_Context);

        stop_sign = new Model("moto.STL", new float[]{0, 0, 1}, 1.0f, loco_Context);

        cc = new Campus(cc_filepath + "M.STL", 1.0f, loco_Context, cc_files.length);
        cc.set_Models(cc_files, new float[]{189 / 255.0f, 252 / 255.0f, 201 / 255.0f});

        ck = new Campus(ck_filepath + "S.STL", 1.0f, loco_Context, ck_files.length);
        ck.set_Models( ck_files, new float[]{255 / 255.0f, 227 / 255.0f, 132 / 255.0f});

        sl = new Campus(sl_filepath + "V.STL", 1.0f, loco_Context, sl_files.length);
        sl.set_Models( sl_files, new float[]{255 / 255.0f, 192 / 255.0f, 203 / 255.0f});

        kf = new Campus(kf_filepath + "L.STL", 1.0f, loco_Context, kf_files.length);
        kf.set_Models( kf_files, new float[]{255 / 255.0f, 248 / 255.0f, 220 / 255.0f});

        jy = new Campus(jy_filepath + "Y.STL", 1.0f, loco_Context, jy_files.length);
        jy.set_Models( jy_files, new float[]{128 / 255.0f, 110 / 255.0f, 110 / 255.0f});

        ls = new Campus(ls_filepath + "C.STL", 1.0f, loco_Context, ls_files.length);
        ls.set_Models( ls_files, new float[]{255 / 255.0f, 150 / 255.0f, 150 / 255.0f});

        cs = new Campus(cs_filepath + "J.STL", 1.0f, loco_Context, cs_files.length);
        cs.set_Models( cs_files, new float[]{222 / 255.0f, 222 / 255.0f, 222 / 255.0f});
        map_Navi.start();
    }

    public void newGPSTracker() {
        gpsTracker = new GPSTracker(loco_Context);
    }

    private Thread map_Navi = new Thread(new Runnable() {
        @Override
        public void run() {
            myMap = new MyMap();
            myMap.LoadMap("campus1.txt", loco_Context);
        }
    });

    //清理暫存資料
    public void clearAllData() {
        cc.clearAllData();
        ck.clearAllData();
        sl.clearAllData();
        kf.clearAllData();
        jy.clearAllData();
        ls.clearAllData();
        cs.clearAllData();
        myMap.clearAllData();
    }

}
