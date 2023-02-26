package devicemanager.com;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.preference.Preference;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LCDActivity extends AppCompatActivity {
    DevicePolicyManager devicePolicyManager;
    ComponentName demoDeviceAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_lock);

        // get admin device
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        demoDeviceAdmin = new ComponentName(this, DeviceAdminSample.class);

        ((Button) findViewById(R.id.buttonOff)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start a theard background for lock on screen after 3 sencond
                Intent intent = new Intent(LCDActivity.this, MyService.class);
                startService(intent);
                // lock off screen
                lockOff();
            }
        });
    }

    private void lockOff() {
        boolean active = devicePolicyManager.isAdminActive(demoDeviceAdmin);
        if (active) {
            devicePolicyManager.lockNow();
            Log.d(MainActivity.TAG, " Lock off");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MainActivity.ADMIN_INTENT:
                if (resultCode == Activity.RESULT_OK) {
                    Log.i("DeviceAdminSample", "Administration enabled!");
                } else {
                    Log.i("DeviceAdminSample", "Administration enable FAILED!");
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
