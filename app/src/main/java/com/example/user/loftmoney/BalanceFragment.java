package com.example.user.loftmoney;


import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BalanceFragment extends androidx.fragment.app.Fragment {


    public BalanceFragment() {

    }

    public static BalanceFragment newInstance() {
        
        Bundle args = new Bundle();
        
        BalanceFragment fragment = new BalanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView balanceView;
    private TextView expenseView;
    private TextView incomeView;
    private DiagramView diagramView;
    private Api api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Application application = getActivity().getApplication();
        App app = (App) application;
        api = app.getApi();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        balanceView = view.findViewById(R.id.balance_value);
        expenseView = view.findViewById(R.id.expense_value);
        incomeView = view.findViewById(R.id.income_value);
        diagramView = view.findViewById(R.id.diagram_view);
    }

    private void loadBalance(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String token = preferences.getString("auth_token", null);
        Call <BalanceResponse> call = api.balance( token);

        call.enqueue(new Callback<BalanceResponse>() {
            @Override
            public void onResponse(Call<BalanceResponse> call, Response<BalanceResponse> response) {
                BalanceResponse balanceResponse = response.body();

                int balance = balanceResponse.getTotalIncome() - balanceResponse.getTotalExpense();

                balanceView.setText(getString(R.string.count, balance));
                expenseView.setText(getString(R.string.count, balanceResponse.getTotalExpense()));
                incomeView.setText(getString(R.string.count, balanceResponse.getTotalIncome()));

                diagramView.upDate(balanceResponse.getTotalIncome(), balanceResponse.getTotalExpense());
            }

            @Override
            public void onFailure(Call<BalanceResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            loadBalance();
        }
    }
}
