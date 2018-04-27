package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neuekaroly.ehubsharing.R;

import java.util.ArrayList;
import java.util.List;

import database.ChargerPoint;

public class SearchChargerAdapter  extends RecyclerView.Adapter<SearchChargerAdapter.Holderview>{
    private List<ChargerPoint> mChargerPointList;
    private Context context;

    public SearchChargerAdapter(List<ChargerPoint> productlist, Context context) {
        this.mChargerPointList = productlist;
        this.context = context;
    }

    @Override
    public Holderview onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.search_charger_list_item, parent,false);
        return new Holderview(layout);
    }

    @Override
    public void onBindViewHolder(Holderview holder, final int position) {
        ChargerPoint chargerPoint = mChargerPointList.get(position);
        holder.name.setText(chargerPoint.getName());
        holder.adress.setText(chargerPoint.getAdress());
        holder.openingHours.setText(chargerPoint.getOpeningHours());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click on " + mChargerPointList.get(position).getName(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChargerPointList.size();
    }

    public void setfilter(List<ChargerPoint> listitem) {
        mChargerPointList =new ArrayList<>();
        mChargerPointList.addAll(listitem);
        notifyDataSetChanged();
    }

    class Holderview extends RecyclerView.ViewHolder {
        public TextView name, adress, openingHours;

        Holderview(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.search_charger_list_item_name);
            adress = (TextView) view.findViewById(R.id.search_charger_list_item_adress);
            openingHours = (TextView) view.findViewById(R.id.search_charger_list_item_openning_hours);
        }
    }
}