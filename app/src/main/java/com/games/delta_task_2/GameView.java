package com.games.delta_task_2;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class GameView extends View {
    private static final String TAG = "gameview";
    public static int ScoreAi;
    public static int Score;
    public ImageView imageView;
    private int Aipaddlewidth = 300;
    private int Playerpaddlewidth = 300;
    private int ballradius = 25;
    private int paddleheight = 50;
    private int cx ;
    private int cy;

    AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
    SoundPool soundPool = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(audioAttributes).build();
    int sound2 = soundPool.load(getContext(),R.raw.powerupsound,1);
    int  sound3 = soundPool.load(getContext(),R.raw.ping_pong_8bit_plop,1);
    int sound4 = soundPool.load(getContext(),R.raw.achievement,1);
    int sound5 = soundPool.load(getContext(),R.raw.lose,1);
    private Rect playerPaddle = new Rect( 0,getHeight()-paddleheight,Playerpaddlewidth,getHeight());
    private Rect AiPaddle = new Rect(0,0,Aipaddlewidth,paddleheight);
    private Paint paint = new Paint();
    private Paint painthollow = new Paint();
    private boolean movingplayer = false;
    private int player_x;
    private int Aix = 0;
    private int px;
    private int dx = 5;
    private int i=1;
    private boolean collision = false;
    private int dy = 5;
    private int py;

    public GameView(Context context) {
        super(context);
    }
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr);}


    @Override
    protected void onDraw(Canvas canvas) {
        if(i==1)
        {Randomxposition();
        Randomyposition();}
        update();
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        painthollow.setColor(Color.WHITE);
        painthollow.setAntiAlias(true);
        painthollow.setStyle(Paint.Style.STROKE);
        painthollow.setStrokeWidth(10);
        canvas.drawRect(playerPaddle,paint);
        canvas.drawRect(AiPaddle,paint);
        canvas.drawCircle(cx,cy,ballradius,paint);
        if(!collision )
        {
         canvas.drawCircle(px,py,ballradius,painthollow);
        }
        i++;
        invalidate();
    }

    private void update() {
        cx = cx + dx;
        cy = cy + dy;
        if (cx == px && cy == py) {
            soundPool.play(sound2, 1, 1, 0, 0, 1);
            Playerpaddlewidth = (int) (Playerpaddlewidth * 1.2);
            collision = true;
        }
        if (cx >= getWidth() - ballradius) {
            soundPool.play(sound3, 1, 1, 0, 0, 1);
            cx = getWidth() - ballradius;
            dx = dx * (-1);
        }
        if (cx <= ballradius) {
            soundPool.play(sound3, 1, 1, 0, 0, 1);
            cx = ballradius;
            dx = dx * (-1);
        }
        if (cy >= getHeight() - ballradius - paddleheight) {
            if (cx >= player_x - Playerpaddlewidth/2 && cx <= player_x + Playerpaddlewidth/2) {
                soundPool.play(sound3, 1, 1, 0, 0, 1);
                cy = getHeight() - ballradius - paddleheight;
                dy *= -1;
                dx = (int) (dx * 1.1);
                dy = (int) (dy * 1.1);
            } else {
                soundPool.play(sound5, 1, 1, 0, 0, 1);
                resetposition();
                ScoreAi++;
            }
        }
        if (cy <= paddleheight + ballradius) {
            if (cx <= Aix + Aipaddlewidth / 2 && cx >= Aix - Aipaddlewidth / 2) {
                soundPool.play(sound3, 1, 1, 0, 0, 1);
                cy = paddleheight + ballradius;
                dy *= -1;
                dx = (int) (dx * 1.5);
                dy = (int) (dy * 1.5);
            } else {
                Score++;
                resetposition();
                soundPool.play(sound4, 1, 1, 0, 0, 1);
            }
        }

        setAi(cx, dx, dy);
        setPlayer();
    }

    private  void Randomxposition() {
        Random r = new Random();
        cx = r.nextInt(getWidth() - 50) + 25;
        px = cx + 500;
        if(px>=getWidth() - ballradius||px<=ballradius)
           px = 100000;
        collision = false;
    }

    private  void Randomyposition() {
        Random r = new Random();
        cy = r.nextInt(getHeight() - getHeight()/2) + paddleheight;
        py = cy + 500;
        if(py>=getHeight() - paddleheight - ballradius || py<=paddleheight + ballradius)
          py =100000;
        collision = false;
    }
    public  void resetposition() {
        dx = 5;
        dy = 5;
        Randomxposition();
        Randomyposition();
        Playerpaddlewidth = 300;
    }

    private void setPlayer()
    {
        if(player_x<=Playerpaddlewidth/2)
            player_x =Playerpaddlewidth/2;
        if(player_x>=getWidth() - Playerpaddlewidth/2)
            player_x = getWidth() - Playerpaddlewidth/2;
        playerPaddle.set(player_x- Playerpaddlewidth/2,getHeight() - paddleheight,player_x + Playerpaddlewidth/2,getHeight());
    }
    private void setAi(int cx, int dx, int dy)
    {
        if(Menu.difficulty == 1) {
            Aix = cx;
            if (Aix <= 150)
                Aix = 150;
            if (Aix >= getWidth() - 150)
                Aix = getWidth() - 150;
            AiPaddle.set(Aix - Aipaddlewidth / 2, 0, Aix + Aipaddlewidth / 2, paddleheight);
        }
        if(Menu.difficulty ==0)
        {
            if( dx > 0)
                Aix = Aix + 5;
            if( dx < 0)
                Aix = Aix - 5;
            if (Aix <= 150)
                Aix = 150;
            if (Aix >= getWidth() - 150)
                Aix = getWidth() - 150;
            AiPaddle.set(Aix - Aipaddlewidth / 2, 0, Aix + Aipaddlewidth / 2, paddleheight);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if( playerPaddle.contains((int)event.getX(),(int)event.getY()))
                    movingplayer = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(movingplayer)
                    player_x = (int)(event.getX());
                break;
            case MotionEvent.ACTION_UP:
                movingplayer = false;
        }
        invalidate();
        return true;
    }

}
