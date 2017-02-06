package com.zf.fastshare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zf.common.widget.RandomTextView;
import com.zf.common.widget.RippleView;
import com.zf.fastshare.wifi.Common;

import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity {
    private RandomTextView randomTextView = null;
    private WifiManager wifiManager = null;
    private boolean isConnected = false;
    private List<String> wifiList = new ArrayList<>();
    private String ssid = null;
    private BroadcastReceiver wifiReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        randomTextView = (RandomTextView) findViewById(R.id.random_textview);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                randomTextView.addKeyWord("彭丽媛");
                randomTextView.addKeyWord("习近平");
                randomTextView.show();
            }
        }, 2 * 1000);
        randomTextView.setOnRippleViewClickListener(new RandomTextView.OnRippleViewClickListener() {
            @Override
            public void onRippleViewClicked(View view) {
                connectToAp(((RippleView) view).getText().toString());
            }
        });

        init();
    }

    private void init() {
        wifiReceiver = createWifiReceiver();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        List<ScanResult> list = wifiManager.getScanResults();
        if (list == null || list.size() == 0 || isConnected) {
            return;
        }
        for (ScanResult result : list) {
            if ((result.SSID).startsWith(Common.SSID)) {
                wifiList.add(result.SSID);
                randomTextView.addKeyWord(result.SSID);
                randomTextView.show();
            }
        }
    }

    @NonNull
    private BroadcastReceiver createWifiReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            }
        };
    }

    private void connectToAp(String ssid) {
        this.ssid = ssid;
        WifiConfiguration wifiConfig = this.setWifiParams(ssid);
        int wcgID = wifiManager.addNetwork(wifiConfig);
        boolean flag = wifiManager.enableNetwork(wcgID, true);
        isConnected = flag;
    }

    private WifiConfiguration setWifiParams(String ssid) {
        WifiConfiguration apConfig = new WifiConfiguration();
        apConfig.SSID = ssid;
        apConfig.preSharedKey = Common.PASSWORD;
        apConfig.hiddenSSID = true;
        apConfig.status = WifiConfiguration.Status.ENABLED;
        apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        apConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        apConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        apConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        return apConfig;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiReceiver);
        wifiManager.disconnect();
    }
}
