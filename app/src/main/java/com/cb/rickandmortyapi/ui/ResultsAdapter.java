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
import com.cb.rickandmortyapi.Model.clases.Modelo;
import com.cb.rickandmortyapi.Model.clases.Results;
import com.cb.rickandmortyapi.R;
import com.cb.rickandmortyapi.SplashScreen;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> implements Filterable {

    private Context context;
    private List<Results> nameList;
    private List<Results> filteredNameList;
    Modelo modelo = Modelo.getInstance();




    public ResultsAdapter(Context context, List<Results> nameList) {
        super();
        this.context = context;
        this.nameList = nameList;
        this.filteredNameList = nameList;


    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false);
        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String imagen = "" + filteredNameList.get(position).getImage();
        String nombre = "" + filteredNameList.get(position).getName();
        String status = "" + filteredNameList.get(position).getStatus();
        String especies = "" + filteredNameList.get(position).getSpecies();
        String lastLocation = "" + filteredNameList.get(position).getLocation().getName();
        String origin = "" + filteredNameList.get(position).getOrigin().getName();


        Picasso.with(context)
                .load(imagen)
                .placeholder(R.drawable.rick_nad_morty_background)
                .error(R.drawable.rick_nad_morty_background)
                .into(holder.rickAndMortyImage);


        holder.nameTextView.setText("Nombre: " + nombre);
        holder.statusTextView.setText("" + status);
        holder.speciesTextView.setText("" + especies);
        holder.lastLocationTextView.setText("Ubicaión: " + lastLocation);
        holder.originTextView.setText("Origen: " + origin);

        if (status.equals("unknown")) {
            holder.statusImage.setBackgroundResource(R.drawable.ic_baseline_gray);
        } else {
            holder.statusImage.setBackgroundResource(R.drawable.ic_baseline_brightness);
        }


        holder.card_view_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                modelo.results =filteredNameList.get(position);
                Log.v("dta...." , filteredNameList.get(position).toString());
                Intent intent = new Intent(context, EpisodioActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });
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
                    List<Results> filteredList = new ArrayList<>();
                    for (Results value : nameList) {
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
                filteredNameList = (List<Results>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    class ResultsViewHolder extends RecyclerView.ViewHolder {


        private ImageView rickAndMortyImage;
        private TextView nameTextView;
        private TextView statusTextView;
        private TextView speciesTextView;
        private TextView lastLocationTextView;
        private TextView originTextView;
        private ImageView statusImage;
        private CardView card_view_list;

        ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            rickAndMortyImage = itemView.findViewById(R.id.rickAndMortyImage);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            speciesTextView = itemView.findViewById(R.id.speciesTextView);
            lastLocationTextView = itemView.findViewById(R.id.lastLocationTextView);
            originTextView = itemView.findViewById(R.id.originTextView);
            statusImage = itemView.findViewById(R.id.statusImage);
            card_view_list = itemView.findViewById(R.id.card_view_list);


        }
    }


}