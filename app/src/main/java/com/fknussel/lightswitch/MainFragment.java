package com.fknussel.lightswitch;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fknussel.lightswitch.networking.RaspiClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainFragment extends Fragment {

    Button toggle_lights,pin_test;
    ImageView lightbulb;
    IMainActivity mActivity;
    Boolean on;
    RaspiClient.Raspi raspi = RaspiClient.getRaspiInterface();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mActivity = (IMainActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement iMainActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        toggle_lights = (Button) rootView.findViewById(R.id.toggle_lights);

        pin_test =(Button) rootView.findViewById(R.id.pin_test);

        lightbulb = (ImageView) rootView.findViewById(R.id.lightbulb);

        toggle_lights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                raspi.toggleLights(new Callback<RaspiClient.Lights>() {
                    @Override
                    public void success(RaspiClient.Lights lights, Response response) {
                        setStatus(lights.on());
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }
        });


        pin_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                raspi.pinTest(new Callback<RaspiClient.Lights>() {
                    @Override
                    public void success(RaspiClient.Lights lights, Response response) {
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }
        });

        raspi.getStatus(new Callback<RaspiClient.Lights>() {
            @Override
            public void success(RaspiClient.Lights status, Response response) {
                setStatus(status.on());
                Log.e("1st time SUCCESS", status.on().toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("1st time ERROR", error.toString());
            }
        });

        return rootView;
    }


    private void setStatus(Boolean status) {
        if (status) {
            toggle_lights.setText(R.string.turn_lights_off);
            toggle_lights.setBackgroundColor(getResources().getColor(R.color.red));
            lightbulb.setAlpha(1.0f);
            on = true;
            Toast.makeText(getActivity(), R.string.light_are_on, Toast.LENGTH_SHORT).show();
        } else {
            toggle_lights.setText(R.string.turn_lights_on);
            toggle_lights.setBackgroundColor(getResources().getColor(R.color.green));
            lightbulb.setAlpha(0.4f);
            on = false;
            Toast.makeText(getActivity(), R.string.lights_are_off, Toast.LENGTH_SHORT).show();
        }
    }
}