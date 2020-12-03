package com.example.maplocationdemo.tencent;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.maplocationdemo.LocationApplication;
import com.example.maplocationdemo.R;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;

import java.lang.ref.WeakReference;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class TencentDemoSingleActivity extends Activity {

    private static final String TAG = TencentDemoSingleActivity.class.getSimpleName();

    private TencentLocationManager mLocationManager;
    private InnerLocationListener mLocationListener;

    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tencent);
        tv_content = findViewById(R.id.tv_content);

        LocationApplication application = (LocationApplication) getApplication();
        mLocationManager = application.getLocationManager();
        mLocationListener = new InnerLocationListener(new WeakReference<TencentDemoSingleActivity>(this));

        if (Build.VERSION.SDK_INT >= 23) {
            requirePermission();
        }

        if (!judgeLocationServerState()) {
            //没有打开位置服务开关，这里设计交互逻辑引导用户打开位置服务开关
        }
    }

    @AfterPermissionGranted(1)
    private void requirePermission() {
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        String[] permissionsForQ = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION, //target为Q时，动态请求后台定位权限
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (Build.VERSION.SDK_INT >= 29 ? EasyPermissions.hasPermissions(this, permissionsForQ) :
                EasyPermissions.hasPermissions(this, permissions)) {
            Toast.makeText(this, "权限OK", Toast.LENGTH_LONG).show();
        } else {
            EasyPermissions.requestPermissions(this, "需要权限",
                    1, Build.VERSION.SDK_INT >= 29 ? permissionsForQ : permissions);
        }
    }

    private boolean judgeLocationServerState() {
        try {
            return Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.LOCATION_MODE) > 1;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void startSingleLocation(View view) {
        if (mLocationManager != null) {
            //也可以使用子线程，但是必须包含Looper
            int re = mLocationManager.requestSingleFreshLocation(null, mLocationListener, Looper.getMainLooper());
            Log.i(TAG, "re: " + re);
        }
    }

    public void clearAll(View view) {
        tv_content.setText("");
    }

    private static class InnerLocationListener implements TencentLocationListener {
        private WeakReference<TencentDemoSingleActivity> mMainActivityWRF;

        public InnerLocationListener(WeakReference<TencentDemoSingleActivity> mainActivityWRF) {
            mMainActivityWRF = mainActivityWRF;
        }

        @Override
        public void onLocationChanged(TencentLocation location, int error,
                                      String reason) {
            if (mMainActivityWRF != null) {
                TencentDemoSingleActivity mainActivity = mMainActivityWRF.get();
                if (mainActivity != null) {
                    mainActivity.tv_content.append(location.toString());
                    mainActivity.tv_content.append("\r\n");
                }
            }
        }

        @Override
        public void onStatusUpdate(String name, int status, String desc) {
            Log.i(TAG, "name: " + name + "status: " + status + "desc: " + desc);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
