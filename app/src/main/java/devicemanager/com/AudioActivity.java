package devicemanager.com;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AudioActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);


        ((Button) findViewById(R.id.buttonOff)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer!= null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    Log.d(MainActivity.TAG, " stop music");
                }
            }
        });

        ((Button) findViewById(R.id.buttonOn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(AudioActivity.this, R.raw.nhatkydoitoi);
                    mediaPlayer.start();
                    Log.d(MainActivity.TAG, "Playing music");
                }
            }
        });

    }
}
