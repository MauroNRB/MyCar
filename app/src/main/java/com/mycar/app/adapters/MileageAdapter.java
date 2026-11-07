package com.mycar.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mycar.app.R;
import com.mycar.app.models.MileageRecord;
import com.mycar.app.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

public class MileageAdapter extends RecyclerView.Adapter<MileageAdapter.ViewHolder> {

    private List<MileageRecord> list = new ArrayList<>();

    public void setItems(List<MileageRecord> items) {
        this.list = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mileage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MileageRecord item = list.get(position);
        holder.tvOdometer.setText(FormatUtils.formatOdometer(item.getOdometer()));
        holder.tvDate.setText(item.getDateTimestamp());

        if (item.getDifference() > 0) {
            holder.tvDifference.setText("+" + FormatUtils.formatDistance(item.getDifference()));
            holder.tvDifference.setVisibility(View.VISIBLE);
        } else {
            holder.tvDifference.setVisibility(View.GONE);
        }

        if (item.getNotes() != null && !item.getNotes().trim().isEmpty()) {
            holder.tvNotes.setText(item.getNotes());
            holder.tvNotes.setVisibility(View.VISIBLE);
        } else {
            holder.tvNotes.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOdometer, tvDifference, tvDate, tvNotes;

        ViewHolder(View itemView) {
            super(itemView);
            tvOdometer = itemView.findViewById(R.id.tv_odometer);
            tvDifference = itemView.findViewById(R.id.tv_difference);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvNotes = itemView.findViewById(R.id.tv_notes);
        }
    }
}
