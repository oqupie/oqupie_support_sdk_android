package com.oqupie.oqupiesupport;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 웹뷰를 띄우기 위한 액티비티
 */
public class WebViewActivity extends Activity {

    private WebView webView = null;
    private FileChooseClient fileChooseClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        initializeViews(intent);
    }

    /**
     * 웹뷰를 초기화한다.
     */
    private void initializeViews(Intent intent) {
        this.webView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = this.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        // 이게 없으면 뒤로가기가 동작하지 않는다.
        if (Build.VERSION.SDK_INT > 18) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        this.webView.setWebViewClient(new WebViewClient() {
            //@SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (view.canGoBack()) {
                    view.loadUrl(url);
                    return true;
                }
                return false;
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.isRedirect()) {
                    String url = request.getUrl().toString();
                    view.loadUrl(url);
                    return true;
                }
                return false;
            }
        });

        this.fileChooseClient = new FileChooseClient(this);
        this.webView.setWebChromeClient(fileChooseClient);

        try {
            Bundle bundle = intent.getExtras();
            String url = bundle.getString(Keys.WEBVIEW_URL);
            url = url.trim();

            String appInfoQueryString = bundle.getString(Keys.APP_INFO, null);

            String queryString = "?" + appInfoQueryString;
            String requestUrl = url + queryString;
            this.webView.loadUrl(requestUrl);

            LinearLayout titleBar = (LinearLayout) findViewById(R.id.titleBar);
            boolean showTitleBar = bundle.getBoolean(Keys.SHOW_TITLE_BAR, false);

            if (showTitleBar) {

                int color = bundle.getInt(Keys.COLOR, android.graphics.Color.BLACK);
                titleBar.setBackgroundColor(color);

                String title = bundle.getString(Keys.TITLE, "");
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(title);

                ImageButton buttonClose = (ImageButton) findViewById(R.id.buttonClose);
                buttonClose.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        finish();
                    }
                });

            } else {
                titleBar.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 뒤로가기 버튼 처리
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    // GPU 정보를 읽어온 경우에 서버로 전송한 후 웹뷰를 띄운다.
    // 파일을 선택한 경우 콜백을 호출한다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FileChooseClient.REQUEST_FILE_CHOOSE) {
            if (this.fileChooseClient.uploadMsgHolder == null)
                return;

            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            this.fileChooseClient.uploadMsgHolder.onReceiveValue(result);
            this.fileChooseClient.uploadMsgHolder = null;
        } else if (requestCode == FileChooseClient.REQUEST_FILE_CHOOSE_LOLLIPOP) {
            if (this.fileChooseClient.filePathCallbackHolder == null)
                return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                this.fileChooseClient.filePathCallbackHolder.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
            this.fileChooseClient.filePathCallbackHolder = null;
        }
    }

    // 파일 접근 권한 요청에 대한 사용자 선택 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == FileChooseClient.REQUEST_STORAGE_PERMISSION_FOR_FILE_UPLOAD) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 허가
                this.fileChooseClient.startFileChooseLolliPop();
            } else {
                // 권한 거부
                // 일단 사용자가 권한을 거부한 이후에는 웹 페이지의 "파일선택" 버튼을 다시 클릭해도 onShowFileChooser 메소드가 호출되지 않는다. (무반응)
                // 강제로 뒤로가기 처리를 한다.

                if (WebViewActivity.this.webView.canGoBack()) {
                    WebViewActivity.this.webView.goBack();
                } else {
                    finish();
                }
            }
        }
    }
}
