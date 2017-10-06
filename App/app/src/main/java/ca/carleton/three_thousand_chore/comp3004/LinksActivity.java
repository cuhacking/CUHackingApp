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
        snapLink.setOnClickListener(snapRedirect);//TODO Test with device

        snapImageView.setOnClickListener(snapRedirect);
        /*
        TODO - test with device and remove below link method
        fbImageView.setOnClickListener(fbRedirect);
        twitterImageView.setOnClickListener(twitterRedirect);
        fbLink.setOnClickListener(fbRedirect);
        */
        fbLink.setMovementMethod(LinkMovementMethod.getInstance());
        slackLink.setMovementMethod(LinkMovementMethod.getInstance());
        twitterLink.setMovementMethod(LinkMovementMethod.getInstance());

    }

    /*
    TODO
    private View.OnClickListener fbRedirect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent facebookIntent = getOpenFacebookIntent(LinksActivity.this);
            startActivity(facebookIntent);
        }
    });


    private View.OnClickListener twitterRedirect = new View.OnClickListener() {
        public void onClick(View v) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("twitter://user?screen_name=826509476278702083"));
                startActivity(intent);
            }
            catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://twitter.com/cuhacking")));
            }
        }
     };
    */

    private View.OnClickListener snapRedirect = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.setPackage("com.snapchat.android");
            startActivity(Intent.createChooser(intent, "Open Snapchat"));
        }
    };

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
