package com.hp.pav.demojune6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Pav on 6/13/2017.
 */

public class DrawerMapFragmet extends Fragment implements OnMapReadyCallback {

    EditText search_txt;
    TextView message;
    Button search, satellite, normal, terrain, hybrid;
    GoogleMap gmap;
    RequestQueue rq;

    String url = "http://maps.googleapis.com/maps/api/geocode/json?address=";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mapfragmentlayout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search_txt = (EditText)view.findViewById(R.id.search_txt);
        search = (Button)view.findViewById(R.id.search_b);
        satellite = (Button)view.findViewById(R.id.satelite);
        normal = (Button)view.findViewById(R.id.normal );
        terrain = (Button)view.findViewById(R.id.terrain);
        hybrid = (Button)view.findViewById(R.id.hybrid);
        rq = Volley.newRequestQueue(getActivity());

        message = (TextView)view.findViewById(R.id.message);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loc = search_txt.getText().toString();
                url = url.concat(loc);

                Toast.makeText(getActivity(), " Search button is clicked",Toast.LENGTH_SHORT).show();

                StringRequest srq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getActivity(), " response parsing",Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject response_obj = new JSONObject(response);
                            JSONArray results = response_obj.getJSONArray("results");
                            JSONObject result_obj = results.getJSONObject(0);

                            JSONObject geometry = result_obj.getJSONObject("geometry");

                            JSONObject location = geometry.getJSONObject("location");
                            double lat = location.getDouble("lat");
                            double lng = location.getDouble("lng");

                            message.setText(message.getText()+". Result: lat="+lat+" lng="+lng);
                            Toast.makeText(getActivity(), " Latitude: "+lat+"  Longitude "+lng, Toast.LENGTH_LONG).show();
                            gmap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));

                        } catch (Exception e) {
                            message.setText(" JSON parsing error");
                            e.printStackTrace();
                            Toast.makeText(getActivity(), " JSON parsing error", Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity()," Volley Error. String request failed.", Toast.LENGTH_SHORT);
                        message.setText(" Volley Error");
                    }
                });
                rq.add(srq);

            }
        });

    satellite.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gmap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
    });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        gmap = googleMap;
        gmap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(35.503,55.2315)));

    }
}
