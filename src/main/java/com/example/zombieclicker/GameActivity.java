package com.example.zombieclicker;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.zombieclicker.databinding.ActivityGameBinding;
import com.google.android.material.navigation.NavigationView;

public class GameActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityGameBinding binding;
    private String name;
    private MediaPlayer playerMusic;
    public int[] sounds = {R.raw.music1, R.raw.music2, R.raw.music3, R.raw.music4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_slideshow, R.id.nav_gallery, R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        preferences = getSharedPreferences("ALL_INFO", MODE_PRIVATE);
        name = preferences.getString("nickname", "");


        Thread musicThread = new Thread() {
            public void run() {
                int index = 0;
                playerMusic = MediaPlayer.create(GameActivity.this, sounds[index]);
                playerMusic.start();
                playerMusic.setLooping(false);
                while (true) {
                    if(!playerMusic.isPlaying()){
                        index++;
                        playerMusic = MediaPlayer.create(GameActivity.this, sounds[index]);
                        playerMusic.start();
                        if(index >= sounds.length-1)
                            index = 0;
                    }
                }
            }
        };

        musicThread.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        TextView nameText = findViewById(R.id.nameTextView);
        nameText.setText(name);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void Save(String category, int integer) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(category, integer);

        editor.apply();
    }
}