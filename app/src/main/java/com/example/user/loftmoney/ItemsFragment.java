package com.example.user.loftmoney;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    public static ItemsFragment newInstance(int type){
        ItemsFragment fragment = new ItemsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);

        fragment.setArguments(bundle);

        return fragment;

    }

    public static final int TYPE_INCOMES = 0;
    public static final int TYPE_EXPENSES = 1;
    public static final int TYPE_BALANCE = 2;
    public static final String KEY_TYPE = "type";

    ItemsAdapter adapter;
    private int type;

    public ItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ItemsAdapter();

      type = getArguments().getInt(KEY_TYPE);



        Log.d("ItemsFragment", "type" + type);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recycler = view.findViewById(R.id.recycler);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));
         adapter.setItems(createTempItems());
    }

    private List<Item> createTempItems(){

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

        return items;
    }

}
