package com.example.user.loftmoney;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    public final static String KEY_TYPE = "type";

    private EditText name;
    private EditText price;
    private Button addBtn;

    private Api api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        api = ((App)getApplication()).getApi();

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        addBtn = findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();
                String namePrice = price.getText().toString();
                String type = getIntent().getStringExtra(KEY_TYPE);

                addItem(nameText, namePrice, type);
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

    private void addItem(String name, String price, String type){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("auth_token", null);

        AddItemRequest request = new AddItemRequest(Double.valueOf(price), name, type);

        Call<Object> call = api.addItem( request, token);
        call.enqueue(new Callback<Object>() {


         @Override
         public void onResponse(Call<Object> call, Response<Object> response) {
             setResult(MainActivity.RESULT_OK);

             finish();
         }

         @Override
         public void onFailure(Call<Object> call, Throwable t) {
             setResult(MainActivity.RESULT_CANCELED);

             finish();
         }
     });


}


}
