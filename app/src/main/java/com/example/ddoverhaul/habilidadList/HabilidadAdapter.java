package com.example.ddoverhaul.habilidadList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.R;

public class HabilidadAdapter extends RecyclerView.Adapter<HabilidadAdapter.HabilidadViewHolder> {
    // Guarda la lista de Habilidades
    private Habilidades[] skills;
    OnClickListener onClickListener;
    Context context;

    public HabilidadAdapter (Habilidades[] skills, Context context) {
        this.skills = skills;
        this.context = context;
    }

    @NonNull
    @Override
    public HabilidadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habilidad_row, parent, false);
        return new HabilidadViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HabilidadViewHolder holder, int position) {
        Habilidades skill = this.skills[position];
        holder.nameTextView.setText(skill.getNombre());
        holder.costTextView.setText("Coste: "+skill.getCoste()+"");
        holder.dmgTextView.setText("Daño: "+skill.getDanio()+"");
        int idIcon = this.context.getResources().getIdentifier(skill.getIcono(),"drawable", context.getPackageName());
        holder.iconImageView.setImageResource(idIcon);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, skill.getId()+"");
                }
            }
        });

    }


    @Override
    public int getItemCount() { return this.skills.length; }


    public void setOnClickListener (OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick (int position, String id);
    }


    public static class HabilidadViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView costTextView;
        public TextView dmgTextView;
        public ImageView iconImageView;


        public HabilidadViewHolder(View v) {
            super(v);
            nameTextView = v.findViewById(R.id.textView_nameSkill);
            costTextView = v.findViewById(R.id.textView_costSkill);
            dmgTextView = v.findViewById(R.id.textView_dmgSkill);
            iconImageView = v.findViewById(R.id.imageView_skill);
        }


    }


}
