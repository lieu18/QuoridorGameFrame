package edu.up.cs301.quoridor;

import android.view.View;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by lieu18 on 3/25/2018.
 */

public class QuoridorHumanPlayer extends GameHumanPlayer {
    /**
     * constructor
     *
     * @param name the name of the player
     */
    public QuoridorHumanPlayer(String name) {
        super(name);
    }

    public void setAsGui(GameMainActivity activity) {

    }

    public View getTopView() {
        return null;
    }

    public void receiveInfo(GameInfo info) {

    }
}
