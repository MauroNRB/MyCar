package com.mycar.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mycar.app.R;
import com.mycar.app.models.AccelerationTestRecord;
import com.mycar.app.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

public class AccelerationTestAdapter extends RecyclerView.Adapter<AccelerationTestAdapter.ViewHolder> {

    private List<AccelerationTestRecord> list = new ArrayList<>();

    public void setItems(List<AccelerationTestRecord> items) {
        this.list = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acceleration_test, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccelerationTestRecord item = list.get(position);
        holder.tvAccelTime.setText(FormatUtils.formatTimeSeconds(item.getElapsedTimeSeconds()));
        holder.tvAccelDate.setText(item.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAccelTime, tvAccelDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvAccelTime = itemView.findViewById(R.id.tv_accel_time);
            tvAccelDate = itemView.findViewById(R.id.tv_accel_date);
        }
    }
}
