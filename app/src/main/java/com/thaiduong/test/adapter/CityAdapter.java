package com.thaiduong.test.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thaiduong.test.R;
import com.thaiduong.test.app_interface.ISentCityNameListener;
import com.thaiduong.test.model.City;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
    private final List<City> mCityList;
    private final ISentCityNameListener mISentCityNameListener;

    @SuppressLint("NotifyDataSetChanged")
    public CityAdapter(List<City> mCityList, ISentCityNameListener mISentCityNameListener) {
        this.mCityList = mCityList;
        this.mISentCityNameListener = mISentCityNameListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City city = mCityList.get(position);
        if (city == null) {
            return;
        }
        holder.tvCityName.setText(city.getName());
        holder.tvCountry.setText(city.getCountry());
        holder.layoutItem.setOnClickListener(v -> mISentCityNameListener.sentCityName(city.getName(), true));
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCityName;
        private final TextView tvCountry;
        private final LinearLayout layoutItem;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCityName = itemView.findViewById(R.id.tv_city_name);
            tvCountry = itemView.findViewById(R.id.tv_country);
            layoutItem = itemView.findViewById(R.id.layout_item);
        }
    }
}
