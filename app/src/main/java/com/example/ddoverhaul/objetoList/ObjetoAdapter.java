package com.example.ddoverhaul.objetoList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.R;


public class ObjetoAdapter extends RecyclerView.Adapter<ObjetoAdapter.ObjetoViewHolder> {
    // Guarda la lista de Objetos
    private Objeto[] objects;
    private OnClickListener onClickListener;
    Context context;

    public ObjetoAdapter(Objeto[] objs, Context context) {
        this.objects = objs;
        this.context = context;
    }


    @NonNull
    @Override
    public ObjetoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.objeto_row, parent, false);
        return new ObjetoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder (@NonNull ObjetoViewHolder holder, int position) {
        Objeto object = this.objects[position];
        holder.nameTextView.setText(object.getNombre());
        holder.typeTextView.setText(object.getTipo());
        int idIcon = this.context.getResources().getIdentifier(object.getIcono(),"drawable", context.getPackageName());
        holder.iconImageView.setImageResource(idIcon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, object.getId()+"", object.getTipo());
                }
            }
        });
    }


    @Override
    public int getItemCount() { return this.objects.length; }

    public void setOnClickListener (OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick (int position, String id, String type);
    }


    public static class ObjetoViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView typeTextView;
        public ImageView iconImageView;

        public ObjetoViewHolder(View v) {
            super(v);
            nameTextView = v.findViewById(R.id.textView_nameObj);
            typeTextView = v.findViewById(R.id.textView_typeObj);
            iconImageView = v.findViewById(R.id.imageView_obj);
        }


    }

}
