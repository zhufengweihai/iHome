package com.zf.fastshare;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.skyfishjy.library.RippleBackground;
import com.zf.common.app.BaseActivity;
import com.zf.fastshare.wifi.WifiApServer;

public class ServerActivity extends BaseActivity {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
        TextView userNameView = (TextView) findViewById(R.id.userNameView);
        userNameView.setText(Build.MODEL);


//        int hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS);
//        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS},
//                    REQUEST_CODE_ASK_PERMISSIONS);
//        }

        startWifiAp();
    }

    private void startWifiAp() {
        WifiApServer manager = new WifiApServer(this);
        manager.startWifiAp();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
//            grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_ASK_PERMISSIONS:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    startWifiAp();
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
}
