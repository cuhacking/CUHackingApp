package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.carleton.three_thousand_chore.comp3004.R;

/**
 * Created by jackmccracken on 2017-10-19.
 */

public class RequestHelpSuccessfulFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_request_help_successful, null);

        return v;
    }
}
