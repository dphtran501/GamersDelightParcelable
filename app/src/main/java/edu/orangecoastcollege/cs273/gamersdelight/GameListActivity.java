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

public class GameListActivity extends AppCompatActivity {

    private DBHelper db;
    private List<Game> gamesList;
    private GameListAdapter gamesListAdapter;
    private ListView gamesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    public void viewGameDetails(View view) {
        if (view instanceof LinearLayout) {
            LinearLayout selectedLayout = (LinearLayout) view;
            Game selectedGame = (Game) selectedLayout.getTag();
            Log.i("Gamers Delight", selectedGame.toString());
            Intent detailsIntent = new Intent(this, GameDetailsActivity.class);
            detailsIntent.putExtra("Name", selectedGame.getName());
            detailsIntent.putExtra("Description", selectedGame.getDescription());
            detailsIntent.putExtra("Rating", selectedGame.getRating());
            detailsIntent.putExtra("ImageName", selectedGame.getImageName());
            startActivity(detailsIntent);
        }
    }

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

    public void clearAllGames(View view)
    {
        gamesList.clear();
        db.deleteAllGames();
        gamesListAdapter.notifyDataSetChanged();
    }

}
