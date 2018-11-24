package ride.happyy.driver.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ride.happyy.driver.R;
import ride.happyy.driver.model.Driver;


public class DriverListAdapter extends ArrayAdapter<Driver> {
    Context mContext;
    int mResource;
    public DriverListAdapter(Context context, int resource, ArrayList<Driver> object) {
        super(context, resource, object);
        mContext =context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int itm=1;
        String itms = getItem(position).getSlNumber();
       String name = getItem(position).getName();
        String totalEarn = getItem(position).getTotalEarning();


       // Drawable drawable =


        Driver driver = new Driver(name,totalEarn);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource,parent,false);
        TextView textViewName = convertView.findViewById(R.id.ernerNameTv);
        TextView textViewEarn = convertView.findViewById(R.id.earningTv);
        TextView sltv = convertView.findViewById(R.id.slistTv);
        sltv.setText(itms);
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

        textViewName.setText(name);
        textViewEarn.setText(totalEarn);
        itm++;
        return convertView;



    }


}
