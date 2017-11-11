package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import org.json.JSONException;
import org.json.JSONObject;

import ca.carleton.three_thousand_chore.comp3004.JsonObjectRequest;
import ca.carleton.three_thousand_chore.comp3004.R;
import ca.carleton.three_thousand_chore.comp3004.Requests;
import ca.carleton.three_thousand_chore.comp3004.models.HelpRequest;

/**
 * Created by jackmccracken on 2017-10-19.
 */

public class RequestHelpSuccessfulFragment extends Fragment {
    public interface HelpRequestCompletedListener {
        void helpRequestCompleted();
    }

    private TextView mentorSubHeader;
    private Button mentorDoneButton;
    private HelpRequest activeHelpRequest;
    private TextView mentorHeadline;
    private ImageView profilePictureView;
    private HelpRequestCompletedListener listener;

    public static RequestHelpSuccessfulFragment newInstance(HelpRequest activeHelpRequest) {
        RequestHelpSuccessfulFragment fragment = new RequestHelpSuccessfulFragment();

        Bundle b = new Bundle();
        b.putParcelable("help_request", activeHelpRequest);
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activeHelpRequest = this.getArguments().getParcelable("help_request");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_request_help_successful, null);

        mentorSubHeader = v.findViewById(R.id.successMessage);
        mentorSubHeader.setMovementMethod(LinkMovementMethod.getInstance());
        mentorHeadline = v.findViewById(R.id.mentor_headline);
        mentorDoneButton = v.findViewById(R.id.mentor_done_button);
        profilePictureView = v.findViewById(R.id.mentor_profile);
        String[] mentors = activeHelpRequest.getMentors();

        if (activeHelpRequest.getStatus().equals(getString(R.string.mentor_found))) {
            String message;
            if (activeHelpRequest.getMentors().length > 1) {
                // Handle multiple mentors
                message = String.format(getString(R.string.multiple_mentors), TextUtils.join(", ", mentors));

            }
            else {
                message = String.format(getString(R.string.mentor_on_the_way_label), mentors[0]);
                displayMentorProfileImage();
            }


            mentorSubHeader.setText(R.string.subtitle_request_help_mentor_found);
            mentorHeadline.setText(message);
            mentorDoneButton.setVisibility(View.VISIBLE);


        }
        else if (activeHelpRequest.getStatus().equals(getString(R.string.help_request_complete))) {
            listener.helpRequestCompleted();
        }

        mentorDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    activeHelpRequest.setStatus(getString(R.string.help_request_complete), new JsonObjectRequest.CompletionHandler<JSONObject>() {
                        @Override
                        public void requestSucceeded(JSONObject object) {
                            listener.helpRequestCompleted();
                        }

                        @Override
                        public void requestFailed(String errorMessage) {
                            Log.e("RequestHelpSFrag Log", "Help request failed to complete: " + errorMessage);
                            Toast.makeText(getContext(), "Help request failed to complete: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    Log.e("RequestHelpSFrag Log", "Help request failed to complete: " + e.getMessage());
                    Toast.makeText(getContext(), "Help request failed to complete: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    public void displayMentorProfileImage() {
        if (activeHelpRequest.getProfilePictureURL() != null && !activeHelpRequest.getProfilePictureURL().equals("null")) {
            ImageRequest request = new ImageRequest(activeHelpRequest.getProfilePictureURL(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    profilePictureView.setVisibility(View.VISIBLE);
                    profilePictureView.setImageBitmap(response);
                }
            }, 512, 512, Bitmap.Config.ALPHA_8, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("RequestHelpSFrag Log", "Failed to download profile picture: " + error.getMessage());
                    Toast.makeText(getContext(), "Failed to download profile picture: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            Requests.getInstance(getContext()).getQueue().add(request);
        }
    }

    @Override
    public void onAttach(Context context) {
        if (getContext() instanceof HelpRequestCompletedListener) {
            this.listener = (HelpRequestCompletedListener) getContext();
        }
        else {
            throw new IllegalStateException("Context must be an instance of HelpRequestCompletedListener.");
        }

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
