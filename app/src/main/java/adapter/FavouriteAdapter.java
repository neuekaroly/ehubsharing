package adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.neuekaroly.ehubsharing.R;

import java.util.List;

import database.ChargerPoint;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>{
    private List<ChargerPoint> favouritesList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, adress, openingHours;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.favourite_list_item_name);
            adress = (TextView) view.findViewById(R.id.favourite_list_item_adress);
            openingHours = (TextView) view.findViewById(R.id.favourite_list_item_openning_hours);
        }
    }

    public FavouriteAdapter(List<ChargerPoint> moviesList) {
        this.favouritesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChargerPoint chargerPoint = favouritesList.get(position);
        holder.name.setText(chargerPoint.getName());
        holder.adress.setText(chargerPoint.getAdress());
        holder.openingHours.setText(chargerPoint.getOpeningHours());

    }

    @Override
    public int getItemCount() {
        return favouritesList.size();
    }
}
