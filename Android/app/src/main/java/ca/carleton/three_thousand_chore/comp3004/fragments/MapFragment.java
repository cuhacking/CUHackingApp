package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import ca.carleton.three_thousand_chore.comp3004.R;

/**
 * Created by elisakazan on 2017-10-09.
 */

public class MapFragment extends Fragment implements View.OnTouchListener{
    FloatingActionButton fab;
    FloatingActionButton me;
    FloatingActionButton cu;
    TextView cuText;
    TextView meText;
    TextView toolbarText;
    ImageView map;

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // Imageview States
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Zoom Information
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
        map.setOnTouchListener(this);

        ViewTreeObserver vto = v.getViewTreeObserver();
        if(vto.isAlive()) {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
            {
                @Override
                public void onGlobalLayout()
                {
                    resetZoom(map);
                }
            });
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cuText.getVisibility() == View.INVISIBLE){
                    displayMapButtons(View.VISIBLE);
                }
                else {
                    displayMapButtons(View.INVISIBLE);
                }
            }
        });

        cu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectedMap(R.string.carleton_campus, R.mipmap.carleton_outdoor_map);
            }
        });

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectedMap(R.string.me_building, R.mipmap.me_building);
            }
        });

        return v;
    }

    private void displayMapButtons(int visible) {
        if (visible == View.VISIBLE) {
            cu.show();
            me.show();
            cuText.setVisibility(View.VISIBLE);
            meText.setVisibility(View.VISIBLE);
        }
        else {
            cu.hide();
            me.hide();
            cuText.setVisibility(View.INVISIBLE);
            meText.setVisibility(View.INVISIBLE);
        }
    }

    private void showSelectedMap(int buildingName, int mipmap) {
        toolbarText.setText(buildingName);
        displayMapButtons(View.INVISIBLE);
        map.setImageResource(mipmap);
        resetZoom(map);
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
                    matrix.postTranslate(event.getX() - start.x,event.getY() - start.y);
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
        limitDrag(matrix, map);
        view.setImageMatrix(matrix);
        return true;
    }

    public void resetZoom(ImageView v) {
        matrix = centrecropMatrix();
        savedMatrix = centrecropMatrix();
        mode = NONE;
        oldDist = 1f;
        v.setImageMatrix(matrix);
        v.invalidate();
    }

    @NonNull
    private Boolean isLandscapeImage(ImageView iv){
        return iv.getDrawable().getIntrinsicWidth() > iv.getDrawable().getIntrinsicHeight();
    }

    private void limitDrag(Matrix m, ImageView iv) {
        Rect boundingRect = iv.getDrawable().getBounds();

        float[] values = new float[9];
        m.getValues(values);

        float viewWidth = map.getMeasuredWidth();
        float viewHeight = map.getMeasuredHeight();

        float drawableWidth = boundingRect.width();
        float drawableHeight = boundingRect.height();

        float xTranslation = values[Matrix.MTRANS_X];
        float yTranslation = values[Matrix.MTRANS_Y];
        float scale = values[Matrix.MSCALE_X];

        float scaledWidth = drawableWidth * scale;
        float horWidth = -Math.abs(scaledWidth - viewWidth);

        float scaledHeight = drawableHeight * scale;
        float vertHeight = -Math.abs(scaledHeight - viewHeight);

        if (xTranslation > 0) {
            xTranslation = 0;
        }
        if (yTranslation > 0) {
            yTranslation = 0;
        }
        if (xTranslation < horWidth) {
            xTranslation = horWidth;
        }
        if (yTranslation < vertHeight) {
            yTranslation = vertHeight;
        }

        values[Matrix.MTRANS_X] = xTranslation;
        values[Matrix.MTRANS_Y] = yTranslation;

        m.setValues(values);
    }

    private Matrix centrecropMatrix() {
        float drawableWidth = (float)map.getDrawable().getIntrinsicWidth();
        float drawableHeight = (float)map.getDrawable().getIntrinsicHeight();
        float viewWidth = (float) map.getMeasuredWidth();
        float viewHeight = (float) map.getMeasuredHeight();

        // CenterCrop Scale
        float xScale = viewWidth / drawableWidth;
        float yScale = viewHeight / drawableHeight;
        float scale = (xScale >= yScale) ? xScale : yScale;

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        if (scale == yScale) {
            float centerOffset = (drawableWidth - viewWidth)/2;
            matrix.postTranslate(centerOffset, 0);
        }

        return matrix;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}