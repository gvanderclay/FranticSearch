package vanderclay.comet.benson.franticsearch.API;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.List;
import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;


/**
 * Created by ben on 3/29/17.
 */

public class CardSearchAPI extends SQLiteOpenHelper implements Serializable {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "frantic_search";

    // Contacts table name
    private static final String TABLE_CARDS = "Cards";

    // Columns name names
    private static final String id = "id";
    private static final String name = "name";
    private static final String manaCost = "mana_cost";
    private static final String cmc = "cmc";
    private static final String colors = "colors";
    private static final String type = "type";
    private static final String subtypes = "sub_types";
    private static final String rarity = "rarity";
    private static final String text = "text";
    private static final String power = "power";
    private static final String toughness = "toughness";
    private static final String loyalty = "loyalty";
    private static final String imageName = "image_name";
    private static final String reserved = "reserved";
    private static final String releaseDate = "release_date";
    private static final String starter = "starter";
    private static final String owned = "owned";

    //Constructor
    public CardSearchAPI(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CARDS + "("
                + id + " INTEGER PRIMARY KEY, "
                + name + " TEXT, "
                + manaCost + " INTEGER, "
                + cmc + " INTEGER, "
                + colors + " TEXT, "
                + type + " TEXT, "
                + subtypes + " TEXT, "
                + rarity + " TEXT, "
                + text + " TEXT, "
                + power + " INTEGER, "
                + toughness + " INTEGER, "
                + loyalty + " INTEGER, "
                + imageName + " TEXT, "
                + reserved + " TEXT, "
                + releaseDate + " TEXT, "
                + starter + " TEXT, "
                + owned + " INTEGER "
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        // Create tables again
        onCreate(db);
    }

    /*
     * Get all the cards from the api we're using and creates an instance of the database.
     */
    public void addAllCards() {
        List<Card> allCards = CardAPI.getAllCards();
        SQLiteDatabase db = this.getWritableDatabase();
        for (Card apiCard : allCards) {
            System.out.println(apiCard);
            ContentValues values = new ContentValues();

//            values.put(KEY_NAME, contact.getName()); // Contact Name
//            values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone Number
            values.put(id, apiCard.getId());
            values.put(name, apiCard.getName());
            values.put(manaCost, apiCard.getManaCost());
            values.put(cmc, apiCard.getCmc());
            values.put(colors, apiCard.getColors().toString());
            values.put(type, apiCard.getType());
            values.put(subtypes, apiCard.getSubtypes().toString());
            values.put(rarity, apiCard.getRarity());
            values.put(text, apiCard.getText());
            values.put(power, apiCard.getPower());
            values.put(toughness, apiCard.getToughness());
            values.put(loyalty, apiCard.getLoyalty());
            values.put(imageName, apiCard.getImageName());
            values.put(reserved, apiCard.isReserved());
            values.put(releaseDate, apiCard.getReleaseDate());
            values.put(starter, apiCard.isStarter());
            values.put(owned, 0);

            // Inserting Row
            db.insert(TABLE_CARDS, null, values);
            // Closing database connection
        }
        db.close();

    }
    //Get all Contacts
    public List<CardDO> getContacts() {
        List <CardDO> resultSet = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_CARDS, null);

        if (cursor != null && cursor.moveToFirst()) {
            do{

                CardDO card = new CardDO();
                card.setId(cursor.getInt(0));
                card.setName(cursor.getString(1));
                card.setManaCost(cursor.getInt(2));
                card.setCmc(cursor.getInt(3));
                card.setColors(cursor.getString(4));
                card.setType(cursor.getString(5));
                card.setPower(cursor.getInt(6));
                card.setToughness(cursor.getInt(7));
                card.setLoyalty(cursor.getInt(8));
                card.setImageName(cursor.getString(9));
                card.setReserved(cursor.getInt(10) == 1);
                card.setReleaseDate(cursor.getString(11));
                card.setStarter(cursor.getString(12));
                card.setOwned(cursor.getInt(13) == 1);
                resultSet.add(card);
            }while(cursor.moveToFirst());
        }else{
            System.out.println("cursor is null or there's nothing in the database.");
        }
        return resultSet;
    }

    public static void main(String args[]){
//        CardSearchAPI cardSearchAPI = new CardSearchAPI();
//        cardSearchAPI.onCreate();
    }
}
