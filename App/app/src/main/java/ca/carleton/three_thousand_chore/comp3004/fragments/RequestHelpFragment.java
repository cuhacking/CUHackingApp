package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.carleton.three_thousand_chore.comp3004.JsonRequest;
import ca.carleton.three_thousand_chore.comp3004.R;
import ca.carleton.three_thousand_chore.comp3004.models.HelpRequest;

/**
 * Created by elisakazan on 2017-10-09.
 */

public class RequestHelpFragment extends Fragment {
    public interface HelpRequestSentListener {
        void helpRequestSent(HelpRequest request);
    }

    private Button getHelpButton;
    private EditText nameField;
    private EditText locationField;
    private EditText problemField;

    private HelpRequestSentListener listener;
    private int userId;

    private static String USER_ID = "user_id";

    public RequestHelpFragment() { }

    public static RequestHelpFragment newInstance(int userId) {
        RequestHelpFragment fragment = new RequestHelpFragment();

        Bundle b = new Bundle();
        b.putInt("user_id", userId);
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            userId = getArguments().getInt(USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.activity_request_help, null);
        getHelpButton = v.findViewById(R.id.get_help_button);
        nameField = v.findViewById(R.id.help_request_name);
        locationField = v.findViewById(R.id.help_request_location);
        problemField = v.findViewById(R.id.problem_description);

        getHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locationField.getText().toString().trim().equals("") || problemField.getText().toString().trim().equals("") || nameField.getText().toString().trim().equals("")){
                    Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    HelpRequest.createHelpRequest(locationField.getText().toString(), problemField.getText().toString(), userId, nameField.getText().toString(), new JsonRequest.CompletionHandler<HelpRequest>() {
                        @Override
                        public void requestSucceeded(HelpRequest object) {
                            listener.helpRequestSent(object);
                        }

                        @Override
                        public void requestFailed(String error) {
                            Toast.makeText(getContext(), "Failed to submit help request: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        if (getContext() instanceof HelpRequestSentListener) {
            this.listener = (HelpRequestSentListener) getContext();
        }
        else {
            throw new IllegalStateException("Context must be an instance of HelpRequestSentListener.");
        }

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
