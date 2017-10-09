package ca.carleton.three_thousand_chore.comp3004;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class SponsorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView martelloImageView = (ImageView) findViewById(R.id.martello);

        martelloImageView.setOnClickListener(martelloClickListener);

    }


    private View.OnClickListener martelloClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            //TODO get url from backend
//            Intent intent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("url"));
//            startActivity(intent);
        }
    };
}
