package com.mycar.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mycar.app.R;
import com.mycar.app.models.TripRecord;
import com.mycar.app.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    private List<TripRecord> list = new ArrayList<>();

    public void setItems(List<TripRecord> items) {
        this.list = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TripRecord item = list.get(position);
        holder.tvDistance.setText(FormatUtils.formatDistance(item.getDistanceKm()));
        holder.tvStartTime.setText(item.getStartTime());
        holder.tvAvgSpeed.setText("Vel. Média: " + Math.round(item.getAvgSpeedKmh()) + " km/h");
        holder.tvMaxSpeed.setText("Máx: " + Math.round(item.getMaxSpeedKmh()) + " km/h");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDistance, tvStartTime, tvAvgSpeed, tvMaxSpeed;

        ViewHolder(View itemView) {
            super(itemView);
            tvDistance = itemView.findViewById(R.id.tv_distance);
            tvStartTime = itemView.findViewById(R.id.tv_start_time);
            tvAvgSpeed = itemView.findViewById(R.id.tv_avg_speed);
            tvMaxSpeed = itemView.findViewById(R.id.tv_max_speed);
        }
    }
}
