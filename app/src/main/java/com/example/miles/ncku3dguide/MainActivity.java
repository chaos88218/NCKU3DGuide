package com.example.miles.ncku3dguide;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miles.ncku3dguide.Calculator.VectorCal;
import com.example.miles.ncku3dguide.GL.MyGLSurfaceView;
import com.example.miles.ncku3dguide.Model.AllCampusData;

import java.util.ArrayList;
import java.util.Vector;


public class MainActivity extends AppCompatActivity {

    //OpenGL layouts
    private MyGLSurfaceView myGLSurfaceView;
    private RelativeLayout glViewLayout;

    //搜尋導航自標按鈕
    private LinearLayout search_layout;
    private EditText search_text;
    private Button search_button;

    //搜尋建議UI
    private LinearLayout ad_layout;
    private TextView first_ad;
    private TextView second_ad;
    private TextView third_ad;

    //圖形UI
    private ImageView classroom_image;
    private ImageView navi_image;
    private ImageView parking_image;
    private LinearLayout navi_done;

    //教室搜尋
    private SearchClassroom searchClassroom;
    private String qu;
    private String[] classroom_ans;

    //模型節點與控制
    private AllCampusData app;
    private boolean navi_mode;
    public static boolean stop_info = false;
    public static boolean navi = false;

