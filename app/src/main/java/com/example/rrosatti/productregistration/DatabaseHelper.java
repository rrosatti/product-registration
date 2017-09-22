package com.example.rrosatti.productregistration;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rrosatti on 9/22/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "store";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE category (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT);");
        db.execSQL("CREATE TABLE product (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, description TEXT, quantity INTEGER, price REAL, categoryId INTEGER, " +
                "FOREIGN KEY(categoryId) REFERENCES category(_id));");
        fillCategories(db);
        fillProducts(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void fillCategories(SQLiteDatabase db) {
        // Book, Movie, (Electronics & Computers), School Supplies
        db.execSQL("INSERT INTO category(name) VALUES (\"Books\"), (\"Movies\"), " +
                "(\"Electronics & Computers\"), (\"School Supplies\");");
    }

    private void fillProducts(SQLiteDatabase db) {
        String insertProductTemplate = "INSERT INTO product(name, description, " +
                "quantity, price, categoryId) VALUES ";

        // create 2 books
        db.execSQL(insertProductTemplate + "(\"American Gods, Neil Gaiman\", " +
                "\"The novel is a blend of Americana, fantasy, and various strands " +
                "of ancient and modern mythology, all centering on the " +
                "mysterious and taciturn Shadow.\", " +
                "15, 45.90, 1), " +
                "(\"Sherlock Holmes, Sir Arthur Conan Doyle\", " +
                "\"A brilliant London-based detective, Holmes is famous for his prowess " +
                "at using logic and astute observation to solve cases. He is perhaps the " +
                "most famous fictional detective, and indeed one of the best known and most " +
                "universally recognizable literary characters.\", " +
                "42, 37.95, 1);");

        // create 2 movies
        db.execSQL(insertProductTemplate + "(\"Star Wars: The Force Awakens\", " +
                "\"Thirty years after the defeat of the Galactic Empire, the galaxy " +
                "faces a new threat from the evil Kylo Ren (Adam Driver) and the First Order.\", " +
                "20, 70.00, 2), " +
                "(\"Inception\", " +
                "\"Dom Cobb (Leonardo DiCaprio) is a thief with the rare ability to enter people's " +
                "dreams and steal their secrets from their subconscious. His skill has made him a hot " +
                "commodity in the world of corporate espionage but has also cost him everything he loves.\", " +
                "9, 40.75, 2);");

        // create 2 items of Electronics & Computers category
        db.execSQL(insertProductTemplate + "(\"Moto G (4th Gen.)\", " +
                "\"Fast 4G LTE speed, up to 1.5 GHz octa-core processor, 2 GB of RAM, and a bright 5.5-Inch " +
                "full-HD (1080p) display ensures videos and games run smoothly and look great.\", " +
                "32, 127.92, 3), " +
                "(\"Apple 13' MacBook Air\", " +
                "\"1.8GHz Intel Core i5 Dual Core Processor, 8GB RAM, 256GB SSD, Mac OS, Silver.\", " +
                "200, 999999.99, 3);");

        // create 2 items of School Supplies category
        db.execSQL(insertProductTemplate + "(\"Pen (Blue) - BIC\", " +
                "\"Blue pen of the brand BIC.\", " +
                "100, 0.20, 4), " +
                "(\"Laptop Backpack - JanSport\", " +
                "\"The JanSport Right Pack Digital Edition comes with modern digital protection " +
                "features. This backpack includes a padded 15 in laptop sleeve, soft tricot lined " +
                "tablet sleeve and front pocket with digital organizer.\", " +
                "30, 65.00, 4);");
    }
}
