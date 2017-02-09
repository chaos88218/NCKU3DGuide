package com.example.miles.ncku3dguide.Model;

import android.content.Context;

import com.example.miles.ncku3dguide.GPSTracker.GPSTracker;


/**
 * Created by miles on 2016/10/24.
 */

public class AllCampusData {
    //Models
    private Context loco_Context;
    public static Model loco;
    public static Model navi;
    public static Model stop;
    public static Model stop_sign;

    public static Campus cc;
    public static Campus ck;
    public static Campus sl;
    public static Campus kf;
    public static Campus jy;

    public static float[] map_zero = new float[]{989.285767f, 817.882629f};

    //GPS
    public static GPSTracker gpsTracker;


    //campus cc
    private float[] cc_map = new float[]{
            1685.020752f, 1502.139648f,
            1371.405640f, 1508.599609f,
            1354.412964f, 350.512787f,
            1665.924438f, 331.17697f,
    };

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
    private String[] cc_name = new String[]{
            "奇美樓", "化工系館",
            "門口地標", "系統系館",
            "兩個人雕像", "航太",
            "電機", "儀器大樓",
            "機械系館", "自強科技大樓",
            "自強校區大門", "自強圓形地標",
            "橄欖球架1", "橄欖球架2"
    };

    private float[] cc_location = new float[]{
            (1600.377075f + 1515.377441f) / 2.0f, (636.360596f + 631.251709f) / 2.0f,
            1519.189575f, 949.409790f,
            (1462.624512f + 1397.700684f) / 2.0f, (1030.356689f + 1030.614380f) / 2.0f,
            1430.713989f, 765.698914f,
            1513.539551f, 833.509949f,
            1413.427368f, 873.227173f,
            (1573.982422f + 1638.929321f) / 2.0f, (1067.892285f + 1064.965698f) / 2.0f,
            1457.405640f, 724.407837f,
            (1623.792358f + 1566.833496f) / 2.0f, (935.426331f + 936.034851f) / 2.0f,
            (1572.755371f + 1623.416382f) / 2.0f, (772.066956f + 819.998108f) / 2.0f,
            (1521.236084f + 1512.470825f) / 2.0f, (1035.904053f + 1026.375366f) / 2.0f,
            (1445.920166f + 1403.801025f) / 2.0f, (545.191345f + 482.413269f) / 2.0f
    };


    //campus ck
    private float[] ck_map = new float[]{
            1342.370728f, 1512.883667f,
            793.995972f, 1518.972778f,
            785.361206f, 741.346313f,
            1333.609131f, 723.839844f,
    };

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
    private String[] ck_name = new String[]{
            "土木系館", "工科系館",
            "工科新系館", "化學物理系館",
            "水利系館", "生命科學館",
            "地球科學系館", "麥當勞前水池",
            "大學路警衛室", "材料系館",
            "材料資訊資源新系館", "卓群大樓",
            "物理二館", "計算機中心與理化大樓",
            "格致堂", "能量光裕",
            "博物館", "博物館前圓環",
            "測量系館", "資訊系館",
            "資源系館", "綜合大樓",
            "數學系館", "環工系館",
            "總圖"
    };

    //campus sl
    private float[] sl_map = new float[]{
            1324.132813f, 723.746094f,
            789.158691f, 731.988647f,
            808.333435f, 99.998878f,
            1314.648804f, 78.596375f,
    };

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
    private String[] sl_name = new String[]{
            "勝8-1", "勝8-2",
            "勝一", "勝二",
            "勝三", "勝六",
            "勝四", "K館",
            "勝利門口方塊", "勝利門口方塊2",
            "勝利路門地標", "學生活動中心",
            "涼亭"
    };

    //campus kf
    private float[] kf_map = new float[]{
            759.799133f, 1513.838501f,
            156.654388f, 1552.364990f,
            52.589336f, 805.825623f,
            789.458691f, 731.988647f,
    };

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
    private String[] kf_name = new String[]{
            "一活", "一活旁雕像",
            "光三", "大成館",
            "小西門", "文學院",
            "文學院前雕像", "管院",
            "歷史系館", "歷史博物館",
            "禮賢樓", "光一",
            "光二", "中正堂",
            "兩個人雕像", "雲平大樓",
            "成功湖", "空中大學",
            "建築系館", "建築研究大樓",
            "耐震大樓", "軍訓室",
            "都計系館", "榕園",
            "維儂大樓", "操場"
    };


    private String jy_filepath = "JY_Y/";
    private String[] jy_files = new String[]{
            jy_filepath + "Y_A.STL", jy_filepath + "Y_B.STL",
            jy_filepath + "Y_C.STL", jy_filepath + "Y_D.STL",
            jy_filepath + "Y_E.STL", jy_filepath + "Y_F.STL",
            jy_filepath + "Y_G.STL", jy_filepath + "Y_H.STL",
            jy_filepath + "Y_I.STL", jy_filepath + "Y_J.STL"
    };
    private String[] jy_name = new String[]{
            "醫師宿舍1", "醫師宿舍2",
            "醫師宿舍3", "敬一",
            "敬二", "敬三",
            "東興里石碑", "海洋水文中心",
            "教職員單身宿舍", "網球場"
    };

    public AllCampusData(Context mContext) {

        loco_Context = mContext;

        loco = new Model("loco.STL", new float[]{1, 0, 0}, 1.0f, mContext);
        navi = new Model("loco.STL", new float[]{0, 0, 1}, 1.0f, mContext);
        stop = new Model("O_A.STL", new float[]{0, 0, 1}, 1.0f, mContext);
        stop_sign = new Model("O_A.STL", new float[]{0, 0, 1}, 1.0f, mContext);

        cc = new Campus(cc_filepath + "M.STL", 1.0f, mContext, cc_name.length);
        cc.set_Models(cc_name, cc_files, new float[]{189 / 255.0f, 252 / 255.0f, 201 / 255.0f});
        cc.set_corner(cc_map);
        cc.set_location(cc_location);

        ck = new Campus(ck_filepath + "S.STL", 1.0f, mContext, ck_name.length);
        ck.set_Models(ck_name, ck_files, new float[]{255 / 255.0f, 227 / 255.0f, 132 / 255.0f});
        ck.set_corner(ck_map);

        sl = new Campus(sl_filepath + "V.STL", 1.0f, mContext, sl_name.length);
        sl.set_Models(sl_name, sl_files, new float[]{255 / 255.0f, 192 / 255.0f, 203 / 255.0f});
        sl.set_corner(sl_map);

        kf = new Campus(kf_filepath + "L.STL", 1.0f, mContext, kf_name.length);
        kf.set_Models(kf_name, kf_files, new float[]{255 / 255.0f, 248 / 255.0f, 220 / 255.0f});
        kf.set_corner(kf_map);

        jy = new Campus(jy_filepath + "Y.STL", 1.0f, mContext, jy_name.length);
        jy.set_Models(jy_name, jy_files, new float[]{108 / 255.0f, 90 / 255.0f, 90 / 255.0f});

    }

    public void newGPSTracker() {
        gpsTracker = new GPSTracker(loco_Context);
    }

}
