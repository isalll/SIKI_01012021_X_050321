package com.siki.android.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.siki.android.R;
import com.siki.android.models.EWS;

import java.util.List;

/**
 * Created by blastocode on 8/13/17.
 */

public class EWSItemAdapter extends ArrayAdapter<EWS> {
    public EWSItemAdapter(@NonNull Context context, @NonNull List<EWS> objects) {
        super(context, R.layout.item_ews, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.item_ews, null);
        }

        EWS pd = getItem(position);

        TextView judulTextView = (TextView) view.findViewById(R.id.text_judul);
        TextView jumlahDataTextView = (TextView) view.findViewById(R.id.text_jumlah_data);
        TextView lonjakanTerbesarTextView = (TextView) view.findViewById(R.id.text_lonjakan_terbesar);
        TextView lonjakan30TextView = (TextView) view.findViewById(R.id.text_lonjakan30);
        //TextView lonjakan20TextView = (TextView) view.findViewById(R.id.text_lonjakan20);
        //TextView lonjakan15TextView = (TextView) view.findViewById(R.id.text_lonjakan15);

        if(pd != null) {
            judulTextView.setText(pd.judul);
            jumlahDataTextView.setText(pd.jumlahData);
            lonjakanTerbesarTextView.setText(pd.lonjakanTerbesar);
            lonjakan30TextView.setText(pd.lonjakan30);
            //lonjakan20TextView.setText(pd.lonjakan20);
            //lonjakan15TextView.setText(pd.lonjakan15);
        }

        return view;
    }
}
