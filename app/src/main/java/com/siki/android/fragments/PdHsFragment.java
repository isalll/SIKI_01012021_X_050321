package com.siki.android.fragments;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.siki.android.R;
import com.siki.android.adapters.PdHsItemAdapter;
import com.siki.android.models.PdHs;
import com.siki.android.utils.ConnectivityUtil;
import com.siki.android.utils.PopupUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PdHsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private PdHsItemAdapter mAdapter;
    private List<PdHs> pdHsList = new ArrayList<>();
    private OkHttpClient mOkHttpClient = new OkHttpClient();

    public PdHsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pd_hs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new PdHsItemAdapter(getActivity(), pdHsList);
        ListView listView = (ListView) getView().findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

        if(ConnectivityUtil.isConnected(getActivity())) {
            loadData();
        }
        else {
            PopupUtil.showMsg(getActivity(), "Can't load data, no internet connection", PopupUtil.SHORT);
        }

    }

    private void loadData() {
        PopupUtil.showLoading(getActivity(), "", "Loading data ...");

        String pilihan = "";
        String tahun1 = "";
        String tahun2 = "";
        String bulan1 = "";
        String bulan2 = "";
        String ditjen = "";
        String url = null;

        try {
            url = "http://iris.kemenperin.go.id/android/"  + "get_data2.php?" +
                    "pilihan=" + pilihan +
                    "&tahun1=" + tahun1 +
                    "&tahun2=" + tahun2 +
                    "&bulan1=" + bulan1 +
                    "&bulan2=" + bulan2 +
                    "ditjen=" + URLEncoder.encode(ditjen, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(url == null) {
            return;
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();

                String body = response.body().string();

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
