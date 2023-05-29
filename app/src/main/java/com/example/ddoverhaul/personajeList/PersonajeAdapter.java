package com.example.ddoverhaul.personajeList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.R;


public class PersonajeAdapter extends RecyclerView.Adapter<PersonajeAdapter.PersonajeViewHolder> {
    // Guarda la lista de Personajes
    private Personaje[] characters;
    private OnClickListener onClickListener;
    private Context context;

    public PersonajeAdapter(Personaje[] chars, Context context) {
        this.characters = chars;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.personaje_row, parent, false);
        return new PersonajeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeViewHolder holder, int position) {
        Personaje personaje = this.characters[position];
        holder.nameTextView.setText(personaje.getNombre());
        holder.levelTextView.setText("Lv."+String.valueOf(personaje.getNivel()));
        holder.raceTextView.setText(personaje.getRaza());
        holder.classTextView.setText(personaje.getClase());

        int idIcon = this.context.getResources().getIdentifier(personaje.getImagen(),"drawable", context.getPackageName());
        holder.iconImageView.setImageResource(idIcon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, personaje.getId()+"");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.characters.length;
    }

    public void setOnClickListener (OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick (int position, String id);
    }


    public static class PersonajeViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView levelTextView;
        public TextView raceTextView;
        public TextView classTextView;
        public ImageView iconImageView;

        public PersonajeViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.textView_namePJ);
            levelTextView = view.findViewById(R.id.textView_levelPJ);
            raceTextView = view.findViewById(R.id.textView_racePJ);
            classTextView = view.findViewById(R.id.textView_classPJ);
            iconImageView = view.findViewById(R.id.imageView_pj);
        }
    }
}
