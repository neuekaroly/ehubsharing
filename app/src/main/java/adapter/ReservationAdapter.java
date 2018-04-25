package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.neuekaroly.ehubsharing.R;

import org.joda.time.DateTime;

import java.util.List;

import database.Reservation;
import helpers.ChargerReservation;

/**
 * Created by neue.karoly on 2018.04.20..
 */

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.MyViewHolder> {

    private List<ChargerReservation> chargerReservationList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView chargerName, chargerAdress, startTime, endTime;

        public MyViewHolder(View view) {
            super(view);
            chargerName = (TextView) view.findViewById(R.id.reservation_list_item_charger_name);
            chargerAdress = (TextView) view.findViewById(R.id.reservation_list_item_charger_adress);
            startTime = (TextView) view.findViewById(R.id.reservation_list_item_start_time);
            endTime = (TextView) view.findViewById(R.id.reservation_list_item_end_time);
        }
    }

    public ReservationAdapter(List<ChargerReservation> chargerReservationList) {
        this.chargerReservationList = chargerReservationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reservation_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    //TODO: DO IT!

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChargerReservation chargerReservation = chargerReservationList.get(position);

        holder.chargerName.setText(chargerReservation.getChargerPoint().getName());
        holder.chargerAdress.setText(chargerReservation.getChargerPoint().getAdress());
        holder.startTime.setText(new DateTime(chargerReservation.getReservation().getStartDate()).toString());
        holder.endTime.setText(new DateTime(chargerReservation.getReservation().getFinishDate()).toString());
    }

    @Override
    public int getItemCount() {
        return chargerReservationList.size();
    }
}