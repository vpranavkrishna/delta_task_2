package com.games.delta_task_2;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

public class PostGameActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postgameactivity);
        TextView gameover = findViewById(R.id.end);
        TextView result = findViewById(R.id.result);
        Button playagain = findViewById(R.id.playagain);
        gameover.setText("gameover");
        int score = getIntent().getIntExtra("score",0);
        int scoreAi = getIntent().getIntExtra("scoreAi",0);
        if(score>scoreAi)
        result.setText("you  won");
        else if(scoreAi>score)
            result.setText("you  lost");
        else
            result.setText("draw");
        playagain.setOnClickListener(this);

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        GameView.ScoreAi = 0;
        GameView.Score = 0;
        startActivity(new Intent(this,Menu.class));
        finish();
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder Builder = new AlertDialog.Builder(this);
        Builder.setTitle("Exit");
        Builder.setMessage("Are you sure u want to exit?");
        Builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
