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
import android.widget.ImageView;
import android.widget.TextView;

public class LinksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView fbImageView =(ImageView) findViewById(R.id.fbImage);
        ImageView slackImageView =(ImageView) findViewById(R.id.slackImage);
        ImageView twitterImageView =(ImageView) findViewById(R.id.twitterImage);
        ImageView snapImageView =(ImageView) findViewById(R.id.snapImage);
        TextView fbLink =(TextView) findViewById(R.id.fbLinkText);
        TextView slackLink =(TextView) findViewById(R.id.slackLinkText);
        TextView twitterLink =(TextView) findViewById(R.id.twitterLinkText);
        TextView snapLink =(TextView) findViewById(R.id.snapLinkText);

        SpannableString content = new SpannableString("cuhacking");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        snapLink.setText(content);

        //On click listeners for image views and text
        fbImageView.setOnClickListener(fbRedirect);
        twitterImageView.setOnClickListener(twitterRedirect);
        snapImageView.setOnClickListener(snapRedirect);

        fbLink.setOnClickListener(fbRedirect);
        twitterLink.setOnClickListener(twitterRedirect);
        snapLink.setOnClickListener(snapRedirect);

        slackLink.setMovementMethod(LinkMovementMethod.getInstance());//TODO redirect to app once channel created
        slackImageView.setOnClickListener(slackRedirect);
    }


    private View.OnClickListener fbRedirect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                LinksActivity.this.getPackageManager().getPackageInfo("com.facebook.katana", 0); //Checks if FB is installed.
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("fb://page/1446989328648692"));
                startActivity(intent);
            }
            catch (Exception e) {
                //FB is not installed
                Intent intent =  new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/CUHacking/")); //catches and opens a url to the desired page
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener twitterRedirect = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
            Uri.parse("https://twitter.com/cuhacking"));
            startActivity(intent);
        }
     };

    private View.OnClickListener snapRedirect = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");//TODO redirect to specific profile
            intent.setPackage("com.snapchat.android");
            startActivity(Intent.createChooser(intent, "Open Snapchat"));
        }
    };

    private View.OnClickListener slackRedirect = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://cuhacking2017.slack.com/"));//TODO change to app instead of browser
            startActivity(intent);

        }
    };
    

}
