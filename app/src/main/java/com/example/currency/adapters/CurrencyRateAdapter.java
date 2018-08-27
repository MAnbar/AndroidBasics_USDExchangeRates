package com.example.currency.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.currency.R;

import java.util.ArrayList;
import java.util.List;

import com.example.currency.models.CurrencyRate;

public class CurrencyRateAdapter extends  RecyclerView.Adapter<CurrencyRateAdapter.RateViewHolder> implements Filterable{

    private Context context;
    private List<CurrencyRate> currencyRateList;
    private List<CurrencyRate> filteredCurrencyRateList;

    public CurrencyRateAdapter(Context context, List<CurrencyRate> currencyRateList) {
        this.context = context;
        this.currencyRateList = currencyRateList;
        this.filteredCurrencyRateList = currencyRateList;
    }

    public void setCurrencyRateList(List<CurrencyRate> currencyRateList) {
        this.currencyRateList = currencyRateList;
        this.filteredCurrencyRateList = currencyRateList;
    }

    @Override
    public RateViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_currency, viewGroup, false);
        RateViewHolder evh = new RateViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(RateViewHolder rateViewHolder, int i) {
        rateViewHolder.rateName.setText(filteredCurrencyRateList.get(i).getRateName());
        rateViewHolder.rate.setText(filteredCurrencyRateList.get(i).getRate());
    }

    @Override
    public int getItemCount() {
        return filteredCurrencyRateList.size();
    }

    public void notifyDataChanged(){
        this.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredCurrencyRateList = currencyRateList;
                } else {
                    List<CurrencyRate> filteredList = new ArrayList<>();
                    for (CurrencyRate row : currencyRateList) {
                        if (row.getRateName().toLowerCase().contains(charString.toLowerCase()) || row.getRate().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    filteredCurrencyRateList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCurrencyRateList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredCurrencyRateList = (ArrayList<CurrencyRate>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public static class RateViewHolder extends RecyclerView.ViewHolder {
        TextView rateName;
        TextView rate;

        RateViewHolder(View itemView) {
            super(itemView);
            rateName = (TextView)itemView.findViewById(R.id.entry_rateName);
            rate = (TextView)itemView.findViewById(R.id.entry_rate);
        }
    }
}
