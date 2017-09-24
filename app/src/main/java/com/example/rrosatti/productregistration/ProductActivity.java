package com.example.rrosatti.productregistration;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends AppCompatActivity {

    private ListView listProducts;
    private long categoryId = 0;
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        categoryId = getIntent().getLongExtra("categoryId", 0);
        if (categoryId == 0) {
            Toast.makeText(this, "Sorry, something went wrong.", Toast.LENGTH_SHORT).show();
            finish();
        }

        helper = new DatabaseHelper(this);
        database = helper.getReadableDatabase();
        listProducts = (ListView) findViewById(R.id.listProducts);

        String sql = "SELECT * FROM product WHERE categoryId = " + categoryId + ";";
        cursor = database.rawQuery(sql, null);

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.product_item,
                cursor,
                new String[] {"_id", "name", "quantity", "price"},
                new int[] {R.id.imgProduct, R.id.txtProductName, R.id.txtQuantity, R.id.txtPrice},
                0);
        simpleCursorAdapter.setViewBinder(new CustomViewBinder());
        listProducts.setAdapter(simpleCursorAdapter);



        listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positon, long id) {  
                Intent in = new Intent(ProductActivity.this,ControlProductActivity.class);
                in.putExtra("_id",id);
                startActivityForResult(in, 100);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
        helper.close();
        Log.d("here", "onDestroy: ");
    }

    private class CustomViewBinder implements SimpleCursorAdapter.ViewBinder {

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            if (columnIndex == cursor.getColumnIndex("_id")) {
                ImageView imgProduct = (ImageView)view;
                setImageDrawable(imgProduct);
                return true;
            }
            else if (columnIndex == cursor.getColumnIndex("quantity")) {
                TextView txtQuantity = (TextView)view;
                String quantity = getString(R.string.text_quantity) + String.valueOf(cursor.getInt(columnIndex));
                txtQuantity.setText(quantity);
                return true;
            }
            else if (columnIndex == cursor.getColumnIndex("price")) {
                TextView txtPrice = (TextView)view;
                String price = getString(R.string.text_price) + String.valueOf(cursor.getFloat(columnIndex));
                txtPrice.setText(price);
                return true;
            }

            return false;

        }

    }

    private void setImageDrawable(ImageView img) {
        switch ((int)categoryId) {
            case 1: {
                img.setImageDrawable(getResources().getDrawable(R.drawable.book));
                break;
            }
            case 2: {
                img.setImageDrawable(getResources().getDrawable(R.drawable.movie));
                break;
            }
            case 3: {
                img.setImageDrawable(getResources().getDrawable(R.drawable.computer));
                break;
            }
            case 4: {
                img.setImageDrawable(getResources().getDrawable(R.drawable.school));
                break;
            }
            default: {
                img.setImageDrawable(getResources().getDrawable(R.drawable.book));
                break;
            }
        }
    }

    private void updateCursor() {
        String sql = "SELECT * FROM product WHERE categoryId = " + categoryId + ";";
        Cursor newCursor = database.rawQuery(sql, null);
        CursorAdapter cursorAdapter = (CursorAdapter)listProducts.getAdapter();
        cursorAdapter.changeCursor(newCursor);
        cursor.close();
        cursor = newCursor;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100) {
            switch (resultCode) {
                case Activity.RESULT_OK: {
                    updateCursor();
                    break;
                }
            }
        }
    }
}
