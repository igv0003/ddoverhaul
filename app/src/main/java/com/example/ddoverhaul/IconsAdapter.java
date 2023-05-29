package com.example.ddoverhaul;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IconsAdapter extends RecyclerView.Adapter<IconsAdapter.IconViewHolder> {

    private int[] iconos;
    private OnIconClickListener onIconClickListener;

    public IconsAdapter(int[] iconos, OnIconClickListener onIconClickListener) {
        this.iconos = iconos;
        this.onIconClickListener = onIconClickListener;
    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon, parent, false);
        return new IconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
        int iconId = iconos[position];
        holder.imageView.setImageResource(iconId);
    }

    @Override
    public int getItemCount() {
        return iconos.length;
    }

    public class IconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        public IconViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int iconId = iconos[position];
            if (onIconClickListener != null) {
                onIconClickListener.onIconClick(iconId);
            }
        }
    }

    public interface OnIconClickListener {
        void onIconClick(int iconId);
    }
}