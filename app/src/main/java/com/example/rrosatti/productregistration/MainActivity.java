package com.example.rrosatti.productregistration;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listCategories;
    private Button btManageProducts;
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniViews();
        helper = new DatabaseHelper(this);
        database = helper.getReadableDatabase();

        showCategories();

        listCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent in = new Intent(MainActivity.this, ProductActivity.class);
                in.putExtra("categoryId", id);
                startActivity(in);
            }
        });
    }

    private void iniViews() {
        listCategories = (ListView) findViewById(R.id.listCategories);
        btManageProducts = (Button) findViewById(R.id.btManageProducts);
    }

    private void showCategories() {
        String sql = "SELECT * FROM category;";
        cursor = database.rawQuery(sql, null);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{"name"},
                new int[]{android.R.id.text1},
                0);
        listCategories.setAdapter(simpleCursorAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.close();
    }
}
