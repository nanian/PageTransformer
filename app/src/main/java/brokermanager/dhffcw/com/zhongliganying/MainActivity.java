package brokermanager.dhffcw.com.zhongliganying;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorMgr;
    private float x, y, z;
    TextView text;
    Sensor sensor;
    private int isStart = 100;//判断是左播放还是右播放

    List<AdvertiseEntity> imgesUrl = new ArrayList<>();
    List<String> imgesText = new ArrayList<>();

    private XBanner bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        setContentView(R.layout.activity_main);
        ActivityManager.getInstance().addActivity(MainActivity.this);

        text = findViewById(R.id.text);
        bannerView = findViewById(R.id.xbanner);
        initBanner();
        initFanZhuan();

    }

    private void initBanner() {
        for (int i = 0; i < 4; i++) {
            AdvertiseEntity advertiseEntity = new AdvertiseEntity();
            if (i == 0) {
                advertiseEntity.setUrl("http://img3.fengniao.com/forum/attachpics/913/114/36502745.jpg");
                imgesText.add("1111111111");
            } else if (i == 1) {
                advertiseEntity.setUrl("http://imageprocess.yitos.net/images/public/20160910/99381473502384338.jpg");
                imgesText.add("222222222");
            } else if (i == 2) {
                advertiseEntity.setUrl("http://imageprocess.yitos.net/images/public/20160910/77991473496077677.jpg");
                imgesText.add("333333333333");
            } else if (i == 3) {
                advertiseEntity.setUrl("http://imageprocess.yitos.net/images/public/20160906/1291473163104906.jpg");
                imgesText.add("444444444444");
            }
            imgesUrl.add(advertiseEntity);
        }
        //添加广告数据
        bannerView.setData(imgesUrl, imgesText);//第二个参数为提示文字资源集合
        //切换动画
        bannerView.setCustomPageTransformer(new ScaleTransformer());
        //加载广告图片
        bannerView.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                AdvertiseEntity advertiseEntity = imgesUrl.get(position);
                //此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                Glide.with(MainActivity.this)
                        .load(advertiseEntity.getUrl()).placeholder(R.drawable.a)
                        .error(R.drawable.e)
                        .into((ImageView) view);
            }
        });
        bannerView.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                Toast.makeText(MainActivity.this, "点击了第" + position + "图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFanZhuan() {
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener lsn = new SensorEventListener() {
            public void onSensorChanged(SensorEvent e) {
                x = e.values[SensorManager.DATA_X];
                y = e.values[SensorManager.DATA_Y];
                z = e.values[SensorManager.DATA_Z];
                //                text.setText("x=" + (int) x + "," + "y=" + (int) y + "," + "z=" + (int) z);
                text.setText("x=" + (int) x);
                if (x > 1 && isStart != 200) {
                    isStart = 200;
                    playAnim();
                } else if (x < -1 && isStart != 100) {
                    isStart = 100;
                    playAnim();
                } else if (x >= -1 && x <= 1 && isStart != 0) {
                    isStart = 0;
                    bannerView.stopAutoPlay();
                    bannerView.setAutoPalyTime(2000);
                    bannerView.startAutoPlay();
                }
                Log.d("MainActivity", "x:" + x);
            }

            public void onAccuracyChanged(Sensor s, int accuracy) {
            }
        };
        //注册listener，第三个参数是检测的精确度
        sensorMgr.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private void playAnim() {
        Log.e("tag", "playAnim::" + isStart);
        bannerView.stopAutoPlay();
        bannerView.setIsOrder(isStart);
        bannerView.setAutoPalyTime(250);
        bannerView.startAutoPlay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bannerView.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bannerView.stopAutoPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerView.stopAutoPlay();
        ActivityManager.getInstance().finishAllActivity();
    }
}
