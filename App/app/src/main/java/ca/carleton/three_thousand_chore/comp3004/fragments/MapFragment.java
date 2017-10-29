package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import ca.carleton.three_thousand_chore.comp3004.R;

/**
 * Created by elisakazan on 2017-10-09.
 */

public class MapFragment extends Fragment implements View.OnTouchListener{
    FrameLayout carletonMapLayout;
    FrameLayout meMapLayout;
    FloatingActionButton fab;
    FloatingActionButton me;
    FloatingActionButton cu;
    TextView cuText;
    TextView meText;
    TextView toolbarText;
    ImageView map;
    ImageView map2;
    ImageView map3;

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // 3 states for imageview
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;


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
        meText = v.findViewById(R.id.meText);
        cuText = v.findViewById(R.id.cuText);
        toolbarText = v.findViewById(R.id.toolbarText);
        map = v.findViewById(R.id.mapImage);
        map2 = v.findViewById(R.id.mapImage2);
        map3 = v.findViewById(R.id.mapImage3);
        carletonMapLayout = v.findViewById(R.id.carletonMapLayout);
        meMapLayout = v.findViewById(R.id.meMapLayout);

        map.setOnTouchListener(this);
        map2.setOnTouchListener(this);
        map3.setOnTouchListener(this);

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
                toolbarText.setText("Carleton Campus");
                cu.hide();
                me.hide();
                carletonMapLayout.setVisibility(View.VISIBLE);
                meMapLayout.setVisibility(View.GONE);
                cuText.setVisibility(View.INVISIBLE);
                meText.setVisibility(View.INVISIBLE);
            }
        });

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarText.setText("Mackenzie");
                carletonMapLayout.setVisibility(View.GONE);
                meMapLayout.setVisibility(View.VISIBLE);
                cu.hide();
                me.hide();
                cuText.setVisibility(View.INVISIBLE);
                meText.setVisibility(View.INVISIBLE);
            }
        });

        return v;
    }


    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x,
                            event.getY() - start.y);
                }
                else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix);
        return true; // indicate event was handled
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
