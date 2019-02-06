package com.example.user.loftmoney;



import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private ItemsAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        adapter = new ItemsAdapter();
        recycler = findViewById(R.id.recycler);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        List <Item> items = new ArrayList<>();
        items.add(new Item("Молоко", "70"));
        items.add(new Item("Зубная щетка", "120"));
        items.add(new Item("Печенье", "56"));
        items.add(new Item("Холодильник", "20000"));
        items.add(new Item("Яблоки", "40"));
        items.add(new Item("Курица", "156"));
        items.add(new Item("Шкаф", "10100"));
        items.add(new Item("Плита", "25700"));
        items.add(new Item("Морковь", "56"));
        items.add(new Item("Яйца", "75"));
        items.add(new Item("Имбирь", "121"));
        items.add(new Item("Кофе", "450"));
        items.add(new Item("Чай", "123"));
        items.add(new Item("Мюсли", "89"));
        items.add(new Item("Овсянка", "56"));

        adapter.setItems(items);


    }

    private Context getContext() {
        return this;
    }
}
