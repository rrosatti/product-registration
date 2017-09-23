package com.example.rrosatti.productregistration;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ControlProductActivity extends AppCompatActivity {

    private TextView tvDescription;
    private TextView tvQtdStock;
    private TextView tvPriceProduct;
    private EditText etSetStock;
    private Button btnUpdate;
    private long id;
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_product);
        iniView();
        id = getIntent().getLongExtra("_id",0);

        if(id == 0){
            Toast.makeText(this, "Sorry, something went wrong.", Toast.LENGTH_SHORT).show();
            finish();
        }

        helper = new DatabaseHelper(this);
        database = helper.getReadableDatabase();

        String sql = "SELECT * FROM product WHERE _id = " + id + ";";
        cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();

        tvDescription.setText("Description: " + cursor.getString(2));
        tvQtdStock.setText("Quantity Stock: " + cursor.getInt(3));
        final int qtdStock = cursor.getInt(3);
        tvPriceProduct.setText("Price Product: &" + cursor.getFloat(4));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qtdStock - Integer.parseInt(etSetStock.getText().toString()) < 0 ){
                    Toast.makeText(getBaseContext(),"Value in stock not is sufficient",Toast.LENGTH_SHORT).show();
                }else {
                    int newStock = qtdStock - Integer.parseInt(etSetStock.getText().toString());
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("quantity", newStock);
                    String whereClause = "_id = ?";
                    String[] whereArgs = new String[]{Long.toString(id)};

                    database.update("product", contentValues, whereClause, whereArgs);
                    Toast.makeText(getBaseContext(), "Stock successfully updated", Toast.LENGTH_SHORT).show();
                    tvQtdStock.setText("Quantity Stock: " + newStock);
                }
                }
        });

    }

    public void iniView(){
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvQtdStock = (TextView) findViewById(R.id.tvQtdStock);
        tvPriceProduct = (TextView) findViewById(R.id.tvPriceProduct);
        etSetStock = (EditText) findViewById(R.id.etSetStock);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
        helper.close();
    }
}