    @Override
    protected void onResume() {
        super.onResume();

        app = new AllCampusData(MainActivity.this);
        app.newGPSTracker();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化
        myGLSurfaceView = new MyGLSurfaceView(MainActivity.this);
        searchClassroom = new SearchClassroom(MainActivity.this);
        setContentView(R.layout.activity_main);

        //UI
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //目前位置定位按鈕
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AllCampusData.gpsTracker.canGetLocation()) {

                    //刷新GPS位置
                    AllCampusData.gpsTracker.getLocation();

                    //重製目前位置指標
                    //myGLSurfaceView.setTranslationIdentity();
                    //myGLSurfaceView.setRotationIdendity();
                    float[] temp = VectorCal.getMapPosition(AllCampusData.gpsTracker.getLatitude(), AllCampusData.gpsTracker.getLongitude());
                    myGLSurfaceView.setUserPosition(temp);

                    //查詢最近節點位置
                    int index = AllCampusData.myMap.get_nearest_location_index(
                            new float[]{(temp[0] + AllCampusData.map_zero[0]), (temp[1] + AllCampusData.map_zero[1])});
                    classroom_ans = searchClassroom.getRoomInfo(AllCampusData.myMap.myNodes.get(index).name);

                    String ans = "";
                    if (!classroom_ans[0].matches("找不到該地點")) {
                        ans = "位於 " + classroom_ans[0] + "校區";
                    }
                    if (!classroom_ans[1].matches("")) {
                        ans += (", " + classroom_ans[1]);
                    }
                    if (!ans.matches("")) {
                        Toast.makeText(view.getContext(), ans, Toast.LENGTH_LONG).show();
                    }
                } else {
                    AllCampusData.gpsTracker.showSettingsAlert();
                }
            }
        });

        //搜尋建議
        ad_layout = (LinearLayout) findViewById(R.id.ad_layout);
        first_ad = (TextView) findViewById(R.id.first_ad);
        first_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first_ad.getText().length() > 0)
                    search_text.setText(first_ad.getText().toString());
            }
        });
        second_ad = (TextView) findViewById(R.id.second_ad);
        second_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (second_ad.getText().length() > 0)
                    search_text.setText(second_ad.getText().toString());
            }
        });
        third_ad = (TextView) findViewById(R.id.third_ad);
        third_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (third_ad.getText().length() > 0)
                    search_text.setText(third_ad.getText().toString());
            }
        });

        //教室搜尋
        search_layout = (LinearLayout) findViewById(R.id.search_layout);

        search_text = (EditText) findViewById(R.id.search_text);
        search_text.addTextChangedListener(filterTextWatcher);

        search_button = (Button) findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //資料庫搜尋
                qu = search_text.getText().toString();
                classroom_ans = searchClassroom.getRoomInfo(qu);

                //導航或搜尋
                if (navi_mode) {
                    {
                        if (AllCampusData.myMap.isLoaded()) {
                            int end_index = -1;
                            Log.d("WTF", classroom_ans[0]);

                            //利用教室查詢找到節點
                            if (!classroom_ans[0].matches("找不到該地點")) {
                                end_index = AllCampusData.myMap.get_location_index(classroom_ans[1]);
                            }
                            if (qu.matches("") || (end_index == -1)) {
                                Toast.makeText(v.getContext(), "無法定位", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(v.getContext(), "導航至 "
                                        + AllCampusData.myMap.myNodes.get(end_index).name, Toast.LENGTH_LONG).show();

                                float now_posi[] = VectorCal.getMapPosition(AllCampusData.gpsTracker.getLatitude()
                                        , AllCampusData.gpsTracker.getLongitude());
                                int start_index = AllCampusData.myMap.get_nearest_location_index(
                                        new float[]{(now_posi[0] + AllCampusData.map_zero[0]), (now_posi[1] + AllCampusData.map_zero[1])}
                                );
                                Vector<Integer> tempbuf = AllCampusData.myMap.setRoute(start_index, end_index);
                                myGLSurfaceView.setRoutObject(tempbuf);
                                navi = true;
                                navi_done.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(v.getContext(), "導航初始化未完成，稍後重試", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    //過濾教室資訊
                    String ans = classroom_ans[0];
                    if (!classroom_ans[1].matches("")) {
                        ans += ("\n" + classroom_ans[1]);
                    }
                    if (!classroom_ans[2].matches("")) {
                        ans += ("\n" + classroom_ans[2] + "樓");
                    }
                    if (!classroom_ans[3].matches("")) {
                        ans += ("\n" + classroom_ans[3]);
                    }

                    Toast.makeText(v.getContext(), ans, Toast.LENGTH_LONG).show();
                }

                //關掉軟鍵盤以及搜尋介面
                InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search_text.getWindowToken(), 0);
                search_layout.setVisibility(View.GONE);
                ad_layout.setVisibility(View.GONE);
            }
        });

        //image button
        classroom_image = (ImageView) findViewById(R.id.classroom_image);
        classroom_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                navi_mode = false;
                search_button.setText("搜尋");
                UIopen();
            }
        });

        navi_image = (ImageView) findViewById(R.id.navi_image);
        navi_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navi_mode = true;
                search_button.setText("導航");
                UIopen();
            }
        });

        parking_image = (ImageView) findViewById(R.id.parking_image);
        parking_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop_info = !stop_info;
            }
        });

        navi_done = (LinearLayout) findViewById(R.id.navi_done);
        navi_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navi = false;
                navi_done.setVisibility(View.GONE);
            }
        });

        //gl layout
        glViewLayout = (RelativeLayout) findViewById(R.id.GL_view);
        glViewLayout.addView(myGLSurfaceView);


        final View search_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.app_update_layout, null);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("更新資訊:")
                .setView(search_view)
                .setPositiveButton("確認", null)
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        app.clearAllData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final View search_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.action_info_dlg, null);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("操作方法:")
                    .setView(search_view)
                    .setPositiveButton("確認", null)
                    .show();
            return true;
        }
        if (id == R.id.bug_report) {
            final View search_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.bug_report_dlg, null);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("回報錯誤:")
                    .setView(search_view)
                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) search_view.findViewById(R.id.report_text);
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("plain/text");
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ck.mappers5@gmail.com"});
                            intent.putExtra(Intent.EXTRA_SUBJECT, "NCKU 3D Guide回饋");
                            intent.putExtra(Intent.EXTRA_TEXT, editText.getText());
                            startActivity(Intent.createChooser(intent, "選擇電子郵件"));
                        }
                    })
                    .show();
            return true;
        }
        if (id == R.id.info_all_name) {
            final View search_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.app_infromation, null);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("軟體資訊:")
                    .setView(search_view)
                    .setPositiveButton("確認", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //text change搜尋系統建議 輸入偵測
    private TextWatcher filterTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            first_ad.setText("");
            second_ad.setText("");
            third_ad.setText("");
        }

        @Override
        public void afterTextChanged(Editable s) {
            ArrayList<String> answer = searchClassroom.getNameList(s + "");
            if (answer.size() > 0) {
                first_ad.setText(answer.get(0));
            }
            if (answer.size() > 1) {
                second_ad.setText(answer.get(1));
            }
            if (answer.size() > 2) {
                third_ad.setText(answer.get(2));
            }
        }
    };

    private void UIopen() {
        search_layout.setVisibility(View.VISIBLE);
        ad_layout.setVisibility(View.VISIBLE);

        search_text.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(search_text, InputMethodManager.SHOW_IMPLICIT);
    }
}
