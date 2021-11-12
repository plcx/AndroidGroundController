package com.example.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    WebView webView;

    void doLog(String log) {
        Log.e(TAG, log);
    }
    private RockerViewMid rockerView1;
    private RockerView rockerView2;
    private SeekBar SeekBar1;
    private SeekBar SeekBar2;
    private SeekBar SeekBar3;
    private Switch  switch1;
    private Button button1;
    private TextView   textView1;
    //int screenWidth;
    //int screenHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 设置全屏
        // ,
        // 屏幕长亮
        setContentView(R.layout.activity_main);




        webView = findViewById(R.id.x5webview);
        requestPermission();
        initWebView();
    /*
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
*/
        rockerView1 = (RockerViewMid) findViewById(R.id.rockerView1);
        rockerView2 = (RockerView) findViewById(R.id.rockerView2);
        SeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        SeekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        SeekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        switch1 = (Switch)findViewById(R.id.switch1);
        button1 = (Button)findViewById(R.id.button1);
        textView1=(TextView)findViewById(R.id.textView1);





        rockerView1.setRockerChangeListener(new RockerViewMid.RockerChangeListener() {

            @Override
            public void report(float x, float y) {
                // TODO Auto-generated method stub
                Client.setCh1((int)(1500-5*x));//右手x,1通道
                Client.setCh2((int)(1500-5*y));//右手y,2通道
                doLog(x + "/" + y);

            }
        });
        rockerView2.setRockerChangeListener(new RockerView.RockerChangeListener() {

            @Override
            public void report(float x, float y) {
                // TODO Auto-generated method stub
                Client.setCh3((int)(1500-5*y));//左手y,3通道
                Client.setCh4((int)(1500-5*x));//左手x,4通道
                doLog(x + "/" + y);


            }
        });

        SeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==0){
                    Client.setCh5(100);//左手x,5通道
                }
                else if(progress==1){
                    Client.setCh5(700);//左手x,5通道
                }
                else if(progress==2){
                    Client.setCh5(1000);//左手x,5通道
                }
                else{
                    Client.setCh5(1900);//左手x,5通道
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==0){
                    Client.setCh6(100);//左手x,5通道
                }
                else if(progress==1){
                    Client.setCh6(700);//左手x,5通道
                }
                else if(progress==2){
                    Client.setCh6(1000);//左手x,5通道
                }
                else{
                    Client.setCh6(1900);//左手x,5通道
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    Client.setCh7(progress*10+1040);//左手x,5通道


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Client.setCh0(1);
                }else {
                    Client.setCh0(0);
                }
            }
        });
        new Thread(new Client()).start();




        //显示线程
        /*
        new Thread() {
            @Override
            public void run() {

                while (true){
                    try {
                        Thread.sleep(1000);
                        textView1.setText(Client.getReceive());
                    }catch(InterruptedException ex)
                    {
                        ex.printStackTrace();
                    }


                }
            }
        }.start();

         */








    }







    private void initWebView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制横屏
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //true表示成功加载x5内核，false表示加载失败，使用内置的webview
                Log.i(TAG, "onViewInitFinished: " + b);
            }
        });

        webView.getSettings().setBlockNetworkImage(false);
        webView.getSettings().setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        // 获取网络设置
        final WebSettings webSettings = webView.getSettings();
        //解决了静止的问题
        webSettings.setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //这个一定要设置为false
        webSettings.setSupportMultipleWindows(false);//不能滑动
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("http://106.14.149.41:9000/javascript_simple.html");//网址




    }



    //踩坑，申请读写和网络
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] PERMISSON = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INTERNET
    };


    private void requestPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, PERMISSON, REQUEST_EXTERNAL_STORAGE);
        }
    }
}
