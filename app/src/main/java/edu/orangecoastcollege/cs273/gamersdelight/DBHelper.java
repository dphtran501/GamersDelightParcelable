package edu.orangecoastcollege.cs273.gamersdelight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * A model class to manage the SQLite database used to store <code>Game</code> data.
 */
class DBHelper extends SQLiteOpenHelper
{

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    static final String DATABASE_NAME = "GamersDelight";
    private static final String DATABASE_TABLE = "Games";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_RATING = "rating";
    private static final String FIELD_IMAGE_NAME = "image_name";

    /**
     * Instantiates a new <code>DBHelper</code> object with the given context.
     *
     * @param context The activity used to open or create the database.
     */
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the database table for the first time.
     *
     * @param sqLiteDatabase The database.
     */
    @Override
    public void onCreate(SQLiteDatabase database)
    {
        String table = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_FIELD_ID + " INTEGER PRIMARY KEY, " + FIELD_NAME + " TEXT, " + FIELD_DESCRIPTION + " TEXT, " + FIELD_RATING + " REAL, " + FIELD_IMAGE_NAME + " TEXT" + ")";
        database.execSQL(table);
    }

    /**
     * Drops the existing database table and creates a new one when database is upgraded.
     *
     * @param sqLiteDatabase The database.
     * @param oldVersion     The old database version.
     * @param newVersion     The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    //********** DATABASE OPERATIONS:  ADD, GETALL, EDIT, DELETE

    /**
     * Adds a <code>Game</code> object to the database.
     *
     * @param game The <code>Game</code> object to add to the database.
     */
    public void addGame(Game game)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //ADD KEY-VALUE PAIR INFORMATION FOR THE GAME NAME
        values.put(FIELD_NAME, game.getName());

        //ADD KEY-VALUE PAIR INFORMATION FOR THE GAME DESCRIPTION
        values.put(FIELD_DESCRIPTION, game.getDescription());

        //ADD KEY-VALUE PAIR INFORMATION FOR THE GAME RATING
        values.put(FIELD_RATING, game.getRating());

        //ADD KEY-VALUE PAIR INFORMATION FOR THE GAME RATING
        values.put(FIELD_IMAGE_NAME, game.getImageName());

        // INSERT THE ROW IN THE TABLE
        long id = db.insert(DATABASE_TABLE, null, values);

        // UPDATE THE GAME WITH THE NEWLY ASSIGNED ID
        game.setId(id);

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    /**
     * Gets a list of all <code>Game</code> objects in the database.
     *
     * @return List of all <code>Game</code> objects in the database.
     */
    public ArrayList<Game> getAllGames()
    {
        ArrayList<Game> gameList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE, new String[]{KEY_FIELD_ID, FIELD_NAME, FIELD_DESCRIPTION, FIELD_RATING, FIELD_IMAGE_NAME}, null, null, null, null, null, null);

        //COLLECT EACH ROW IN THE TABLE
        if (cursor.moveToFirst())
        {
            do
            {
                Game game = new Game(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getString(4));
                gameList.add(game);
            } while (cursor.moveToNext());
        }
        return gameList;
    }

    /**
     * Deletes a <code>Game</code> in the database.
     *
     * @param game The <code>Game</code> to delete in the database.
     */
    public void deleteGame(Game game)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // DELETE THE TABLE ROW
        db.delete(DATABASE_TABLE, KEY_FIELD_ID + " = ?", new String[]{String.valueOf(game.getId())});
        db.close();
    }

    /**
     * Deletes all <code>Game</code> objects in the database.
     */
    public void deleteAllGames()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }

    /**
     * Updates a <code>Game</code> record in the database.
     *
     * @param game The <code>Game</code> object to update in the database.
     */
    public void updateGame(Game game)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, game.getName());
        values.put(FIELD_DESCRIPTION, game.getDescription());
        values.put(FIELD_RATING, game.getRating());
        values.put(FIELD_IMAGE_NAME, game.getImageName());

        db.update(DATABASE_TABLE, values, KEY_FIELD_ID + " = ?", new String[]{String.valueOf(game.getId())});
        db.close();
    }

    /**
     * Gets a <code>Game</code> object in the database.
     *
     * @param id The database ID of the <code>Game</code> object in the database.
     * @return The <code>Game</code> object in the database with the argument ID.
     */
    public Game getGame(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_FIELD_ID, FIELD_NAME, FIELD_DESCRIPTION, FIELD_RATING, FIELD_IMAGE_NAME}, KEY_FIELD_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) cursor.moveToFirst();

        Game game = new Game(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getString(4));

        db.close();
        return game;
    }


}
