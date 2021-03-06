package ir.Parka.keychi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import ir.Parka.keychi.ThisApplication;
import ir.Parka.keychi.act.ActivityMain;
import ir.Parka.keychi.helper.HelperUi;
import ir.Parka.keychi.view.CustomEditText;
import ir.Parka.keychi.view.CustomMonthYear;
import ir.Parka.keychi.view.CustomTypefaceSpan;
import ir.Parka.keychi.view.DrawableAwesome;
import ir.Parka.keychi.view.NumberTextWatcher;

public class FragmentCalculate extends Fragment {

    public FragmentCalculate() {
        // Required empty public constructor
    }

    private EditText edtGoal, edtCurrent;
    private TextInputLayout lytGoal, lytCurrent;
    private CustomMonthYear edtDate;
    private CustomEditText edtInflation, edtProfit;
    private long goalValue, currentValue;
    private int yearValue, monthValue;
    private float profitValue, inflationValue;
    public int whichYear = 1;
    public float savingValue = 0.0f;
    TextView txtOutput;
    private boolean isErrorSet = false;
    NumberFormat usFormat;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(ir.Parka.keychi.R.layout.fragment_calculate, container, false);
        HelperUi.persianizer((ViewGroup) view);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int dpi = metrics.densityDpi;
        int heightPX = metrics.heightPixels;
        int heightDP = (heightPX * 160) / dpi;

