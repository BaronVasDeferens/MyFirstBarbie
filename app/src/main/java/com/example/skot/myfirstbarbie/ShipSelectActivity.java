package com.example.skot.myfirstbarbie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ShipSelectActivity extends AppCompatActivity {

    MediaPlayer buttonSelectedSound;
    MediaPlayer currentBGM;
    List<String> availableTrackNames;
    int currentBGMselction = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_select_activity);

        // Set up mediaPlayer for handling the soundtrack, button presses
        buttonSelectedSound = MediaWrangler.getSound("select");
        availableTrackNames = MediaWrangler.getSoundFamilyNames("soundtrack");
        currentBGMselction = 0;
        currentBGM = MediaWrangler.getSound(availableTrackNames.get(currentBGMselction));
        updateSongText(availableTrackNames.get(currentBGMselction));

        LinearLayout buttonArea = (LinearLayout) findViewById(R.id.ship_select_button_area);
        ImageButton b;
        Bitmap image;

        // TIE Fighter
        b = (ImageButton) getLayoutInflater().inflate(R.layout.ship_select_button, null);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.tie_fighter);
        b.setImageBitmap(Bitmap.createScaledBitmap(image, 200, 200, false));
        b.setTag(ShipID.tie_fighter);
        buttonArea.addView(b);

        // Millenium Falcon
        b = (ImageButton) getLayoutInflater().inflate(R.layout.ship_select_button, null);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.millenium_falcon);
        b.setImageBitmap(Bitmap.createScaledBitmap(image, 200, 200, false));
        b.setTag(ShipID.millenium_falcon);
        buttonArea.addView(b);

        // X-Wing
        b = (ImageButton) getLayoutInflater().inflate(R.layout.ship_select_button, null);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.x_wing);
        b.setImageBitmap(Bitmap.createScaledBitmap(image, 200, 200, false));
        b.setTag(ShipID.x_wing);
        buttonArea.addView(b);

        // Slave-1
        b = (ImageButton) getLayoutInflater().inflate(R.layout.ship_select_button, null);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.slave_one);
        b.setImageBitmap(Bitmap.createScaledBitmap(image, 200, 200, false));
        b.setTag(ShipID.slave_one);
        buttonArea.addView(b);
    }


    // Launch the PlaySoundActivity based on the chosen ship
    public void launchSoundPlayer(View view) {
        buttonSelectedSound.start();
        Intent i = new Intent(this, SoundPlayerActivity.class);
        i.putExtra("ship_id", view.getTag().toString());
        startActivity(i);
    }

    // PLAY/PAUSE: plays or pauses the current music track
    public void playPause(final View v) {

        if (currentBGM.isPlaying()) {
            currentBGM.pause();
        }
        else {
            currentBGM.start();
        }
    }

    // NEXT TRACK: advances to the next track
    public void nextTrack(View v) {

        if (currentBGM.isPlaying()) {
            currentBGM.pause();
        }

        currentBGM.release();

        currentBGMselction++;
        Log.i("nextTrack", "is " + currentBGMselction);

        if (currentBGMselction > availableTrackNames.size() - 1) {
            currentBGMselction = 0;
            Log.i("nextTrack", "is " + currentBGMselction);
        }

        currentBGM = MediaWrangler.getSound(availableTrackNames.get(currentBGMselction));
        updateSongText(availableTrackNames.get(currentBGMselction));
        currentBGM.start();
    }

    private void updateSongText(String text) {

        TextView nowPlayingLabel = (TextView) findViewById(R.id.now_playing_label);

        text = text.split("soundtrack_")[1];
        text = text.replace("_", " ");
        text = text.toUpperCase();
        nowPlayingLabel.setText(text);

    }


    @Override
    public void onBackPressed() {

    }

}
