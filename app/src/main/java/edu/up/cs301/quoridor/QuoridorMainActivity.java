package edu.up.cs301.quoridor;

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
 */

public class QuoridorMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 5213;

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

        // Create a game configuration class for Tic-tac-toe
        GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Quoridor", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0); // yellow-on-blue GUI
        defaultConfig.addPlayer("Computer", 1); // dumb computer player

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 0); // red-on-yellow GUI

        //done!
        return defaultConfig;

    }

    @Override
    public LocalGame createLocalGame() {
        return new QuoridorLocalGame();
    }
}
