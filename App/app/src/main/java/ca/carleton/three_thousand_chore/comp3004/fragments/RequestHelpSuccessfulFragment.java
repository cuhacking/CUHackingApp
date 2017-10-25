package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.carleton.three_thousand_chore.comp3004.R;
import ca.carleton.three_thousand_chore.comp3004.models.HelpRequest;

/**
 * Created by jackmccracken on 2017-10-19.
 */

public class RequestHelpSuccessfulFragment extends Fragment {
    TextView message;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_request_help_successful, null);

        message = (TextView) v.findViewById(R.id.successMessage);
        message.setMovementMethod(LinkMovementMethod.getInstance());

        return v;
    }
}
