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

import java.util.List;

/**
 * Created by blastocode on 8/13/17.
 */

public class OneItemAdapter extends ArrayAdapter<String> {
    public OneItemAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, R.layout.item_one, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.item_one, null);
        }

        String item = getItem(position);

        TextView judulTextView = (TextView) view.findViewById(R.id.text_judul);
        judulTextView.setText(item);

        return view;
    }
}
