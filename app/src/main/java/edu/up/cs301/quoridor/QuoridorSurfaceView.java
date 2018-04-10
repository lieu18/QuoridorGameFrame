package edu.up.cs301.quoridor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by shuler18 on 2/5/2018.
 */

public class QuoridorSurfaceView extends SurfaceView {


    private Paint brownPaint, redPaint, bluePaint, wallPaint; //create paint colors
    //private SurfaceView sv;

    protected int canvasHeight, canvasWidth, criticalSize, margin, squareSize, boardSize, startingX,
            startingY, wallWid, wallLen;

    protected QuoridorGameState state;


    public QuoridorSurfaceView(Context context) {
        super(context);
        init();
    }

    public QuoridorSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuoridorSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setState(QuoridorGameState state) {
        this.state = state;
    }

    public void init()
    {
        setWillNotDraw(false);

        //sets colors for layout of the game
        brownPaint = new Paint();
        brownPaint.setColor(0xFFDEB887);
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        wallPaint = new Paint();
        wallPaint.setColor(0xFF8B4513);

        //TODO add playable gui
        //sv = (SurfaceView)findViewById(R.id.board);
        //try {
        //    Thread.sleep(3000);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        //updateGlobalMeas(); //call method
    }

    /*
     * draws gameboard
     * at the moment, board is drawn like it is mid-game
     * will be changed to draw board during anytime
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        int curX, curY;

        float x0=0,y0=0,r0=0,x1=0,y1=0,r1=0;

        updateGlobalMeas(canvas); //call method

        if (state == null)
            return;

        curX = startingX;
        curY = startingY;

        //draws board
        for(int i = 0; i < 9; i++)
        {

            for(int j = 0; j < 9; j++)
            {
                //TODO isSelected shows where a click event happens
                boolean isSelected = false;
                canvas.drawRect(
                        curX,
                        curY,
                        curX+squareSize,
                        curY+squareSize,
                        isSelected /* TODO [j][i]*/ ? brownPaint : brownPaint);


                //draw player1
                if(j  == state.getPlayerPos(0)[0] &&
                        i == state.getPlayerPos(0)[1])
                {
                    x0 = curX+(squareSize*.5f);
                    y0 = curY+(squareSize*.5f);
                    r0 = squareSize*.45f;
                }

                //draw player2
                if(j  == state.getPlayerPos(1)[0] &&
                        i == state.getPlayerPos(1)[1])
                {
                    x1 = curX+(squareSize*.5f);
                    y1 = curY+(squareSize*.5f);
                    r1 = squareSize*.45f;
                }

                //draw horizontal wall
                if (!(i > 7 || j > 7)) {
                    if (state.getHorzWalls()[j][i]) {
                        canvas.drawRect(
                                curX,
                                curY - (wallWid + (margin - squareSize)) / 2,
                                curX + squareSize + margin,
                                curY - ((margin - squareSize) - (wallWid + (margin - squareSize)) / 2),
                                wallPaint);
                    }

                    //draw vertical wall
                    if (state.getVertWalls()[j][i] && (i < 8 || j < 8)) {
                        canvas.drawRect(
                                curX - (wallWid + (margin - squareSize)) / 2,
                                curY,
                                curX - ((margin - squareSize) - (wallWid + (margin - squareSize)) / 2),
                                curY + squareSize + margin,
                                wallPaint);
                    }
                }


                curX += margin;
            }
            curX = startingX;
            curY += margin;
        }
        //canvas.drawCircle(x,y,radius, redPaint);
        //canvas.drawRect(10,10,110,110, brownPaint);

        //draws circle using values set in for loop
        canvas.drawCircle(x0,y0,r0, redPaint);
        canvas.drawCircle(x1,y1,r1, bluePaint);

        //calls method to draw player's remaining walls
        drawP1RemainingWalls(canvas);
        drawP2RemainingWalls(canvas);

    }

    /*
     * method goes through global variables and sets them
     * called during initialization of board and when vars need updating
     */
    void updateGlobalMeas(Canvas canvas)
    {
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();
        criticalSize = (canvasHeight > canvasWidth) ? canvasWidth : canvasHeight;
        margin = criticalSize / 9 - 10;
        squareSize = margin * 2 / 3;

        boardSize = margin * 9 - (margin - squareSize);
        startingX = (canvasWidth - boardSize)/2;
        startingY = (canvasHeight - boardSize)/2;
        wallLen = margin + squareSize;
        wallWid = ( margin - squareSize ) / 2;
    }

    /*
     * draws player 1's remaining walls on the side of the board
     * called when player uses wall and needs to update their walls
     */
    void drawP1RemainingWalls(Canvas canvas)
    {
        int curX, curY;
        curX = startingX - (margin - squareSize);
        curY = startingY;

        for (int i=0; i<10; i++)
        {
            canvas.drawRect(curX-wallLen,
                    curY+2*i*wallWid,
                    curX,
                    curY+wallWid+2*i*wallWid,
                    wallPaint);
        }
    }

    /*
     * draws player 2's remaining walls on the side of the board
     * called when player uses wall and needs to update their walls
     */
    void drawP2RemainingWalls(Canvas canvas)
    {
        int curX, curY;
        curX = startingX + (margin*9) ;
        curY = startingY + (margin*8 + squareSize);

        for (int i=0; i<8; i++)
        {
            canvas.drawRect(curX,
                    curY,
                    curX + wallLen,
                    curY-wallWid,
                    wallPaint);
            curY -= 2 * wallWid;
        }
    }


}