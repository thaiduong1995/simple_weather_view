package com.thaiduong.test.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thaiduong.test.R;
import com.thaiduong.test.model.Day;

import java.util.List;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder> {
    public static final String TAG = WeatherForecastAdapter.class.getName();
    private final Context mContext;
    private List<Day> mDayList;

    @SuppressLint("NotifyDataSetChanged")
    public WeatherForecastAdapter(Context mContext, List<Day> mDayList) {
        this.mContext = mContext;
        this.mDayList = mDayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new WeatherForecastViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WeatherForecastViewHolder holder, int position) {
        Day mDay = mDayList.get(position);
        if (mDay == null) {
            return;
        }

        holder.tvDate.setText(mDay.getDatetime());
        holder.tvDescription.setText(mDay.getDescription());
        holder.tvHumidity.setText(Math.round(mDay.getHumidity()) + "%");
        String imgID = mDay.convertUriIconName();
        holder.ivDescription.setImageResource(mContext.getResources().getIdentifier(imgID
                , "drawable", mContext.getPackageName()));
        holder.tvMaxTemp.setText(mDay.convertTemperatureToDegrees(mDay.getTempMax()));
        holder.tvMinTemp.setText(mDay.convertTemperatureToDegrees(mDay.getTempMin()));
    }

    @Override
    public int getItemCount() {
        return mDayList.size();
    }

    public static class WeatherForecastViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate, tvDescription, tvHumidity, tvMaxTemp, tvMinTemp;
        private final ImageView ivDescription;

        public WeatherForecastViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_date);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvHumidity = itemView.findViewById(R.id.tv_humidity);
            tvMaxTemp = itemView.findViewById(R.id.tv_max_temp);
            tvMinTemp = itemView.findViewById(R.id.tv_min_temp);
            ivDescription = itemView.findViewById(R.id.iv_description);
        }
    }
}
