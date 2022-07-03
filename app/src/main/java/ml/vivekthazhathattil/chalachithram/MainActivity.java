package ml.vivekthazhathattil.chalachithram;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import com.example.malayalamdialoguesquiz.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    public static Integer time_for_round = 10000;
    public static boolean is_sp_active = false;
    public static Integer final_score;
    public static boolean is_game_won = false;

    public class BackgroundPlayer {

        private Context ctx;
        private MediaPlayer mediaPlayer;

        public BackgroundPlayer(Context ctx, int res) {
            this.ctx = ctx;
            mediaPlayer = MediaPlayer.create(ctx, res);
            mediaPlayer.setLooping(true);
        }

        public void startBackgroundMusic() {
            if(mediaPlayer!=null&&!mediaPlayer.isPlaying())
                mediaPlayer.start();
        }

        public void stopBackgroundMusic() {
            if(mediaPlayer!=null && mediaPlayer.isPlaying())
                mediaPlayer.pause();
        }

        public void kill() {
            if(mediaPlayer!=null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                ctx = null;
            }
        }

    }
    public static BackgroundPlayer backgroundPlayer;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        is_sp_active = get_data_from_memory();
        is_game_won = get_game_won_data_from_memory();

            backgroundPlayer = new BackgroundPlayer(this, getResources().getIdentifier("jazz", "raw", getPackageName()));
    }

    public void play_sfx(int val){
        int resID;
        if(is_sound_on(1)) {
            if (val == 1) // for switch fragments
                resID = getResources().getIdentifier("woosh", "raw", getPackageName());
            else if (val == 2) // for wrong answer
                resID = getResources().getIdentifier("wrong", "raw", getPackageName());
            else if (val == 3) // for correct answer
                resID = getResources().getIdentifier("correct", "raw", getPackageName());
            else // any other cases
                resID = getResources().getIdentifier("woosh", "raw", getPackageName());
            System.out.println("resID = " + resID);
            MediaPlayer woosh = MediaPlayer.create(this, resID);
            woosh.start();
            woosh.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                };
            });
        }
    }

    private boolean get_game_won_data_from_memory(){
        System.out.println("invoked get_data_from_memory 2nd method");
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        boolean val = sharedPreferences.getBoolean("is_game_won", false);
        System.out.println("is_game_won = " + Boolean.toString(val));
        return val;
    }

    public boolean is_sound_on(int num){
        boolean val;
        System.out.println("invoked get_data_from_memory 3rd method");
        if (num == 0){// for music
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            val = sharedPreferences.getBoolean("music_on", true);
            System.out.println("is_music_on = " + Boolean.toString(val));
        }
        else{ // for sfx
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            val = sharedPreferences.getBoolean("sfx_on", true);
            System.out.println("is_music_on = " + Boolean.toString(val));
        }
        return val;
    }

    private boolean get_data_from_memory(){
        System.out.println("invoked get_data_from_memory method");
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        boolean val = sharedPreferences.getBoolean("is_santosh_pandit_round_unlocked", false);
        System.out.println("sp_active = " + Boolean.toString(val));
        return val;
    }

    @Override
    public void onBackPressed(){
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

/*
    public void store_data_in_local_memory(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("write", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("is_santosh_pandit_round_unlocked", true);

// Apply the edits!
        editor.apply();
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void store_music_preferences(int val, boolean pref){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        if (val == 1)
            editor.putBoolean("music_on", pref);
        else if (val == 2)
            editor.putBoolean("sfx_on", pref);

        editor.apply();
    }
}
