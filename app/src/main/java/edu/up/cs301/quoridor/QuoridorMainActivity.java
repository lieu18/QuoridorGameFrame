package edu.up.cs301.quoridor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.R;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;
import edu.up.cs301.quoridor.QuoridorComputerPlayer;
import edu.up.cs301.quoridor.QuoridorHumanPlayer;

/**
 * Created by lieu18 on 3/25/2018.
 *
 * @author Anthony Lieu
 * @author Phillip Manalili
 * @author Noah Davis
 * @author Dylan Shuler
 *
 *
 * Current state of the game does not include the complex path finder algorithm
 *      Algorithm not behaving the way we expected. More complex than noted.
 *      Ran into strange bugs with pawn movement that was not caught before.
 *
 * Network play is mysteriously not working. Prompts a Could not find game server on network
 *      Should have gone in early and addressed this issue after alpha bugs were fixed.
 *
 * Game contains all the graphics in its final form
 * Smart and Dumb Computer AI components are implemented and runs
 *
 * Game actions are implemented and completed.
 *
 * Enhancements:
 * Buttons behave properly and add to the functionality of the game.
 *      New Game
 *      Finalize
 *      Undo
 */

public class QuoridorMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 10069;

    @Override
    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // yellow-on-blue GUI
        playerTypes.add(new GamePlayerType("Human Player") {
            public GamePlayer createPlayer(String name) {
                return new QuoridorHumanPlayer(name, R.layout.quoridor_main);
            }
        });

        // dumb computer player
        playerTypes.add(new GamePlayerType("Computer Player (Random)") {
            public GamePlayer createPlayer(String name) {
                return new QuoridorComputerPlayer(name);
            }
        });

        //TODO add smart computer

        playerTypes.add(new GamePlayerType("Computer Player (Smart)") {
            public GamePlayer createPlayer(String name) {
                return new QuoridorSmartComputerPlayer(name);
            }
        });


        // Create a game configuration class for Quoridor
        GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Quoridor", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer", 1); // dumb computer player

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 0);

        //done!
        return defaultConfig;



    }

    @Override
    public LocalGame createLocalGame() {
        return new QuoridorLocalGame();
    }
}
