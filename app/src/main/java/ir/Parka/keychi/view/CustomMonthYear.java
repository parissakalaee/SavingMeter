package ir.Parka.keychi.view;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Locale;

import ir.Parka.keychi.act.ActivityMain;


public class CustomMonthYear extends LinearLayout{

    private EditText edtYear;
    private EditText edtMonth;
    private TextInputLayout lytYear;
    private TextInputLayout lytMonth;

    private int maxYear = 100;
    private int minYear = 0;
    private int currentYear = 0;

    private int maxMonth = 11;
    private int minMonth = 0;
    private int currentMonth = 0;


    public CustomMonthYear(Context context) {
        super(context);
        initialize(context);
    }

    public CustomMonthYear(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(ir.Parka.keychi.R.layout.custom_yearmonth, this, true);

        Button btnUpYear = (Button) view.findViewById(ir.Parka.keychi.R.id.btn_up_year);
        Button btnDownYear = (Button) view.findViewById(ir.Parka.keychi.R.id.btn_down_year);
        Button btnUpMonth = (Button) view.findViewById(ir.Parka.keychi.R.id.btn_up_month);
        Button btnDownMonth = (Button) view.findViewById(ir.Parka.keychi.R.id.btn_down_month);

        edtYear = (EditText) view.findViewById(ir.Parka.keychi.R.id.edt_year);
        lytYear = (TextInputLayout) view.findViewById(ir.Parka.keychi.R.id.lyt_year);
        lytYear.setTypeface(ActivityMain.typeFaceDefault);

        btnUpYear.setOnClickListener(btnListener);
        btnDownYear.setOnClickListener(btnListener);

        edtMonth = (EditText) view.findViewById(ir.Parka.keychi.R.id.edt_month);
        lytMonth = (TextInputLayout) view.findViewById(ir.Parka.keychi.R.id.lyt_month);
        lytMonth.setTypeface(ActivityMain.typeFaceDefault);

        btnUpMonth.setOnClickListener(btnListener);
        btnDownMonth.setOnClickListener(btnListener);

        edtYear.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        edtMonth.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        edtYear.addTextChangedListener(new NumberTextWatcher(edtYear));
        edtMonth.addTextChangedListener(new NumberTextWatcher(edtMonth));

        edtYear.addTextChangedListener(new MyTextWatcher());
        edtMonth.addTextChangedListener(new MyTextWatcher());
    }

    public int getYearValue() {
        String contentString = edtYear.getText().toString();

        int value;
        try {
            value = Integer.parseInt(contentString);
            if (value > maxYear) {
                value = maxYear;
            }
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            value = 0;
        }
        return value;
    }

    public int getMonthValue() {
        String contentString = edtMonth.getText().toString();

        int value;
        try {
            value = Integer.parseInt(contentString);
            if (value > maxMonth) {
                value = maxMonth;
            }
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            value = 0;
        }
        return value;
    }

    public void setYearValue(int value) {
        currentYear = value;
        if (currentYear > maxYear) {
            currentYear = maxYear;
        }

        if (currentYear < minYear) {
            currentYear = minYear;
        }
        edtYear.setText(String.format(new Locale("en-US"),"%d", currentYear));
    }

    public void setMonthValue(int value) {
        currentMonth = value;
        if (currentMonth > maxMonth) {
            currentMonth = maxMonth;
        }

        if (currentMonth < minMonth) {
            currentMonth = minMonth;
        }
        edtMonth.setText(String.format(new Locale("en-US"),"%d", currentMonth));
    }

    public void displayErrorYear(String errorInput, boolean errorFlag) {

        if(errorFlag){

            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(errorInput);
            ssbuilder.setSpan(new CustomTypefaceSpan("", ActivityMain.typeFaceDefault), 0, ssbuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            if (lytYear.getChildCount() == 2)
                lytYear.getChildAt(1).setVisibility(View.VISIBLE);

            lytYear.setError(ssbuilder);

        } else {
            lytYear.setError(null);

            if (lytYear.getChildCount() == 2)
                lytYear.getChildAt(1).setVisibility(View.GONE);
        }
    }

    public void displayErrorMonth(String errorInput, boolean errorFlag) {

        if(errorFlag){

            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(errorInput);
            ssbuilder.setSpan(new CustomTypefaceSpan("", ActivityMain.typeFaceDefault), 0, ssbuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            if (lytMonth.getChildCount() == 2)
                lytMonth.getChildAt(1).setVisibility(View.VISIBLE);

            lytMonth.setError(ssbuilder);

        } else {
            lytMonth.setError(null);

            if (lytMonth.getChildCount() == 2)
                lytMonth.getChildAt(1).setVisibility(View.GONE);
        }
    }

    public EditText getYearId  () {return edtYear;}
    public EditText getMonthId () {return edtMonth;}

    private OnClickListener btnListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            int tempValue;
            switch (v.getId()) {
                case ir.Parka.keychi.R.id.btn_down_month:
                    tempValue = getMonthValue() - 1;
                    if (tempValue >= minMonth) {
                        currentMonth = tempValue;
                        edtMonth.setText(String.format(new Locale("en-US"),"%d", currentMonth));
                    } else {
                        edtMonth.setText(String.format(new Locale("en-US"),"%d", maxMonth));
                    }
                    break;
                case ir.Parka.keychi.R.id.btn_down_year:
                    tempValue = getYearValue() - 1;
                    if (tempValue >= minYear) {
                        currentYear = tempValue;
                        edtYear.setText(String.format(new Locale("en-US"),"%d", currentYear));
                    } else {
                        edtYear.setText(String.format(new Locale("en-US"),"%d", minYear));
                    }
                    break;
                case ir.Parka.keychi.R.id.btn_up_month:
                    tempValue = getMonthValue() + 1;
                    if (tempValue <= maxMonth) {
                        currentMonth = tempValue;
                        edtMonth.setText(String.format(new Locale("en-US"),"%d", currentMonth));
                    }else{
                        edtMonth.setText(String.format(new Locale("en-US"),"%d", minMonth));
                    }
                    break;
                case ir.Parka.keychi.R.id.btn_up_year:
                    tempValue = getYearValue() + 1;
                    if (tempValue <= maxYear) {
                        currentYear = tempValue;
                        edtYear.setText(String.format(new Locale("en-US"),"%d", currentYear));
                    } else {
                        edtYear.setText(String.format(new Locale("en-US"),"%d", maxYear));
                    }
                    break;
            }
        }

    };

    private class MyTextWatcher implements TextWatcher {

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {

            if (editable == edtMonth.getEditableText()) {
                String contentString = edtMonth.getText().toString();

                float value;
                try {
                    value = Integer.parseInt(contentString);
                    if (value > maxMonth) {
                        edtMonth.setText(String.format(new Locale("en-US"),"%d", maxMonth));
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    edtMonth.setText(String.format(new Locale("en-US"),"%d", minMonth));
                }
            } else if (editable == edtYear.getEditableText()) {
                String contentString = edtYear.getText().toString();

                float value;
                try {
                    value = Integer.parseInt(contentString);
                    if (value > maxYear) {
                        edtYear.setText(String.format(new Locale("en-US"),"%d", maxYear));
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    edtYear.setText(String.format(new Locale("en-US"),"%d", minYear));
                }
            }
        }
    }
}