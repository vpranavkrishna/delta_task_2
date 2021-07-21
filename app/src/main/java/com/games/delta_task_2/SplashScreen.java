package com.games.delta_task_2;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    private boolean onbackpressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
         setContentView(R.layout.splash);
        ImageView img = findViewById(R.id.imageView2);
        Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.animation);
        img.startAnimation(slideAnimation);
        Intent intent = new Intent(this,Menu.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!onbackpressed)
                {
                    startActivity(intent);
                    finish();
                }
            }
        },3000);
    }
    @Override
    public void onBackPressed() {
        onbackpressed = true;
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