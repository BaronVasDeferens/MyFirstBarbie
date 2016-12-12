package com.example.skot.myfirstbarbie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;
import java.util.Random;

public class ShipSelectActivity extends AppCompatActivity {

    MediaPlayer buttonSelectedSound;
    MediaPlayer currentBGM;
    List<String> availableTrackNames;
    static Random rando;
    int currentBGMselction = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_select_activity);

        // Set up mediaPlayer for handling the soundtrack, button press sounds
        buttonSelectedSound = MediaWrangler.getSound("select");
        availableTrackNames = MediaWrangler.getSoundFamilyNames("soundtrack");
        rando = new Random();
        currentBGMselction = rando.nextInt(availableTrackNames.size());
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        } else {
            currentBGM.start();
        }
    }

    // NEXT TRACK: advances to the next track
    public void nextTrack(View v) {

        if (currentBGM.isPlaying()) {
            currentBGM.pause();
        }

        currentBGM.release();
//
//        currentBGMselction++;
//        Log.i("nextTrack", "is " + currentBGMselction);
//
//        if (currentBGMselction > availableTrackNames.size() - 1) {
//            currentBGMselction = 0;
//            Log.i("nextTrack", "is " + currentBGMselction);
//        }
//

        if (availableTrackNames.isEmpty())
            availableTrackNames = MediaWrangler.getSoundFamilyNames("soundtrack");


        currentBGMselction = rando.nextInt(availableTrackNames.size());
        String currentBGMTrackName = availableTrackNames.get(currentBGMselction);
        currentBGM = MediaWrangler.getSound(currentBGMTrackName);
        availableTrackNames.remove(currentBGMselction);
        updateSongText(currentBGMTrackName);
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ShipSelect Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
