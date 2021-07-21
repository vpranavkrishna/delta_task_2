package com.games.delta_task_2;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;

public class Menu extends Activity implements View.OnClickListener {
private Button easy;
private Button hard;
public static int difficulty;
private EditText time;
private Intent intent;
private MediaPlayer mediaPlayer ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mediaPlayer = MediaPlayer.create(this,R.raw.menumusic2);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        easy = findViewById(R.id.easy);
        hard = findViewById(R.id.hard);
        time = findViewById(R.id.time);
        easy.setOnClickListener(this);
        hard.setOnClickListener(this);
        GameView.Score =0;
        GameView.ScoreAi =0;
    }
    private boolean isempty()
    {
        if(time.getText().toString().trim().length() <= 0 )
            return true ;
        else
            return false;
    }
    public void onClick(View v) {
        if(!isempty())
        {
            if (Integer.parseInt(time.getText().toString()) >= 10) {
                if (v.getId() == easy.getId())
                    difficulty = 0;
                if (v.getId() == hard.getId())
                    difficulty = 1;
                intent = new Intent(Menu.this, Game.class);
                intent.putExtra("time", Integer.parseInt(time.getText().toString()));
                startActivity(intent);
                mediaPlayer.stop();
                mediaPlayer.release();
                finish();
            }
            else
                Toast.makeText(this, "Enter a valid integer greater than 10", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Enter a valid integer greater than 10", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder Builder = new AlertDialog.Builder(this);
        Builder.setTitle("Exit");
        Builder.setMessage("Are you sure u want to exit?");
        Builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.stop();
                mediaPlayer.release();
                finish();
            }
        });
        Builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        Builder.create().show();
    }
}