package ride.happyy.driver.adapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ride.happyy.driver.R;
import ride.happyy.driver.model.Driver;

public class DriverListAdapterTrip extends ArrayAdapter<Driver>{

    Context mContext;
    int mResource;
    public DriverListAdapterTrip(Context context, int resource, ArrayList<Driver> object) {
        super(context, resource, object);
        mContext =context;
        mResource = resource;
    }
    private int lastPosition = -1;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String itms = getItem(position).getSlNumber();
        String name = getItem(position).getName();
        String totalTrips = getItem(position).getTotalTrips();

        Driver driver = new Driver(name,totalTrips);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource,parent,false);
        //slistTv
        TextView sltv = convertView.findViewById(R.id.slistTv);
        TextView textViewName = convertView.findViewById(R.id.ernerNameTv);
        TextView textViewEarn = convertView.findViewById(R.id.tripTv);
/*
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        convertView.startAnimation(animation);
        lastPosition=position;
        */

        if(itms.equals("1")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                sltv.setBackgroundResource(R.drawable.circle_green);
            }
        }

        if(itms.equals("2")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                sltv.setBackgroundResource(R.drawable.circle_blue);
            }
        }

        if(itms.equals("3")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                sltv.setBackgroundResource(R.drawable.circle_gray);
            }
        }

        sltv.setText(itms);
        textViewName.setText(name);
        textViewEarn.setText(totalTrips);

        return convertView;



    }
}
