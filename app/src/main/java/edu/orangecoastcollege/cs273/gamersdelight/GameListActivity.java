package edu.orangecoastcollege.cs273.gamersdelight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.List;

/**
 * This activity displays a list of <code>Game</code> objects from the app's database. The user can
 * add a new <code>Game</code> entry into the database and also clear the database. Clicking on a
 * list item in the ListView will launch <code>GameDetailsActivity</code> using information from
 * the selected <code>Game</code>.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 9, 2017
 */
public class GameListActivity extends AppCompatActivity
{

    private DBHelper db;
    private List<Game> gamesList;
    private GameListAdapter gamesListAdapter;
    private ListView gamesListView;

    /**
     * Initializes <code>GameListActivity</code> by inflating its UI.
     *
     * @param savedInstanceState Bundle containing the data it recently supplied in
     *                           onSaveInstanceState(Bundle) if activity was reinitialized after
     *                           being previously shut down. Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        this.deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);

        db.addGame(new Game("League of Legends", "Multiplayer online battle arena", 4.5f, "lol.png"));
        db.addGame(new Game("Dark Souls III", "Action role-playing", 4.0f, "ds3.png"));
        db.addGame(new Game("The Division", "Single player experience", 3.5f, "division.png"));
        db.addGame(new Game("Doom FLH", "First person shooter", 2.5f, "doomflh.png"));
        db.addGame(new Game("Battlefield 1", "Single player campaign", 5.0f, "battlefield1.png"));

        gamesList = db.getAllGames();
        gamesListAdapter = new GameListAdapter(this, R.layout.game_list_item, gamesList);
        gamesListView = (ListView) findViewById(R.id.gameListView);
        gamesListView.setAdapter(gamesListAdapter);

    }

    /**
     * Launches <code>GameDetailsActivity</code> showing information about the <code>Game</code>
     * object that was clicked in the ListView.
     *
     * @param v The view that called this method.
     */
    public void viewGameDetails(View view)
    {
        if (view instanceof LinearLayout)
        {
            LinearLayout selectedLayout = (LinearLayout) view;
            Game selectedGame = (Game) selectedLayout.getTag();
            Log.i("Gamers Delight", selectedGame.toString());
            Intent detailsIntent = new Intent(this, GameDetailsActivity.class);
            /*
            detailsIntent.putExtra("Name", selectedGame.getName());
            detailsIntent.putExtra("Description", selectedGame.getDescription());
            detailsIntent.putExtra("Rating", selectedGame.getRating());
            detailsIntent.putExtra("ImageName", selectedGame.getImageName());
            */
            detailsIntent.putExtra("SelectedGame", selectedGame);
            startActivity(detailsIntent);
        }
    }

    /**
     * Adds a <code>Game</code> object to the database and the ListView.
     *
     * @param v The view that called this method.
     */
    public void addGame(View view)
    {

        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.gameRatingBar);

        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description))
        {
            Toast.makeText(this, "Both name and description of the game must be provided.", Toast.LENGTH_LONG);
            return;
        }
        Game newGame = new Game(name, description, ratingBar.getRating());

        db.addGame(newGame);
        gamesListAdapter.add(newGame);
        nameEditText.setText("");
        descriptionEditText.setText("");
        ratingBar.setRating(0.0f);
    }

    /**
     * Deletes all <code>Game</code> objects in the database and ListView.
     *
     * @param view The view that called this method.
     */
    public void clearAllGames(View view)
    {
        gamesList.clear();
        db.deleteAllGames();
        gamesListAdapter.notifyDataSetChanged();
    }

}
