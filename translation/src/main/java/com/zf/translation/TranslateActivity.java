package com.zf.translation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zf.common.app.BaseActivity;

public class TranslateActivity extends BaseActivity implements TranslateResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final EditText soureEdit = (EditText) findViewById(R.id.sourceEdit);
        ImageView covertButton = (ImageView) findViewById(R.id.covertButton);
        covertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TranslateService().translate(soureEdit.getText().toString(), TranslateActivity.this);
            }
        });
        soureEdit.addTextChangedListener(getWatcher(covertButton));
    }

    @NonNull
    private TextWatcher getWatcher(final ImageView covertButton) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    covertButton.setVisibility(View.VISIBLE);
                } else {
                    covertButton.setVisibility(View.INVISIBLE);
                }
            }
        };
    }


    @Override
    public void onResult(String result) {
        TextView resultView = (TextView) findViewById(R.id.translationView);
        resultView.setText(result);
    }
}
