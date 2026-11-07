package com.mycar.app.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mycar.app.R;
import com.mycar.app.models.MaintenanceRecord;
import com.mycar.app.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceAdapter extends RecyclerView.Adapter<MaintenanceAdapter.ViewHolder> {

    public interface OnMaintenanceClickListener {
        void onItemClick(MaintenanceRecord item);
    }

    private List<MaintenanceRecord> list = new ArrayList<>();
    private OnMaintenanceClickListener listener;

    public void setOnItemClickListener(OnMaintenanceClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<MaintenanceRecord> items) {
        this.list = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintenance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MaintenanceRecord item = list.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvCategory.setText(item.getCategory());
        holder.tvCost.setText(FormatUtils.formatCurrency(item.getCost()));

        String info = (item.getDatePerformed() != null ? item.getDatePerformed() : "") + " • " + FormatUtils.formatOdometer(item.getOdometer());
        holder.tvDateOdometer.setText(info);

        if (item.getWorkshop() != null && !item.getWorkshop().isEmpty()) {
            holder.tvWorkshop.setText("Oficina: " + item.getWorkshop());
            holder.tvWorkshop.setVisibility(View.VISIBLE);
        } else {
            holder.tvWorkshop.setVisibility(View.GONE);
        }

        if (item.isScheduled()) {
            holder.tvStatusBadge.setText("Agendada");
            holder.tvStatusBadge.setTextColor(Color.parseColor("#FF9100"));
            holder.tvStatusBadge.setBackgroundResource(R.drawable.badge_status_upcoming);
        } else {
            holder.tvStatusBadge.setText("Realizada");
            holder.tvStatusBadge.setTextColor(Color.parseColor("#00E676"));
            holder.tvStatusBadge.setBackgroundResource(R.drawable.badge_status_ok);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCategory, tvCost, tvDateOdometer, tvWorkshop, tvStatusBadge;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvCost = itemView.findViewById(R.id.tv_cost);
            tvDateOdometer = itemView.findViewById(R.id.tv_date_odometer);
            tvWorkshop = itemView.findViewById(R.id.tv_workshop);
            tvStatusBadge = itemView.findViewById(R.id.tv_status_badge);
        }
    }
}
