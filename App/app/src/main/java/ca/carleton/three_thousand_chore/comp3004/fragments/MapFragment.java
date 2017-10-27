package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ca.carleton.three_thousand_chore.comp3004.R;

/**
 * Created by elisakazan on 2017-10-09.
 */

public class MapFragment extends Fragment {
    ImageView map;
    ImageView map2;
    FloatingActionButton fab;
    FloatingActionButton me;
    FloatingActionButton cu;
    TextView cuText;
    TextView meText;
    TextView toolbarText;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_map, null);
        fab = v.findViewById(R.id.fab);
        me = v.findViewById(R.id.fabMe);
        cu = v.findViewById(R.id.fabCU);
        map = v.findViewById(R.id.map_image);
        map2 = v.findViewById(R.id.map_image2);
        meText = v.findViewById(R.id.meText);
        cuText = v.findViewById(R.id.cuText);
        toolbarText = v.findViewById(R.id.toolbarText);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cuText.getVisibility() == View.INVISIBLE){
                    cu.show();
                    me.show();
                    cuText.setVisibility(View.VISIBLE);
                    meText.setVisibility(View.VISIBLE);
                }
                else{
                    cu.hide();
                    me.hide();
                    cuText.setVisibility(View.INVISIBLE);
                    meText.setVisibility(View.INVISIBLE);
                }

            }
        });

        cu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setImageResource(R.mipmap.exterior_map);
                toolbarText.setText("Carleton Campus");
                cu.hide();
                me.hide();
                map2.setVisibility(View.INVISIBLE);
                cuText.setVisibility(View.INVISIBLE);
                meText.setVisibility(View.INVISIBLE);
            }
        });

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarText.setText("Mackenzie");
                map.setImageResource(R.mipmap.me3);
                map2.setImageResource(R.mipmap.me4);
                map2.setVisibility(View.VISIBLE);
                cu.hide();
                me.hide();
                cuText.setVisibility(View.INVISIBLE);
                meText.setVisibility(View.INVISIBLE);
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
