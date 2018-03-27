package edu.up.cs301.quoridor;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import edu.up.cs301.tictactoe.TTTState;
import edu.up.cs301.tictactoe.TTTSurfaceView;

/**
 * Created by lieu18 on 3/25/2018.
 */

public class QuoridorHumanPlayer extends GameHumanPlayer {
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
        else if (!(info instanceof TTTState))
            // if we do not have a TTTState, ignore
            return;
        else {
            //surfaceView.setState((TTTState)info);
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
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }
}
