package com.example.amountindecrease;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class AmountInDecreaseView extends LinearLayout implements OnClickListener {

    private Context mContext;
    private ImageButton mDescrease;
    private ImageButton mIncrease;
    private EditText mNumberEditText;
    
    private int mMin = 1;
    private int mMax = Integer.MAX_VALUE - 1;
    
    public AmountInDecreaseView(Context context) {
        this(context, null, 0);
    }
    
    public AmountInDecreaseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public AmountInDecreaseView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.amount_indecrease_view, this);
        
        mContext = context;
        mDescrease = (ImageButton) findViewById(R.id.decrease);
        mIncrease = (ImageButton) findViewById(R.id.increase);
        mNumberEditText = (EditText) findViewById(R.id.number);
        
        mDescrease.setOnClickListener(this);
        mIncrease.setOnClickListener(this);
        mNumberEditText.clearFocus();
        mNumberEditText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (isEmpty()) {
                        setNumber(mMin);
                    }
                    clearFocus();
                    hideInputKeyboard();
                    return true;
                }
                return false;
            }
        });
        
        setNumber(mMin);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.decrease:
                if (getNumber() -1 >= mMin) {
                    setNumber(getNumber() - 1);
                }
                break;
            case R.id.increase:
                if (getNumber() + 1 <= mMax) {
                    setNumber(getNumber() + 1);
                }
                break;
        }
        
        if (mNumberEditText.hasFocus()) {
            mNumberEditText.clearFocus();
        }
    }
    
    public void setNumberRange(int min, int max) {
        assert (min >= 0 && max >= 0 && max >= min);
        mMin = min;
        mMax = max;
    }
    
    private boolean isEmpty() {
        return TextUtils.isEmpty(mNumberEditText.getText().toString().trim());
    }
    
    private int getNumber() {
        return Integer.valueOf(mNumberEditText.getText().toString());
    }
    
    private void setNumber(int number) {
        assert (number >= 0);
        mNumberEditText.setText(String.valueOf(number));
    }
    
    @SuppressWarnings("unused")
    private void showInputKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mNumberEditText, 0);
    }

    private void hideInputKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mNumberEditText.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
