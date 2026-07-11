package com.example.giftregister.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giftregister.R;
import com.example.giftregister.model.Gift;

import java.util.List;

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.GiftViewHolder> {
    private Context context;
    private List<Gift> giftList;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(Gift gift);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Gift gift);
    }

    public GiftAdapter(Context context, List<Gift> giftList) {
        this.context = context;
        this.giftList = giftList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    @NonNull
    @Override
    public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gift, parent, false);
        return new GiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftViewHolder holder, int position) {
        Gift gift = giftList.get(position);
        holder.bind(gift);
    }

    @Override
    public int getItemCount() {
        return giftList == null ? 0 : giftList.size();
    }

    public void updateList(List<Gift> newList) {
        this.giftList = newList;
        notifyDataSetChanged();
    }

    class GiftViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFrom;
        private TextView tvItem;
        private TextView tvAmount;
        private TextView tvDate;
        private TextView tvNotes;

        public GiftViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFrom = itemView.findViewById(R.id.tv_gift_from);
            tvItem = itemView.findViewById(R.id.tv_gift_item);
            tvAmount = itemView.findViewById(R.id.tv_gift_amount);
            tvDate = itemView.findViewById(R.id.tv_gift_date);
            tvNotes = itemView.findViewById(R.id.tv_gift_notes);
        }

        public void bind(Gift gift) {
            tvFrom.setText("来自: " + gift.getGiftFrom());
            tvItem.setText("礼品: " + gift.getGiftItem());
            tvAmount.setText("金额: ¥" + String.format("%.2f", gift.getAmount()));
            tvDate.setText("日期: " + gift.getDate());
            tvNotes.setText(gift.getNotes() == null || gift.getNotes().isEmpty() ? "无备注" : "备注: " + gift.getNotes());

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(gift);
                }
            });

            itemView.setOnLongClickListener(v -> {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(gift);
                    return true;
                }
                return false;
            });
        }
    }
}
