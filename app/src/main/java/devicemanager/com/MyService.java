package devicemanager.com;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;

/**
 * Created by khoaninh on 19/07/2016.
 */

public class MyService extends Service {
    DevicePolicyManager devicePolicyManager;
    ComponentName demoDeviceAdmin;

    @Override
    public void onCreate() {
        super.onCreate();
        // get admin device
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        demoDeviceAdmin = new ComponentName(this, DeviceAdminSample.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // create thread background, it start run() function after 3 second
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lockOn();
                Log.d(MainActivity.TAG, " lock on");
            }
        }, 3000);
        return super.onStartCommand(intent, flags, startId);
    }

    private void lockOn() {

        // setup parama to unlock screen
        PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "My Lock on");
        wakeLock.acquire();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
