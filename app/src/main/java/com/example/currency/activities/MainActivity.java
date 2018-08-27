package com.example.currency.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.currency.R;

import java.util.ArrayList;
import java.util.List;

import com.example.currency.adapters.CurrencyRateAdapter;
import com.example.currency.handlers.ApiHandler;
import com.example.currency.models.CurrencyRate;


public class MainActivity extends AppCompatActivity {

    private List<CurrencyRate> currencyRateList;
    private RecyclerView recyclerView;
    private CurrencyRateAdapter currencyRateAdapter;
    private SearchView searchView;

    private void initializeData() {
        currencyRateList = ApiHandler.getRates();
        currencyRateAdapter.setCurrencyRateList(currencyRateList);
        currencyRateAdapter.notifyDataSetChanged();
    }

    private void initializeAdapter() {
        currencyRateAdapter = new CurrencyRateAdapter(this, currencyRateList);
        currencyRateAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(currencyRateAdapter);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        System.out.println("USER QUERY Handler");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println("USER QUERY" + query);
            //use the query to search your data somehow
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                currencyRateAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                currencyRateAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        currencyRateList = new ArrayList<>();
        currencyRateAdapter = new CurrencyRateAdapter(this, currencyRateList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(currencyRateAdapter);

        StrictMode.ThreadPolicy oldTP = StrictMode.getThreadPolicy();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();

        StrictMode.setThreadPolicy(policy);

        initializeData();

        StrictMode.setThreadPolicy(oldTP);

        //initializeAdapter();
    }
}
