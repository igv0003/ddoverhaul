package com.example.ddoverhaul.personajeList;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.R;

public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.CreateViewHolder> {

    private Objeto[] objects;
    private Habilidades[] skills;
    private OnObjectClickListener onObjectClickListener;
    private OnSkillClickListener onSkillClickListener;
    Context context;
    boolean switcher;

    public CreateAdapter (Objeto[] obs, Context c, boolean sw) {
        this.objects = obs;
        this.context = c;
        this.switcher = sw;
    }

    public CreateAdapter (Habilidades[] skills, Context c, boolean sw) {
        this.skills = skills;
        this.context = c;
        this.switcher = sw;
    }

    @NonNull
    @Override
    public CreateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        if (switcher) {
            layout = R.layout.objeto_row;
        } else {
            layout = R.layout.habilidad_row;
        }
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout,parent,false);
        return new CreateViewHolder(itemView, switcher);


    }

    @Override
    public void onBindViewHolder (@NonNull CreateViewHolder holder, int position) {
        if (switcher) {
            Objeto object = this.objects[position];
            holder.nameObj.setText(object.getNombre());
            holder.typeObj.setText(object.getTipo());
            int idIcon = this.context.getResources().getIdentifier(object.getIcono(),"drawable", context.getPackageName());
            holder.iconObj.setImageResource(idIcon);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onObjectClickListener != null) {
                        onObjectClickListener.onClick(position, object.getId()+"", object.getTipo());
                    }
                }
            });
        } else {
            Habilidades skill = this.skills[position];
            holder.nameSkill.setText(skill.getNombre());
            holder.costSkill.setText("Coste: "+skill.getCoste()+"");
            holder.dmgSkill.setText("Daño: "+skill.getDanio()+"");
            int idIcon = this.context.getResources().getIdentifier(skill.getIcono(),"drawable", context.getPackageName());
            holder.iconSkill.setImageResource(idIcon);

            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (onSkillClickListener != null) {
                        onSkillClickListener.onClick(position, skill.getId()+"");
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (switcher) {
            return this.objects.length;
        } else {
            return this.skills.length;
        }
    }

    public interface OnObjectClickListener {
        void onClick (int position, String id, String type);
    }

    public void setOnObjectClickListener(OnObjectClickListener onObjectClickListener) {
        this.onObjectClickListener = onObjectClickListener;
    }

    public interface OnSkillClickListener {
        void onClick (int position, String id);
    }

    public void setOnSkillClickListener(OnSkillClickListener onSkillClickListener) {
        this.onSkillClickListener = onSkillClickListener;
    }


    public static class CreateViewHolder extends RecyclerView.ViewHolder {
        public TextView nameObj;
        public TextView typeObj;
        public ImageView iconObj;
        public TextView nameSkill;
        public TextView costSkill;
        public TextView dmgSkill;
        public ImageView iconSkill;

        public CreateViewHolder(View v, boolean switcher) {
            super(v);
            if (switcher) {
                nameObj = v.findViewById(R.id.textView_nameObj);
                typeObj = v.findViewById(R.id.textView_typeObj);
                iconObj = v.findViewById(R.id.imageView_obj);
            } else {
                nameSkill = v.findViewById(R.id.textView_nameSkill);
                costSkill = v.findViewById(R.id.textView_costSkill);
                dmgSkill = v.findViewById(R.id.textView_dmgSkill);
                iconSkill = v.findViewById(R.id.imageView_skill);
            }
        }


    }
}
