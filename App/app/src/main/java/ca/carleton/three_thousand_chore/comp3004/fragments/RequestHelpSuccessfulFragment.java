package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ca.carleton.three_thousand_chore.comp3004.JsonRequest;
import ca.carleton.three_thousand_chore.comp3004.R;
import ca.carleton.three_thousand_chore.comp3004.models.HelpRequest;

/**
 * Created by jackmccracken on 2017-10-19.
 */

public class RequestHelpSuccessfulFragment extends Fragment {
    public interface HelpRequestCompletedListener {
        void helpRequestCompleted();
    }

    TextView mentorSubHeader;
    Button mentorDoneButton;
    HelpRequest activeHelpRequest;
    TextView mentorHeadline;
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

        if (activeHelpRequest.getStatus().equals(getString(R.string.mentor_found))) {
            mentorSubHeader.setText(R.string.subtitle_request_help_mentor_found);
            mentorHeadline.setText(String.format(getString(R.string.mentor_on_the_way_label), activeHelpRequest.getMentors()[0]));
            mentorDoneButton.setVisibility(View.VISIBLE);
        }

        mentorDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    activeHelpRequest.setStatus(getString(R.string.help_request_complete), new JsonRequest.CompletionHandler<JSONObject>() {
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
                    Log.e("RequestHelpSFrag Log", "(JSE) Help request failed to complete: " + e.getMessage());
                    Toast.makeText(getContext(), "Help request failed to complete: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
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
