package com.example.user.loftmoney;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> items = Collections.emptyList();
    private ItemsAdapterListener listener = null;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();



    public void addItem(Item item) {
        this.items.add(item);
        notifyItemInserted(items.size());
    }


    public void setListener(ItemsAdapterListener listener) {
        this.listener = listener;
    }

     Item removeItem(int position){
        Item item = items.get(position);
        items.remove(position);
        notifyItemRemoved(position);
        return item;
    }


    void toggleItem(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);

        }
        notifyItemChanged(position);
    }

    void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }



    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    List<Integer> getSelectedPositions() {
        List<Integer> selectedPosition = new ArrayList<>();

        for (int i = 0; i < selectedItems.size(); i++) {
            int key = selectedItems.keyAt(i);

            if (selectedItems.get(key)) {
                selectedPosition.add(key);
            }
        }
        return selectedPosition;

    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bindItem(item, selectedItems.get(position));
        holder.setListenter(item, listener, position);
        holder.setFragmentColor(item.getType());
    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public int getSelectedCount() {
        return (selectedItems.size());
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;

        private Context context;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);

        }

        void bindItem(Item item, boolean selected) {
            name.setText(item.getName());
            price.setText(context.getString(R.string.count, String.valueOf(item.getPrice())));


            itemView.setSelected(selected);
        }

       void setFragmentColor (String type){
            if(type.equals(Item.TYPE_INCOME)){
                price.setTextColor(ContextCompat.getColor(context, R.color.income_color));
            }
            else if (type.equals(Item.TYPE_EXPENSE)){
                price.setTextColor(ContextCompat.getColor(context,R.color.expense_color));
            }
        }

        void setListenter(Item item, ItemsAdapterListener listener, int position) {
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(item, position);
                }
            });

            itemView.setOnLongClickListener(v -> {
                if (listener != null) {
                    listener.onItemLongClick(item, position);
                }
                return true;

            });

        }

    }

}
