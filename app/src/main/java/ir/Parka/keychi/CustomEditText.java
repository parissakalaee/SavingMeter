package ir.Parka.keychi;

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


public class CustomEditText extends LinearLayout{

    private EditText  edtValue;
    Button btnUpDouble, btnDownDouble, btnUp, btnDown;
    private TextInputLayout lytEditText;

    private float     maxValue     = 99.9f;
    private float     minValue     = 0.0f;
    private float     currentValue = 0.0f;

    public CustomEditText(Context context) {
        super(context);
        initialize(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public void setHint (int hintID){
        lytEditText.setHint(getResources().getString(hintID));
    }

    private void initialize(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(ir.Parka.keychi.R.layout.custom_edittext, this, true);

        btnUpDouble     = (Button) view.findViewById(ir.Parka.keychi.R.id.btn_up_double);
        btnDownDouble   = (Button) view.findViewById(ir.Parka.keychi.R.id.btn_down_double);
        btnUp   = (Button) view.findViewById(ir.Parka.keychi.R.id.btn_up);
        btnDown = (Button) view.findViewById(ir.Parka.keychi.R.id.btn_down);

        edtValue = (EditText) view.findViewById(ir.Parka.keychi.R.id.edt_input);
        lytEditText = (TextInputLayout) view.findViewById(ir.Parka.keychi.R.id.lyt_edittext);
        lytEditText.setTypeface(ActivityMain.typeFaceDefault);

        btnUp.setOnClickListener(btnListener);
        btnUpDouble.setOnClickListener(btnListener);
        btnDown.setOnClickListener(btnListener);
        btnDownDouble.setOnClickListener(btnListener);

        edtValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        edtValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String contentString = edtValue.getText().toString();

                float value;
                try {
                    value = Float.parseFloat(contentString);
                    if (value > maxValue) {
                        edtValue.setText("" + maxValue);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    edtValue.setText("" + minValue);
                }

                if (getCurrentValue() == 0.0f) {
                    displayError(getResources().getString(ir.Parka.keychi.R.string.err_empty_input), true);
                } else {
                    displayError(null, false);
                }
            }
        });

//        edtValue.addTextChangedListener(new NumberTextWatcher(edtValue));
    }

    public float getCurrentValue() {
        String contentString = edtValue.getText().toString();

        float value;
        try {
            value = Float.parseFloat(contentString);
            if (value > maxValue) {
                value = maxValue;
            }
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            value = 0.0f;
        }
        return value;
    }

    public void setCurrentValue(float value) {
        currentValue = value;
        if (currentValue > maxValue) {
            currentValue = maxValue;
        }

        if (currentValue < minValue) {
            currentValue = minValue;
        }
        edtValue.setText(String.format(Locale.getDefault(), "%.1f", currentValue));
    }

    public void displayError(String errorInput, boolean errorFlag) {

        if(errorFlag){

            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(errorInput);
            ssbuilder.setSpan(new CustomTypefaceSpan("", ActivityMain.typeFaceDefault), 0, ssbuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            if (lytEditText.getChildCount() == 2)
                lytEditText.getChildAt(1).setVisibility(View.VISIBLE);

            lytEditText.setError(ssbuilder);

        } else {
            lytEditText.setError(null);

            if (lytEditText.getChildCount() == 2)
                lytEditText.getChildAt(1).setVisibility(View.GONE);
        }
    }

    public EditText getEditTextId (){
        return edtValue;
    }

    private OnClickListener btnListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            float tempValue;
            switch (v.getId()) {
                case ir.Parka.keychi.R.id.btn_down:
                    tempValue = getCurrentValue() - 0.1f;
                    if (tempValue >= minValue) {
                        currentValue = tempValue;
                        edtValue.setText("" + String.format("%.1f", currentValue));
                    } else {
                        edtValue.setText("" + minValue);
                    }
                    break;
                case ir.Parka.keychi.R.id.btn_down_double:
                    tempValue = getCurrentValue() - 1;
                    if (tempValue >= minValue) {
                        currentValue = tempValue;
                        edtValue.setText("" + String.format("%.1f", currentValue));
                    } else {
                        edtValue.setText("" + minValue);
                    }
                    break;
                case ir.Parka.keychi.R.id.btn_up:
                    tempValue = getCurrentValue() + 0.1f;
                    if (tempValue <= maxValue) {
                        currentValue = tempValue;
                        edtValue.setText("" + String.format("%.1f", currentValue));
                    }else{
                        edtValue.setText("" + maxValue);
                    }
                    break;
                case ir.Parka.keychi.R.id.btn_up_double:
                    tempValue = getCurrentValue() + 1;
                    if (tempValue <= maxValue) {
                        currentValue = tempValue;
                        edtValue.setText("" + String.format("%.1f", currentValue));
                    } else {
                        edtValue.setText("" + maxValue);
                    }
                    break;
            }
        }

    };
}