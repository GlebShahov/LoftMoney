package com.example.user.loftmoney;


import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    private static final String TAG = "ItemsFragment";
    private static final int ADD_ITEM_REQUEST_CODE = 111;

    public static ItemsFragment newInstance(String type) {
        ItemsFragment fragment = new ItemsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, type);

        fragment.setArguments(bundle);

        return fragment;

    }


    public static final String KEY_TYPE = "type";

    private SwipeRefreshLayout refresh;


    private ItemsAdapter adapter;
    private String type;

    private Api api;
    private ActionMode actionMode;

    public ItemsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ItemsAdapter();
        adapter.setListener(new AdapterListener());

        type = getArguments().getString(KEY_TYPE);

        Application application = getActivity().getApplication();
        App app = (App) application;
        api = app.getApi();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        refresh = view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

        RecyclerView recycler = view.findViewById(R.id.recycler);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        recycler.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));

        loadItems();

    }

    private void loadItems() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String token = preferences.getString("auth_token", null);
        Call call = api.getItems(type, token);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                refresh.setRefreshing(false);
                List<Item> items = (List<Item>) response.body();
                adapter.setItems(items);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                refresh.setRefreshing(false);
                Log.e(TAG, "loadItems: ", t);

            }
        });

    }

    private void removeItem(Long id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String token = preferences.getString("auth_token", null);
        Call call = api.removeItem(id, token);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {


            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    public void onFabClick() {
        Intent intent = new Intent(requireContext(), AddItemActivity.class);
        intent.putExtra(AddItemActivity.KEY_TYPE, type);
        startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadItems();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class AdapterListener implements ItemsAdapterListener {

        @Override
        public void onItemClick(Item item, int position) {
            Log.i(TAG, "onItemClick: " + item.getName());

            if (actionMode == null) {
                return;
            }
            toggleItem(position);

        }

        @Override
        public void onItemLongClick(Item item, int position) {
            Log.i(TAG, "onItemLongClick: " + item.getName());

            if (actionMode != null) {
                return;
            }
            getActivity().startActionMode(new ActionModeCallback());
            toggleItem(position);

        }

        public void toggleItem(int position) {
            actionMode.setTitle(getString(R.string.item_fragment_selected)+ ": " + String.valueOf(adapter
                    .getSelectedCount()));
            adapter.toggleItem(position);

        }
    }

    private class ActionModeCallback implements android.view.ActionMode.Callback {


        @Override
        public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
            actionMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
            MenuInflater inflater = new MenuInflater(requireContext());
            inflater.inflate(R.menu.menu_action_mode, menu);

            return true;
        }

        @Override
        public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.delete_item) {
                showDialog();
                return true;
            }
            return false;

        }

        @Override
        public void onDestroyActionMode(android.view.ActionMode mode) {
            actionMode = null;
            adapter.clearSelections();

        }

        void removeSelectedItems() {
            List<Integer> selectedPosition = adapter.getSelectedPositions();

            for (int i = selectedPosition.size() - 1; i >= 0; i--) {
                Item item = adapter.removeItem(selectedPosition.get(i));
                removeItem(item.getId());
            }
            actionMode.finish();
        }

        void showDialog(){
            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setMessage(R.string.item_fragment_delete_message)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                removeSelectedItems();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create();

            dialog.show();
        }

    }

}
