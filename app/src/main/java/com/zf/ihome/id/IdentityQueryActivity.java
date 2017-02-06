package com.zf.ihome.id;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zf.common.app.BaseActivity;
import com.zf.ihome.R;

public class IdentityQueryActivity extends BaseActivity implements IdentityQueryListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_inquire);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.nav_identity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button queryButton = (Button) findViewById(R.id.queryButton);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputIdNoEdit = (EditText) findViewById(R.id.idNoEdit);
                IdentityQuery identityQuery = new IdentityQuery();
                identityQuery.request(inputIdNoEdit.getText().toString(), IdentityQueryActivity.this);
            }
        });

    }

    @Override
    public void onRequest(IdentityInfo identityInfo) {
        TextView addressView = (TextView) findViewById(R.id.addressView);
        addressView.setText(identityInfo.getAddress());
        TextView birthdayView = (TextView) findViewById(R.id.birthdayView);
        birthdayView.setText(identityInfo.getBirthday());
        TextView sexView = (TextView) findViewById(R.id.sexView);
        sexView.setText(identityInfo.getSex().equals("M") ? "男" : "女");
    }
}
