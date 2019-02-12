package com.example.user.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    public final static String KEY_NAME = "name";
    public final static String KEY_PRICE = "price";

    private EditText name;
    private EditText price;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        addBtn = findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String nameText = name.getText().toString();
                String namePrice = price.getText().toString();

                intent.putExtra(KEY_NAME, nameText);
                intent.putExtra(KEY_PRICE, namePrice);

                setResult(MainActivity.RESULT_OK, intent);

                finish();
            }
        });

        TextWatcher  editWatcher = new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {

               if (name.getText().toString().trim().length() <= 0 || price.getText().toString().trim().length() <= 0)
                   addBtn.setEnabled(false);
               else addBtn.setEnabled(true);

           }
       };

       name.addTextChangedListener(editWatcher);
       price.addTextChangedListener(editWatcher);


    }
}
