package edu.orangecoastcollege.cs273.gamersdelight;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The <code>Game</code> class maintains information about a video game,
 * including its id number, name, description, rating and image name.
 *
 * @author Michael Paulding
 */
public class Game implements Parcelable
{

    //Member variables
    private long mId;
    private String mName;
    private String mDescription;
    private float mRating;
    private String mImageName;

    /**
     * Creates a default <code>Game</code> with an id of -1, empty description,
     * rating of 0.0f and default image name of avatar.png.
     */
    public Game()
    {
        this(-1, "", "", 0.0f, "avatar.png");
    }

    /**
     * Creates a new <code>Game</code> from its id, description and status.
     *
     * @param description The game description
     * @param rating      The game rating (out of 5.0)
     */
    public Game(String name, String description, float rating)
    {
        this(-1, name, description, rating, "avatar.png");
    }

    /**
     * Creates a new <code>Game</code> from its id, description and status.
     *
     * @param description The game description
     * @param rating      The game rating (out of 5.0)
     * @param imageName   The image file name of the game
     */
    public Game(String name, String description, float rating, String imageName)
    {
        this(-1, name, description, rating, imageName);
    }

    // Parcelable interface uses a private constructor to instantiate objects

    /**
     * Creates a new <code>Game</code> from a parcel.
     *
     * @param parcel The package with all information for the <code>Game</code>.
     */
    private Game(Parcel parcel)
    {
        // ORDER MATTERS !!!
        mId = parcel.readLong();
        mName = parcel.readString();
        mDescription = parcel.readString();
        mRating = parcel.readFloat();
        mImageName = parcel.readString();
    }

    // In order to read a Parcel, we need a CREATOR (STATIC FIELD)
    /**
     * Interface that must be implemented and provided as a public CREATOR field that generates
     * instances of the <code>Game</code> class from a Parcel.
     */
    public static final Parcelable.Creator<Game> CREATOR = new Creator<Game>()
    {
        /**
         * This method is used with Intents to create new <code>Game</code> objects.
         * @param parcel The package with all information for the <code>Game</code>.
         * @return The new <code>Game</code> object.
         */
        @Override
        public Game createFromParcel(Parcel parcel)
        {
            return new Game(parcel);
        }

        /**
         * This method is used with JSON to create an array of <code>Game</code> objects.
         * @param size The size of the JSON array (how many <code>Game</code> objects).
         * @return New array of <code>Game</code> objects.
         */
        @Override
        public Game[] newArray(int size)
        {
            return new Game[size];
        }
    };

    /**
     * Creates a new <code>Game</code> from its id, description and status.
     *
     * @param id          The task id
     * @param description The game description
     * @param rating      The game rating (out of 5.0)
     * @param imageName   The image file name of the game
     */
    public Game(long id, String name, String description, float rating, String imageName)
    {
        mId = id;
        mName = name;
        mDescription = description;
        mRating = rating;
        mImageName = imageName;
    }

    /**
     * Gets the unique id of the <code>Game</code>.
     *
     * @return The unique id
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the unique id of the <code>Game</code>.
     *
     * @param id The unique id
     */
    public void setId(long id)
    {
        mId = id;
    }

    /**
     * Gets the name of the <code>Game</code>.
     *
     * @return The game name
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the name of the <code>Game</code>.
     *
     * @param name The new game name.
     */
    public void setName(String name)
    {
        mName = name;
    }

    /**
     * Gets the description of the <code>Game</code>.
     *
     * @return The task description
     */
    public String getDescription()
    {
        return mDescription;
    }

    /**
     * Sets the description of the <code>Game</code>.
     *
     * @param desc The new task description
     */
    public void setDescription(String desc)
    {
        mDescription = desc;
    }

    /**
     * Gets the rating of the <code>Game</code>.
     *
     * @return The rating (number of stars) of the game.
     */
    public float getRating()
    {
        return mRating;
    }

    /**
     * Sets the rating of the <code>Game</code>.
     *
     * @param rating The rating (number of stars) of the game.
     */
    public void setRating(float rating)
    {
        mRating = rating;
    }

    /**
     * Gets the image file name of the <code>Game</code>.
     *
     * @return The image file name (e.g. lol.png) of the game.
     */
    public String getImageName()
    {
        return mImageName;
    }

    /**
     * Sets the image file name of the <code>Game</code>.
     *
     * @param imageName The image file name (e.g. lol.png) of the game.
     */
    public void setImageName(String imageName)
    {
        mImageName = imageName;
    }

    /**
     * A method for displaying a <code>Game</code> as a String in the form:
     * <p>
     * Game{id=1, Name=League of Legends, Description=Multiplayer online battle arena game,
     * Rating=4.5, ImageName=lol.png}
     *
     * @return The formatted String
     */
    @Override
    public String toString()
    {
        return "Game{" + "Id=" + mId + ", Name='" + mName + '\'' + ", Description='" + mDescription + '\'' + ", Rating=" + mRating + ", ImageName='" + mImageName + '\'' + '}';
    }

    /**
     * Returns 0 if it's a standard parcel, else if sending files need to return file descriptors.
     *
     * @return 0
     */
    @Override
    public int describeContents()
    {
        return 0;
    }

    /**
     * Writes all the member variables of the class to the parcel. We specify the data types.
     *
     * @param parcel The package with details about the <code>Game</code>.
     * @param i      Any custom flags (with files)
     */
    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(mId);
        parcel.writeString(mName);
        parcel.writeString(mDescription);
        parcel.writeFloat(mRating);
        parcel.writeString(mImageName);
    }
}
