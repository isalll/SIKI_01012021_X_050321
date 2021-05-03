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
import com.siki.android.models.RCA;

import java.util.List;

/**
 * Created by blastocode on 8/13/17.
 */

public class RCAItemAdapter extends ArrayAdapter<RCA> {
    public RCAItemAdapter(@NonNull Context context, @NonNull List<RCA> objects) {
        super(context, R.layout.item_rca, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.item_rca, null);
        }

        RCA pd = getItem(position);

        TextView judulTextView = (TextView) view.findViewById(R.id.text_judul);
        TextView jumlahDataTextView = (TextView) view.findViewById(R.id.text_jumlah_data);
        TextView lonjakanTerbesarTextView = (TextView) view.findViewById(R.id.text_lonjakan_terbesar);


        if(pd != null) {
            judulTextView.setText(pd.group);
            jumlahDataTextView.setText(pd.uraian);
            lonjakanTerbesarTextView.setText(pd.jumlah);

        }

        return view;
    }
}
