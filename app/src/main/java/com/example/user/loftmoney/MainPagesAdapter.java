package com.example.user.loftmoney;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainPagesAdapter extends FragmentPagerAdapter {

     public static final int PAGE_INCOMES = 0;
     public static final int PAGE_EXPENSES = 1;
     public static final int PAGE_BALANCE = 2;

    private static final int PAGE_COUNT = 3;

    private Context context;

    public MainPagesAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case PAGE_INCOMES:
                return ItemsFragment.newInstance(Item.TYPE_INCOME);
            case PAGE_EXPENSES:
                return ItemsFragment.newInstance(Item.TYPE_EXPENSE);
            case PAGE_BALANCE:
                return ItemsFragment.newInstance(Item.TYPE_BALANCE);
            default:
                return new ItemsFragment();

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case PAGE_INCOMES:
                return context.getString(R.string.main_tab_incomes);
            case PAGE_EXPENSES:
                return context.getString(R.string.main_tab_expenses);
            case PAGE_BALANCE:
                return context.getString(R.string.main_tab_balance);
            default:
                return "";

        }

    }
}
