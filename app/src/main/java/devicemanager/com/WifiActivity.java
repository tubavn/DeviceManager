package devicemanager.com;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.HttpGet;

import java.io.IOException;

import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class WifiActivity extends AppCompatActivity {

    private static boolean FLAG_REQUEST = true;
    private String url = "http://api.kingdark.org/me.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        // button off
        ((Button) findViewById(R.id.buttonOff)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                if (wifi.isWifiEnabled()) {
                   // wifi.setWifiEnabled(false);
                    Log.d("STATUS", "is WIFI disable");
                } else {
                    Log.d("STATUS", "wifi is off");
                }
            }
        });

        // button idle
        ((Button) findViewById(R.id.buttonIdle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                if (wifi.isWifiEnabled()) {
                    if (FLAG_REQUEST){
                        FLAG_REQUEST = false;
                    }
                } else {
                    wifi.setWifiEnabled(true);
                    FLAG_REQUEST = true;
                }
                Log.d("STATUS", "is WIFI enable");

            }
        });

        // button stranmit
        ((Button) findViewById(R.id.buttonTransmit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(MainActivity.TAG, "start stranmit");
                new LongOperation().execute(url);
            }
        });
    }

    private class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String content;
        private String error = null;

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            while(FLAG_REQUEST) {
                try {
                    // Server url call by GET method
                    HttpGet httpget = new HttpGet(urls[0]);
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    // request to server
                    content = Client.execute(httpget, responseHandler);

                    Log.d(MainActivity.TAG, "DATA: " + content.getBytes().length + " byte");
                } catch (ClientProtocolException e) {
                    error = e.getMessage();
                    cancel(true);
                } catch (IOException e) {
                    error = e.getMessage();
                    cancel(true);
                }

            }
            return null;
        }

        protected void onPostExecute(Void unused) {
        }

    }
}
