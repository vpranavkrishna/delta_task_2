package com.games.delta_task_2;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import androidx.annotation.RequiresApi;

public class Game extends Activity {
        private TextView score;
        public static int ViewHeight;
        public static int ViewWidth;
        private TextView gametime;
        private GameView gameView;
        public  boolean onbackpressed;
        private long time;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);
             gametime =  findViewById(R.id.gametime);
             score =  findViewById(R.id.score);
             gameView = findViewById(R.id.gameview);
             ViewWidth =gameView.getWidth();
             ViewHeight = gameView.getHeight();
             Intent intent = getIntent();
             time = intent.getIntExtra("time",10);
            new CountDownTimer(time*1000, 1000) {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onTick(long millisUntilFinished) {
                    gametime.setText("Time= " + (int) (millisUntilFinished / 1000));
                    score.setText("YOU= " + GameView.Score +"|"+ "AI= " + GameView.ScoreAi);
                    if(onbackpressed)
                        onFinish();
                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onFinish() {
                    if(!onbackpressed) {
                        Intent intent = new Intent(Game.this, PostGameActivity.class);
                        intent.putExtra("score", GameView.Score);
                        intent.putExtra("scoreAi", GameView.ScoreAi);
                        startActivity(intent);
                        finish();
                    }
                }
            }.start();
        }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder Builder = new AlertDialog.Builder(this);
        Builder.setTitle("Exit");
        Builder.setMessage("Are you sure u want to exit?");
        Builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onbackpressed = true;
                finish();
            }
        });
        Builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onbackpressed = false;
                dialog.cancel();
            }
        });
        Builder.create().show();
    }
}

