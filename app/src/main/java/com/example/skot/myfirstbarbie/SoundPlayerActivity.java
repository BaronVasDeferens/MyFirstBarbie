package com.example.skot.myfirstbarbie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoundPlayerActivity extends AppCompatActivity {

    MediaWrangler mediaWrangler;
    List<String> allSoundNames;
    List<MediaPlayer> mediaPlayers;

    MediaPlayer shipSelectSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);



        // Capture intent and ship related info
        Intent intent = getIntent();
        String shipId = (String) intent.getExtras().get("ship_id");

        // Find layout
        LinearLayout attackGroup = (LinearLayout)findViewById(R.id.right_panel);
        LinearLayout moveGroup = (LinearLayout)findViewById(R.id.left_panel);
        LinearLayout centerPanel = (LinearLayout) findViewById(R.id.center_panel);
        ImageView centerPic = (ImageView)findViewById(R.id.ship_view);

        //attackGroup.removeAllViews();
        //moveGroup.removeAllViews();


        // Find matching ship picture in @drawable
        int id = getResources().getIdentifier(shipId, "drawable","com.example.skot.myfirstbarbie");         // this does the reverse lookup!!!
        Bitmap image = BitmapFactory.decodeResource(getResources(), id);
        if (image != null) {
            centerPic.setImageBitmap(Bitmap.createScaledBitmap(image, 330, 330, false));
        }

        // Round up all the sounds associated with the selected ship
        // Load sounds
        mediaWrangler = MediaWrangler.getMediaWranglerInstance();
        // Get ship select sound
        shipSelectSound = mediaWrangler.getSound("select");

        // Create sound library
        allSoundNames = mediaWrangler.getAllSoundNames();
        mediaPlayers = new ArrayList<>();

        for (String snd: allSoundNames) {

            if (snd.contains(shipId)) {

                Button b;

                b = new Button(this);
                b.setTextSize(15);


//                int btnId = R.layout.std_button_attack;
//
//                if (snd.contains("fire")) {
//                    btnId = R.layout.std_button_attack;
//                }
//
//                else if (snd.contains("flyby")) {
//                    btnId = R.layout.std_button_flyby;
//                }

//                b = (Button) getLayoutInflater().inflate(btnId, null);
                b.setText(snd.replace("_", " "));
                b.setTextColor(Color.WHITE);
                b.setTag(snd);

                b.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        Button btn = (Button) v;
                        MediaPlayer m = mediaWrangler.getSound((String) btn.getTag());

                        // Have each sound clean up after itself
                        m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                            }
                        });

                        mediaPlayers.add(m);

                        if (event.getAction() == MotionEvent.ACTION_DOWN)
                            if (!m.isPlaying())
                                m.start();
                            else {
                                m.seekTo(0);
                                m.start();
                            }

                        return true;
                    }
                });

                if (snd.contains("fire")) {
                    attackGroup.addView(b);
                }

                else if (snd.contains("flyby")) {
                    moveGroup.addView(b);
                }

            }
        }

    }

    /**
     * Play Sound From Family
     * Given a sound "family" (q.v "shield_out"), play a random sound from the its matching set (q.v "shield_out_3")
     * @param view
     */
    public void playSoundFromFamily (View view) {

        String familyName = view.getTag().toString();

        List<String> soundFamily = new ArrayList<>();

        for(String snd: allSoundNames) {
            if (snd.contains(familyName)) {
                soundFamily.add(snd);
            }
        }

        if (soundFamily.size() > 0) {
            Random rando = new Random();
            MediaPlayer m = MediaWrangler.getSound(soundFamily.get(rando.nextInt(soundFamily.size())));

            // Have each sound clean up after itself
            m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            m.start();
        }
    }

    public void backToSelect (View v) {
        shipSelectSound.start();
        this.finish();
    }

}
