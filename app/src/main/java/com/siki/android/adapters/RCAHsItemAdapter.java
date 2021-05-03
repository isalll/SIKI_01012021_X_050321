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
//import com.iris.android.models.PdHs;
import com.siki.android.models.RCAHs;

import java.util.List;

/**
 * Created by blastocode on 8/13/17.
 */

public class RCAHsItemAdapter extends ArrayAdapter<RCAHs> {
    public RCAHsItemAdapter(@NonNull Context context, @NonNull List<RCAHs> objects) {
        super(context, R.layout.item_pdhs, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.item_rcahs, null);
        }

        RCAHs pd = getItem(position);

        TextView kodeHsTextView = (TextView) view.findViewById(R.id.text_kode_hs);
        TextView deskripsiTextView = (TextView) view.findViewById(R.id.text_deskripsi);
        TextView lonjakanTextView = (TextView) view.findViewById(R.id.text_lonjakan);
        TextView becTextView = (TextView) view.findViewById(R.id.text_bec);
        TextView kelompokTextView = (TextView) view.findViewById(R.id.text_kelompok);

        if(pd != null) {
            kodeHsTextView.setText(pd.kodeHS);
            deskripsiTextView.setText(pd.uraian);
            lonjakanTextView.setText(pd.rca);
            becTextView.setText(pd.rsca);
            kelompokTextView.setText(pd.tbi);
        }
/*
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
*/
        return view;
    }
}
