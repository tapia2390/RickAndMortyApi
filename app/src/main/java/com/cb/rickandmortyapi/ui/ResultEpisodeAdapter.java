package com.cb.rickandmortyapi.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cb.rickandmortyapi.Model.clases.Episode;
import com.cb.rickandmortyapi.Model.clases.Modelo;
import com.cb.rickandmortyapi.Model.clases.Results;
import com.cb.rickandmortyapi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ResultEpisodeAdapter extends RecyclerView.Adapter<ResultEpisodeAdapter.ResultsViewHolder> implements Filterable {

    private Context context;
    private List<Episode> nameList;
    private List<Episode> filteredNameList;
    Modelo modelo = Modelo.getInstance();




    public ResultEpisodeAdapter(Context context, List<Episode> nameList) {
        super();
        this.context = context;
        this.nameList = nameList;
        this.filteredNameList = nameList;


    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_episodio, viewGroup, false);
        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String nombre = "" + filteredNameList.get(position).getName();
        String episode = "" + filteredNameList.get(position).getEpisode();




        holder.icon_name.setText("Nombre: " + nombre);
        holder.icon_episode.setText("Nombre: " + episode);


    }

    @Override
    public int getItemCount() {
        return filteredNameList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                    filteredNameList = nameList;
                } else {
                    List<Episode> filteredList = new ArrayList<>();
                    for (Episode value : nameList) {
                        if (value.getName().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(value);
                        }
                        filteredNameList = filteredList;
                    }

                }
                FilterResults results = new FilterResults();
                results.values = filteredNameList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredNameList = (List<Episode>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    class ResultsViewHolder extends RecyclerView.ViewHolder {


        private TextView icon_name;
        private TextView icon_episode;

        ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            icon_name = itemView.findViewById(R.id.icon_name);
            icon_episode = itemView.findViewById(R.id.icon_episode);


        }
    }


}