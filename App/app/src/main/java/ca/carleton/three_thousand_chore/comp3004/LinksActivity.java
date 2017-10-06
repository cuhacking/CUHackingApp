package ca.carleton.three_thousand_chore.comp3004;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class LinksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView fbLink =(TextView) findViewById(R.id.fbLinkText);
        TextView slackLink =(TextView) findViewById(R.id.slackLinkText);
        TextView twitterLink =(TextView) findViewById(R.id.twitterLinkText);
        TextView snapLink =(TextView) findViewById(R.id.snapLinkText);

        SpannableString content = new SpannableString("cuhacking");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        snapLink.setText(content);


        fbLink.setMovementMethod(LinkMovementMethod.getInstance());
        slackLink.setMovementMethod(LinkMovementMethod.getInstance());
        twitterLink.setMovementMethod(LinkMovementMethod.getInstance());

        /*
        TODO test with device
        fbLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent facebookIntent = getOpenFacebookIntent(LinksActivity.this);
                startActivity(facebookIntent);
            }
        });
        */

        snapLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.setPackage("com.snapchat.android");
                startActivity(Intent.createChooser(intent, "Open Snapchat"));
            }
        });

    }


    public static Intent getOpenFacebookIntent(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://profile/1446989328648692")); //Trys to make intent with FB's URI
        }
        catch (Exception e) {
            //FB is not installed
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/CUHacking/")); //catches and opens a url to the desired page
        }
    }
}