        if (heightDP >= 512) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        } else {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }

        DrawableAwesome drawable = new DrawableAwesome
                .DrawableAwesomeBuilder(getContext(), ir.Parka.keychi.R.string.ic_reset)
                .build();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(ir.Parka.keychi.R.id.fab);
        fab.setImageDrawable(drawable);
        fab.setOnClickListener(view1 -> {

            resetFields();
//                Snackbar.make(view, "?????????? ??????????", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
        });

        txtOutput = (TextView) view.findViewById(ir.Parka.keychi.R.id.txt_output);

        lytGoal = (TextInputLayout) view.findViewById(ir.Parka.keychi.R.id.lyt_goal);
        lytCurrent = (TextInputLayout) view.findViewById(ir.Parka.keychi.R.id.lyt_current);

        lytGoal.setTypeface(ActivityMain.typeFaceDefault);
        lytCurrent.setTypeface(ActivityMain.typeFaceDefault);

        edtGoal = (EditText) view.findViewById(ir.Parka.keychi.R.id.edt_goal);
        edtCurrent = (EditText) view.findViewById(ir.Parka.keychi.R.id.edt_current);
        edtProfit = (CustomEditText) view.findViewById(ir.Parka.keychi.R.id.edt_profit);
        edtInflation = (CustomEditText) view.findViewById(ir.Parka.keychi.R.id.edt_inflation);
        edtDate = (CustomMonthYear) view.findViewById(ir.Parka.keychi.R.id.edt_date);

        edtProfit.setHint(ir.Parka.keychi.R.string.str_profit);
        edtInflation.setHint(ir.Parka.keychi.R.string.str_inflation);

        edtGoal.addTextChangedListener(new MyTextWatcher());
        edtCurrent.addTextChangedListener(new MyTextWatcher());
        edtInflation.getEditTextId().addTextChangedListener(new MyTextWatcher());
        edtProfit.getEditTextId().addTextChangedListener(new MyTextWatcher());
        edtDate.getMonthId().addTextChangedListener(new MyTextWatcher());
        edtDate.getYearId().addTextChangedListener(new MyTextWatcher());

        edtGoal.addTextChangedListener(new NumberTextWatcher(edtGoal));
        edtCurrent.addTextChangedListener(new NumberTextWatcher(edtCurrent));

        int maxLength = 11;
        edtGoal.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        edtCurrent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        resetFields();

        usFormat = NumberFormat.getNumberInstance(Locale.US);
        try{
            goalValue = usFormat.parse(edtGoal.getText().toString().trim()).longValue();
        }catch(ParseException e){
            e.printStackTrace();
        }
        try{
            currentValue = usFormat.parse(edtCurrent.getText().toString().trim()).longValue();
        }catch(ParseException e){
            e.printStackTrace();
        }
        profitValue = edtProfit.getCurrentValue();
        inflationValue = edtInflation.getCurrentValue();
        yearValue = edtDate.getYearValue();
        monthValue = edtDate.getMonthValue();

        return view;
    }

    private void resetFields() {

        edtGoal.setFocusable(false);
        edtGoal.setFocusableInTouchMode(true);
        edtCurrent.setFocusable(false);
        edtCurrent.setFocusableInTouchMode(true);
        edtProfit.getEditTextId().setFocusable(false);
        edtProfit.getEditTextId().setFocusableInTouchMode(true);
        edtInflation.getEditTextId().setFocusable(false);
        edtInflation.getEditTextId().setFocusableInTouchMode(true);
        edtDate.getYearId().setFocusable(false);
        edtDate.getYearId().setFocusableInTouchMode(true);
        edtDate.getMonthId().setFocusable(false);
        edtDate.getMonthId().setFocusableInTouchMode(true);

        edtGoal.setText("100000");
        edtCurrent.setText("0");
        edtProfit.setCurrentValue(15);
        edtInflation.setCurrentValue(25);
        edtDate.setYearValue(1);
        edtDate.setMonthValue(1);
    }

    private void submitForm() {
        boolean isCorrectYear = calculateSaving(monthValue, yearValue, goalValue, currentValue, inflationValue, profitValue);

        if (isCorrectYear) {
            txtOutput.setVisibility(View.VISIBLE);
            txtOutput.setText(NumberFormat.getNumberInstance(Locale.US).format((int)savingValue));
            edtDate.displayErrorYear(null, false);
        } else {
            txtOutput.setVisibility(View.INVISIBLE);
            String msgString = " ???? ?????? ?????? ?????????? ???????????? ???????? ???????? ?????? ????" + " " + whichYear + " " + " ?????? ??????";
            edtDate.displayErrorYear(msgString, true);
        }
    }

    private void validateGoal() {
        ThisApplication.HANDLER.post(() -> {
            if (edtGoal.getText().toString().length() > 0){
                try{
                    goalValue = usFormat.parse(edtGoal.getText().toString().trim()).longValue();
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }

            if (edtCurrent.getText().toString().length() > 0){
                try{
                    currentValue = usFormat.parse(edtCurrent.getText().toString().trim()).longValue();
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }

            if (edtGoal.getText().toString().trim().isEmpty()) {
                txtOutput.setVisibility(View.INVISIBLE);
                displayError(lytGoal, getString(ir.Parka.keychi.R.string.err_empty_goal), true);
            } else if (goalValue < 100000) {
                txtOutput.setVisibility(View.INVISIBLE);
                displayError(lytGoal, getString(ir.Parka.keychi.R.string.err_insufficient_goal), true);
            } else  {
                if (goalValue <= currentValue) {
                    txtOutput.setVisibility(View.INVISIBLE);
                    displayError(lytGoal, getString(ir.Parka.keychi.R.string.err_unnecessary_saving), true);
                    displayError(lytCurrent, getString(ir.Parka.keychi.R.string.err_unnecessary_saving), true);
                    isErrorSet = true;
                } else if ((inflationValue - profitValue) * goalValue / 100 > (goalValue - currentValue)) {
                    txtOutput.setVisibility(View.INVISIBLE);
                    displayError(lytGoal, getString(ir.Parka.keychi.R.string.err_wrong_method), true);
                    displayError(lytCurrent, getString(ir.Parka.keychi.R.string.err_wrong_method), true);
                    isErrorSet = true;
                } else {
                    displayError(lytGoal, null, false);
                    if (isErrorSet) {
                        displayError(lytCurrent, null, false);
                        isErrorSet = false;
                    }
                    submitForm();
                }
            }
        });
    }

    private void validateCurrent() {
        ThisApplication.HANDLER.post(() -> {
            if (edtGoal.getText().toString().length() > 0){
                try{
                    goalValue = usFormat.parse(edtGoal.getText().toString().trim()).longValue();
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }

            if (edtCurrent.getText().toString().length() > 0){
                try{
                    currentValue = usFormat.parse(edtCurrent.getText().toString().trim()).longValue();
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }

            if (edtCurrent.getText().toString().trim().isEmpty()) {
                txtOutput.setVisibility(View.INVISIBLE);
                displayError(lytCurrent, getString(ir.Parka.keychi.R.string.str_current_error), true);
            } else {
                if (goalValue <= currentValue) {
                    txtOutput.setVisibility(View.INVISIBLE);
                    displayError(lytCurrent, getString(ir.Parka.keychi.R.string.err_unnecessary_saving), true);
                    displayError(lytGoal, getString(ir.Parka.keychi.R.string.err_unnecessary_saving), true);
                    isErrorSet = true;
                } else if ((inflationValue - profitValue) * goalValue / 100 > (goalValue - currentValue)) {
                    txtOutput.setVisibility(View.INVISIBLE);
                    displayError(lytCurrent, getString(ir.Parka.keychi.R.string.err_wrong_method), true);
                    displayError(lytGoal, getString(ir.Parka.keychi.R.string.err_wrong_method), true);
                    isErrorSet = true;
                } else {
                    displayError(lytCurrent, null, false);

                    if (isErrorSet) {
                        displayError(lytGoal, null, false);
                        isErrorSet = false;
                    }

                    submitForm();
                }
            }
        });
    }

    private void validateProfit() {
        ThisApplication.HANDLER.post(() -> {
            profitValue = edtProfit.getCurrentValue();
            if (edtProfit.getCurrentValue() >= edtInflation.getCurrentValue()) {
                edtProfit.displayError(getString(ir.Parka.keychi.R.string.err_inflation_less_than_profit), true);
                edtInflation.displayError(getString(ir.Parka.keychi.R.string.err_inflation_less_than_profit), true);
                txtOutput.setVisibility(View.INVISIBLE);
            } else {
                edtProfit.displayError(null, false);
                edtInflation.displayError(null, false);
                submitForm();
            }
        });
    }

    private void validateInflation() {
        ThisApplication.HANDLER.post(() -> {
            inflationValue = edtInflation.getCurrentValue();
            if (edtProfit.getCurrentValue() >= edtInflation.getCurrentValue()) {
                edtProfit.displayError(getString(ir.Parka.keychi.R.string.err_inflation_less_than_profit), true);
                edtInflation.displayError(getString(ir.Parka.keychi.R.string.err_inflation_less_than_profit), true);
                txtOutput.setVisibility(View.INVISIBLE);
            } else {
                edtInflation.displayError(null, false);
                edtProfit.displayError(null, false);
                submitForm();
            }
        });
    }

    private void validateDate() {
        ThisApplication.HANDLER.post(() -> {
            yearValue = edtDate.getYearValue();
            monthValue = edtDate.getMonthValue();
            if (yearValue == 0 && (monthValue == 0 || monthValue == 1)) {
                edtDate.displayErrorMonth(getString(ir.Parka.keychi.R.string.err_zero_year_month), true);
                edtDate.displayErrorYear(getString(ir.Parka.keychi.R.string.err_zero_year_month), true);
                txtOutput.setVisibility(View.INVISIBLE);
            } else {
                edtDate.displayErrorMonth(null, false);
                submitForm();
            }
        });
    }

    private class MyTextWatcher implements TextWatcher {

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {

            if (editable == edtGoal.getEditableText()) {
                validateGoal();
            } else if (editable == edtCurrent.getEditableText()) {
                validateCurrent();
            } else if (editable == edtProfit.getEditTextId().getEditableText()) {
                validateProfit();
            } else if (editable == edtInflation.getEditTextId().getEditableText()) {
                validateInflation();
            } else if (editable == edtDate.getMonthId().getEditableText() || editable == edtDate.getYearId().getEditableText()) {
                validateDate();
            }
        }
    }

    public void displayError(TextInputLayout inputLayout, String errorInput, boolean errorFlag) {
        if (errorFlag) {
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(errorInput);
            ssbuilder.setSpan(new CustomTypefaceSpan("", ActivityMain.typeFaceDefault), 0, ssbuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            if (inputLayout.getChildCount() == 2)
                inputLayout.getChildAt(1).setVisibility(View.VISIBLE);

            inputLayout.setError(ssbuilder);
        } else {
            inputLayout.setError(null);

            if (inputLayout.getChildCount() == 2)
                inputLayout.getChildAt(1).setVisibility(View.GONE);
        }
    }

    private boolean calculateSaving(int monthNumber, int yearNumber, float currentPrice, float currentState, float inflation, float profit) {
        float[] yearPrice = new float[yearNumber];
        float[] yearState = new float[yearNumber];
        float minSaving = 1000000000.0f;

        float saveCurrentPrice = currentPrice;
        float saveCurrentState = currentState;

        for (int yearCnt = 0; yearCnt < yearNumber; yearCnt++) {
            yearPrice[yearCnt] = currentPrice * (1.0f + inflation / 100.0f);
            yearState[yearCnt] = currentState * (1.0f + profit / 100.0f);

            savingValue = ((yearPrice[yearCnt] - yearState[yearCnt]));
            savingValue = savingValue / ((yearCnt + 1) * 12) + ((((yearCnt + 1) * 12) * (((yearCnt + 1) * 12) + 1)) * profit / 100.0f) / 24;
//            Toast.makeText(getActivity().getApplicationContext(), " " + savingValue, Toast.LENGTH_LONG).show();

            currentPrice = yearPrice[yearCnt];
            currentState = yearState[yearCnt];

            if (savingValue < minSaving) {
                minSaving = savingValue;
            } else {
                whichYear = yearCnt + 1;
                return false;
            }
        }

        if (monthNumber != 0) {
            monthNumber += yearNumber * 12;
            float[] monthPrice = new float[monthNumber];
            float[] monthState = new float[monthNumber];
            float monthInflation = inflation / 1200.0f;
            float monthProfit = profit / 1200.0f;

            for (int monthCnt = 0; monthCnt < monthNumber; monthCnt++) {
                monthPrice[monthCnt] = saveCurrentPrice * (1.0f + (monthInflation * (monthCnt + 1)));
                monthState[monthCnt] = saveCurrentState * (1.0f + (monthProfit * (monthCnt + 1)));

                savingValue = ((monthPrice[monthCnt] - monthState[monthCnt]));
                savingValue /= ((monthCnt + 1) + (((monthCnt + 1) * (monthCnt + 2) / 2) * monthProfit));
            }
        }

        return true;
    }
}
