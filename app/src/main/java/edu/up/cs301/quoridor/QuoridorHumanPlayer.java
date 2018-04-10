package edu.up.cs301.quoridor;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import edu.up.cs301.tictactoe.TTTState;

/**
 * Created by lieu18 on 3/25/2018.
 */

public class QuoridorHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener {
    /**
     * constructor
     *
     * @param name the name of the player
     */

    // the current activity
    private Activity myActivity;

    // the surface view
    private QuoridorSurfaceView surfaceView;

    // the ID for the layout to use
    private int layoutId;

    //private QuoridorGameState quoridorGameState;

    public QuoridorHumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }


    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    public void receiveInfo(GameInfo info) {
        if (surfaceView == null) return;

        //TODO this method
        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            //surfaceView.flash(Color.RED, 50);
        }
        else if (!(info instanceof QuoridorGameState))
            // if we do not have a TTTState, ignore
            return;
        else {
            surfaceView.setState((QuoridorGameState)info);
            surfaceView.invalidate();
            Log.i("human player", "receiving");
        }

    }

    public int getPlayerNum() {return this.playerNum;}

    /**
     * sets the current player as the activity's GUI
     */
    public void setAsGui(GameMainActivity activity) {

        // remember our activitiy
        myActivity = activity;

        // Load the layout resource for the new configuration
        activity.setContentView(layoutId);

        // set the surfaceView instance variable
        surfaceView = (QuoridorSurfaceView) myActivity.findViewById(R.id.quoridorBoard);
        Log.i("set listener","OnTouch");
        surfaceView.setOnTouchListener(this);

        Button newGame = (Button) activity.findViewById(R.id.newGameButton);
        Button finalize = (Button) activity.findViewById(R.id.finalizeTurnButton);
        Button undo = (Button) activity.findViewById(R.id.undoButton);

        newGame.setOnClickListener(this);

    }

    /**
     * perform any initialization that needs to be done after the player
     * knows what their game-position and opponents' names are.
     */
    protected void initAfterReady() {
        myActivity.setTitle("Quoridor: "+allPlayerNames[0]+" vs. "+ allPlayerNames[1]);
    }


    public void onClick(View v) {
        // if we are not yet connected to a game, ignore
        if (game == null) return;

        if (!(v instanceof Button)) return;

        // Construct the action and send it to the game
        switch(v.getId()){
            case R.id.undoButton:
                game.sendAction(new QuoridorUndoTurn(this));
                break;
            case R.id.newGameButton:
                game.sendAction(new QuoridorNewGame(this));
                break;
            case R.id.finalizeTurnButton:
                game.sendAction(new QuoridorFinalizeTurn(this));
                break;
            default:
                return;
        }


    }

    public boolean onTouch(View v, MotionEvent event) {
        // ignore if not an "up" event
        //if (event.getAction() != MotionEvent.ACTION_UP) return true;
        // get the x and y coordinates of the touch-location;
        // convert them to square coordinates (where both
        // values are in the range 0..2)
        //int x = (int) event.getX();
        //int y = (int) event.getY();
        //Point p = surfaceView.mapPixelToSquare(x, y);

        // if the location did not map to a legal square, flash
        // the screen; otherwise, create and send an action to
        // the game
        /*
        if (p == null) {
            surfaceView.flash(Color.RED, 50);
        } else {
            TTTMoveAction action = new TTTMoveAction(this, p.y, p.x);
            Log.i("onTouch", "Human player sending TTTMA ...");
            game.sendAction(action);
            surfaceView.invalidate();
        }
        */

        // register that we have handled the event



        if (!(v instanceof QuoridorSurfaceView))
            return true;

        int x = (int) event.getX();
        int y = (int) event.getY();

        int curX = surfaceView.startingX;
        int curY = surfaceView.startingY;
        int margin = surfaceView.margin;
        int squareSize = surfaceView.squareSize;

        QuoridorLocalGame qlg = (QuoridorLocalGame) this.game;
        QuoridorGameState qgs = qlg.state;

        //postition of player 1
        int[] p1Pos = qgs.getPlayerPos(0);
        //postition of player 2
        int[] p2Pos = qgs.getPlayerPos(1);

        int[][] playerPos = new int[][]{p1Pos,p2Pos};

        int turn = qgs.getTurn();

        // LEFT TODO: Handle Jump Cases
        if (x > curX + playerPos[turn][0] * margin - margin &&
                x < curX + playerPos[turn][0] * margin + squareSize - margin &&
                y > curY + playerPos[turn][1] * margin &&
                y < curY + playerPos[turn][1] * margin + squareSize) {
            game.sendAction(new QuoridorMovePawn(this, Direction.LEFT, false));
        }
        // RIGHT
        else if (x > curX + playerPos[turn][0] * margin + margin &&
                x < curX + playerPos[turn][0] * margin + squareSize + margin &&
                y > curY + playerPos[turn][1] * margin &&
                y < curY + playerPos[turn][1] * margin + squareSize) {
            game.sendAction(new QuoridorMovePawn(this, Direction.RIGHT, false));
        }
        // UP
        else if (x > curX + playerPos[turn][0] * margin &&
                x < curX + playerPos[turn][0] * margin + squareSize &&
                y > curY + playerPos[turn][1] * margin - margin &&
                y < curY + playerPos[turn][1] * margin + squareSize - margin) {
            game.sendAction(new QuoridorMovePawn(this, Direction.UP, false));
        }
        // DOWN
        else if (x > curX + playerPos[turn][0] * margin &&
                x < curX + playerPos[turn][0] * margin + squareSize &&
                y > curY + playerPos[turn][1] * margin + margin &&
                y < curY + playerPos[turn][1] * margin + squareSize + margin) {
            game.sendAction(new QuoridorMovePawn(this, Direction.DOWN, false));
        }

        //check if every square is clickable
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //test to see if clicks happen TODO take out later
                if (x > curX && x < curX + squareSize &&
                        y > curY && y < curY + squareSize) {
                    surfaceView.movePawn(i,j);
                }
                curX += margin;
            }
            curX = surfaceView.startingX;
            curY += margin;
        }

        surfaceView.invalidate();

        return true;
    }
}
