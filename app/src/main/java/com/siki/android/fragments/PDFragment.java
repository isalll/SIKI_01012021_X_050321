package com.siki.android.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.siki.android.PdActivity;
import com.siki.android.R;
import com.siki.android.adapters.OneItemAdapter;

import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PDFragment extends Fragment implements AdapterView.OnItemClickListener {


    public PDFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pd, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<String> ditjen = Arrays.asList(new String[]{
                "Industri Argo (IA)",
                "Industri Kimia, Tekstil dan Aneka Industri (IKTA)",
                "Logam, Mesin, Alat Transportasi dan Elektronika (ILMATE)"
        });

        OneItemAdapter adapter = new OneItemAdapter(getActivity(), ditjen);
        ListView listView = (ListView) getView().findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        TextView titleTextView = (TextView) getView().findViewById(R.id.tv_title);
        titleTextView.setText("Peringatan Dini");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), PdActivity.class);

        switch (i) {
            case 0:
                intent.putExtra(PdActivity.KEY_DITJEN, "Ditjen IA");
                intent.putExtra(PdActivity.KEY_TITLE, "Industri Argo (IA)");
                break;
            case 1:
                intent.putExtra(PdActivity.KEY_DITJEN, "Ditjen IKTA");
                intent.putExtra(PdActivity.KEY_TITLE, "Industri Kimia, Tekstil dan Aneka Industri (IKTA)");
                break;
            case 2:
                intent.putExtra(PdActivity.KEY_DITJEN, "Ditjen ILMATE");
                intent.putExtra(PdActivity.KEY_TITLE, "Logam, Mesin, Alat Transportasi dan Elektronika (ILMATE)");
                break;
        }

        startActivity(intent);
    }

    public void setNewPage(Fragment fragment, int pageIndex) {
        getFragmentManager().beginTransaction()

                .replace(R.id.fragment_lonjakan, fragment, "currentFragment")
                .commit();
    }
}
