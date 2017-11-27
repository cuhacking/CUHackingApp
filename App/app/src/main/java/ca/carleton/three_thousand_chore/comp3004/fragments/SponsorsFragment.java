package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ca.carleton.three_thousand_chore.comp3004.R;

/**
 * Created by elisakazan on 2017-10-09.
 */

public class SponsorsFragment extends Fragment implements View.OnClickListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_sponsors, null);
        ImageView mlh =(ImageView) v.findViewById(R.id.mlh);
        ImageView ccss =(ImageView) v.findViewById(R.id.ccss);
        ImageView ieee =(ImageView) v.findViewById(R.id.ieee);
        ImageView martello =(ImageView) v.findViewById(R.id.martello);

        mlh.setOnClickListener(this);
        ccss.setOnClickListener(this);
        ieee.setOnClickListener(this);
        martello.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        String url;
        switch (v.getId()) {
            case R.id.mlh:
                url = "https://mlh.io/";
                break;
            case R.id.ccss:
                url = "http://ccss.carleton.ca/";
                break;
            case R.id.ieee:
                url = "http://ieeecarleton.ca/";
                break;
            case R.id.martello:
                url = "http://martellotech.com/";
                break;
            default:
                url = "";
                break;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));
        startActivity(intent);

    }

}
