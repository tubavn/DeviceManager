package devicemanager.com;

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

public class _3GActivity extends AppCompatActivity {

    private static boolean FLAG_REQUEST = true;
    private String url = "http://api.kingdark.org/me.json";
    private MyAsyncTask thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        thread = new MyAsyncTask();

        ((Button) findViewById(R.id.buttonOff)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("STATUS", "is 3g off");

            }
        });

        ((Button) findViewById(R.id.buttonIdle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!thread.isCancelled()){
                    //*
                    thread.cancel(true);
                }
                Log.d("STATUS", "is 3g idle/on");
            }
        });

        ((Button) findViewById(R.id.buttonTransmit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!thread.isCancelled()){
                    thread.execute(url);
                    Log.d(MainActivity.TAG, "start stranmit");
                }else {

                }
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String content;
        private String error = null;

        protected Void doInBackground(String... urls) {

            while (FLAG_REQUEST) {
                try {
                    // Server url call by GET method
                    HttpGet httpget = new HttpGet(urls[0]);
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    //* request to server
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
    }
}
