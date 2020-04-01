package com.oqupie.portalfinderexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oqupie.oqupiesupport.AppInfo;
import com.oqupie.oqupiesupport.Logger;
import com.oqupie.oqupiesupport.OqupieManager;

public class MainActivity extends Activity {

    private EditText editTextWebViewUrl = null;
    private TextView textViewOutput = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
    }

    private void initializeViews() {
        Logger.setLogLevel(Logger.VERBOSE);

        this.editTextWebViewUrl = (EditText) findViewById(R.id.editTextWebViewUrl);
        this.textViewOutput = (TextView) findViewById(R.id.textViewOutput);

        // 버튼 클릭시 웹뷰를 띄우면서 앱 정보를 전송한다.
        Button buttonOpenWebView = (Button) findViewById(R.id.buttonOpenWebView);
        buttonOpenWebView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OqupieManager oqupieManager = OqupieManager.create(MainActivity.this);
                AppInfo appInfo = oqupieManager.getAppInfo();
                appInfo.addInfo("userName", "Bi Bim Bap");
                appInfo.addInfo("userId", "wassup");
                appInfo.addInfo("userEmail", "example@onionfive.io");
                appInfo.addInfo("applicationLanguage", "en");
                appInfo.addInfo("access_key", "2190ffccd8dbb478");
                appInfo.addInfo("secret_key", "dde1cc31a14524bf903b2b1e71a8afde");
                appInfo.addInfo("brand_key1", "ko");
                appInfo.addInfo("brand_key2", "goodgame");
                appInfo.addInfo("brand_key3", "asia");
                appInfo.addInfo("게임엔진", "Android 네이티브");
                appInfo.addInfo("결제정보", "구글스토어");
                String url = editTextWebViewUrl.getText().toString();
                boolean showTitleBar = true;
                String title = "고객센터";
                int color = Color.rgb(127, 115, 231);

                oqupieManager.openWebView(url, appInfo, showTitleBar, title, color);
            }
        });

        // 버튼 클릭시 앱 정보를 읽어온 후 결과창에 출력한다.
        Button buttonDetectAppInfo = (Button) findViewById(R.id.buttonDetectAppInfo);
        buttonDetectAppInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OqupieManager oqupieManager = OqupieManager.create(MainActivity.this);
                MainActivity.this.textViewOutput.setText("");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
