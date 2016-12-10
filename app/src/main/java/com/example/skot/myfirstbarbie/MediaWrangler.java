package com.example.skot.myfirstbarbie;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Skot on 7/7/2016.
 */
public class MediaWrangler {

    private static HashMap<String, Integer> fieldMap = null;
    private static List<String> soundNames;
    private static MediaWrangler mediaWranglerInstance = null;
    private static boolean initialized = false;
    private static Context appContext;
    private static Field[] fields;

    private MediaWrangler() { }

    public static MediaPlayer BGMPlayer;


    // GET MEDIA WRANGLER INSTANCE
    public static synchronized MediaWrangler getMediaWranglerInstance() {
        if (fieldMap == null) {
            throw new RuntimeException(">>> MediaWrangler not initialized!");
        }

        return mediaWranglerInstance;
    }

    // INITIALIZE
    // Creates a HashMap and List of available sound resources
    public static synchronized void initialize(Context context) {

        if (mediaWranglerInstance != null) {
            throw new RuntimeException(">>> MediaWrangler is already initialized!");
        }

        appContext = context;

        soundNames = new ArrayList<>();
        fieldMap = new HashMap<>();

        fields = R.raw.class.getFields();

        for (int count = 0; count < fields.length; count++){

            int resourceID = 0;
            String fileName = "";

            try {
                fileName = fields[count].getName();
                resourceID = fields[count].getInt(fields[count]);
                fieldMap.put(fileName, resourceID);
                soundNames.add(fileName);
            }

            catch (Exception e) {
                Log.e(">>> PROBLEM LOADING " + fileName, e.toString());
            }
        }

        BGMPlayer = getSound(getSoundFamilyNames("soundtrack").get(0));
        BGMPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer = MediaWrangler.getNextRandomSoundtrack();
                mediaPlayer.start();
            }
        });

        initialized = true;
    }

    public static MediaPlayer getSound(String sound) {
        return MediaPlayer.create(appContext, fieldMap.get(sound));
    }


    public static List<String> getAllSoundNames() {
        return soundNames;
    }


    public static ArrayList<String> getSoundFamilyNames (String family) {

        ArrayList<String> returnList = new ArrayList<String>();

        for (String str: soundNames) {
            if (str.contains(family))
                returnList.add(str);
        }

        return returnList;
    }

    public static MediaPlayer getNextRandomSoundtrack() {

        Random rando = new Random();
        BGMPlayer = getSound(getSoundFamilyNames("soundtrack").get(rando.nextInt(getSoundFamilyNames("soundtrack").size())));
        return BGMPlayer;
    }

}
