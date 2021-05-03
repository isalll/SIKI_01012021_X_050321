package com.siki.android.adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.siki.android.R;
import com.siki.android.models.HSLINKHs;

import java.util.List;

/**
 * Created by blastocode on 8/13/17.
 */

public class HSLinkHsItemAdapter extends ArrayAdapter<HSLINKHs> {
    public HSLinkHsItemAdapter(@NonNull Context context, @NonNull List<HSLINKHs> objects) {
        super(context, R.layout.item_hslinkhs, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.item_hslinkhs, null);
        }

        HSLINKHs pd = getItem(position);

        TextView kodeHsTextView = (TextView) view.findViewById(R.id.text_kode_hs);
        TextView deskripsiTextView = (TextView) view.findViewById(R.id.text_deskripsi);
        TextView lonjakanTextView = (TextView) view.findViewById(R.id.text_lonjakan);
        TextView becTextView = (TextView) view.findViewById(R.id.text_bec);
        TextView kelompokTextView = (TextView) view.findViewById(R.id.text_kelompok);

        if(pd != null) {
            kodeHsTextView.setText(pd.kodeHS);
            deskripsiTextView.setText(pd.deskripsi);
            lonjakanTextView.setText(pd.lonjakan);
            becTextView.setText(pd.berat);
            kelompokTextView.setText(pd.kelompok);
        }

        try {
            float lonjakan = Float.parseFloat(pd.lonjakan);

            if(lonjakan > 30) {
                lonjakanTextView.setTextColor(Color.parseColor("#ff0000"));
            }
            else if(lonjakan <= 30 && lonjakan > 15) {
                lonjakanTextView.setTextColor(Color.parseColor("#fff600"));
            }
            else {
                lonjakanTextView.setTextColor(Color.parseColor("#0ac805"));
            }
        }
        catch (Exception e) {

        }

        return view;
    }
}
