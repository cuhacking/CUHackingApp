package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import ca.carleton.three_thousand_chore.comp3004.R;

/**
 * Created by elisakazan on 2017-10-09.
 */

public class LinksFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_links, null);

        ImageView fbImageView =(ImageView) v.findViewById(R.id.fbImage);
        ImageView slackImageView =(ImageView) v.findViewById(R.id.slackImage);
        ImageView twitterImageView =(ImageView) v.findViewById(R.id.twitterImage);
        ImageView snapImageView =(ImageView) v.findViewById(R.id.snapImage);
        TextView fbLink =(TextView) v.findViewById(R.id.fbLinkText);
        TextView slackLink =(TextView) v.findViewById(R.id.slackLinkText);
        TextView twitterLink =(TextView) v.findViewById(R.id.twitterLinkText);
        TextView snapLink =(TextView) v.findViewById(R.id.snapLinkText);

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

        slackLink.setMovementMethod(LinkMovementMethod.getInstance());
        slackImageView.setOnClickListener(slackRedirect);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private View.OnClickListener fbRedirect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                getContext().getPackageManager().getPackageInfo("com.facebook.katana", 0); //Checks if FB is installed.
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
