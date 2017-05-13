package com.bidjidevelops.hd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bidjidevelops.hd.Adapter.AdapterMyTL;
import com.bidjidevelops.hd.Adapter.AdapterTL;
import com.bidjidevelops.hd.Gson.GsonMyTL;
import com.bidjidevelops.hd.Gson.GsonTL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class Myquestion extends Fragment {


    @BindView(R.id.rcSoalmy)
    RecyclerView rcSoalmy;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    Unbinder unbinder;
    ArrayList<muser> data;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    StringRequest stringRequest;
    String Spassword, Semail, Remail, idUser;
    GsonMyTL gsonMyTl;
    RecyclerView rc;
    public Myquestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myquestion, container, false);
        unbinder = ButterKnife.bind(this, view);
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        LinearLayoutManager linearmanager = new LinearLayoutManager(getActivity());
        rcSoalmy.setLayoutManager(linearmanager);
        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetails();
        Semail = user.get(SessionManager.kunci_email);
        idUser = user.get(SessionManager.idusers);
        data = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        Spassword = user.get(SessionManager.kunci_password);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
                getSoal();
            }
//
        });
        getSoal();
        return view;
    }

    private void getSoal() {
        data.clear();
        String url = Helper.BASE_URL + "getmysoal.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    gsonMyTl = gson.fromJson(response, GsonMyTL.class);
                    AdapterMyTL adapter = new AdapterMyTL(getActivity(),gsonMyTl.dataSoal);
                    rcSoalmy.setAdapter(adapter);
                }
                catch (Exception i){
                    Toast.makeText(getActivity(), "sad", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Maaf Internet Lambat", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramuserlogin = new HashMap<>();
                paramuserlogin.put("iduser", idUser);
                return paramuserlogin;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
