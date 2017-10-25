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
    FloatingActionButton me;
    FloatingActionButton me3;
    FloatingActionButton me4;
    FloatingActionButton cu;
    TextView cuText;
    TextView me3Text;
    TextView me4Text;
    TextView toolbarText;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_map, null);
        me = v.findViewById(R.id.fabME);
        me3 = v.findViewById(R.id.fabMeFloor3);
        me4 = v.findViewById(R.id.fabMeFloor4);
        cu = v.findViewById(R.id.fabCU);
        map = v.findViewById(R.id.map_image);
        cuText = v.findViewById(R.id.cuText);
        me3Text = v.findViewById(R.id.meFloor3Text);
        me4Text = v.findViewById(R.id.meFloor4Text);
        toolbarText = v.findViewById(R.id.toolbarText);

        cu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cu.hide();
                cuText.setVisibility(View.INVISIBLE);
                map.setImageResource(R.mipmap.exterior_map);
                toolbarText.setText("Carleton Campus");
            }
        });

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(me3.getVisibility() == View.VISIBLE){
                    me3.hide();
                    me4.hide();
                    me3Text.setVisibility(View.INVISIBLE);
                    me4Text.setVisibility(View.INVISIBLE);
                }
                else{
                    me3.show();
                    me4.show();
                    me3Text.setVisibility(View.VISIBLE);
                    me4Text.setVisibility(View.VISIBLE);
                }
            }
        });

        me4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setImageResource(R.mipmap.me4);
                me3.hide();
                me4.hide();
                cu.show();
                cuText.setVisibility(View.VISIBLE);
                me3Text.setVisibility(View.INVISIBLE);
                me4Text.setVisibility(View.INVISIBLE);
                toolbarText.setText("Mackenzie 4th Floor");
            }
        });

        me3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setImageResource(R.mipmap.me3);
                me3.hide();
                me4.hide();
                cu.show();
                cuText.setVisibility(View.VISIBLE);
                me3Text.setVisibility(View.INVISIBLE);
                me4Text.setVisibility(View.INVISIBLE);
                toolbarText.setText("Mackenzie 3rd Floor");
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
