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
    FloatingActionButton me1;
    FloatingActionButton me2;
    TextView me1Text;
    TextView me2Text;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_map, null);
        FloatingActionButton me = v.findViewById(R.id.fabME);
        me1 = v.findViewById(R.id.fabMeFloor1);
        me2 = v.findViewById(R.id.fabMeFloor2);
        map = v.findViewById(R.id.map_image);
        me1Text = v.findViewById(R.id.meFloor1Text);
        me2Text = v.findViewById(R.id.meFloor2Text);

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(me1.getVisibility() == View.VISIBLE){
                    me1.hide();
                    me2.hide();
                    me1Text.setVisibility(View.INVISIBLE);
                    me2Text.setVisibility(View.INVISIBLE);
                }
                else{
                    me1.show();
                    me2.show();
                    me1Text.setVisibility(View.VISIBLE);
                    me2Text.setVisibility(View.VISIBLE);
                }

            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
