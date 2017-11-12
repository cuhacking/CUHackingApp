package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.carleton.three_thousand_chore.comp3004.JsonObjectRequest;
import ca.carleton.three_thousand_chore.comp3004.MainActivity;
import ca.carleton.three_thousand_chore.comp3004.R;
import ca.carleton.three_thousand_chore.comp3004.UserListener;
import ca.carleton.three_thousand_chore.comp3004.models.HelpRequest;

/**
 * Created by elisakazan on 2017-10-09.
 */

public class RequestHelpFragment extends Fragment implements UserListener {
    @Override
    public void userReceived(int userId) {
        this.userId = userId;
    }

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

    public static RequestHelpFragment newInstance() {
        return new RequestHelpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.activity_request_help, null);
        getHelpButton = v.findViewById(R.id.get_help_button);
        nameField = v.findViewById(R.id.help_request_name);
        locationField = v.findViewById(R.id.help_request_location);
        problemField = v.findViewById(R.id.problem_description);

        problemField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getHelpButton.performClick();
                    return true;
                }
                return false;
            }
        });

        getHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = getActivity().getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                if(locationField.getText().toString().trim().equals("") || problemField.getText().toString().trim().equals("") || nameField.getText().toString().trim().equals("")){
                    Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    HelpRequest.createHelpRequest(locationField.getText().toString(), problemField.getText().toString(), userId, nameField.getText().toString(), new JsonObjectRequest.CompletionHandler<HelpRequest>() {
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
