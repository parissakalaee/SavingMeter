package ir.Parka.keychi;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HelperUi {

    public static void persianizer(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                persianizer((ViewGroup) child);
                continue;
            }

            if (child instanceof TextView) {
                String tag = (String) child.getTag();
                if (tag != null && tag.equalsIgnoreCase("icon")) {
                    ((TextView) child).setTypeface(ActivityMain.typeFaceFont);
                } else if (tag != null && tag.equalsIgnoreCase("moon")) {
                    ((TextView) child).setTypeface(ActivityMain.typeFaceMoon);
                } else {
                    ((TextView) child).setTypeface(ActivityMain.typeFaceDefault);
                }
            }
        }
    }
}
