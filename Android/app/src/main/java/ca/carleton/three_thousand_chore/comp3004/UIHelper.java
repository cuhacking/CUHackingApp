package ca.carleton.three_thousand_chore.comp3004;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by estitweg on 2017-11-12.
 */

public class UIHelper {

    public static void closeKeyboard(View v, InputMethodManager imm){
        if (v != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
    

}
