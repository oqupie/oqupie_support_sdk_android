package com.oqupie.oqupiesupport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

/**
 * 앱 정보를 검색하거나 웹뷰를 호출하는 매니저 클래스
 */
public class OqupieManager {
    private Activity currentActivity = null;

    public static OqupieManager create(Activity currentActivity) {
        OqupieManager oqupieManager = new OqupieManager();
        oqupieManager.currentActivity = currentActivity;
        return oqupieManager;
    }

    /**
     * 앱 정보를 전송한 후 웹뷰를 띄운다.
     *
     * @param url          웹뷰 URL
     * @param showTitleBar 타이틀바 표시 여부
     * @param title        타이틀
     * @param color        타이틀바 배경색
     */
    public void openWebView(String url, AppInfo appInfo, boolean showTitleBar, String title, int color) {
        try {
            startWebViewActivity(url, appInfo.toQueryString(), showTitleBar, title, color);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * openWebView 유니티 바인딩 함수
     *
     * @param url                웹뷰 URL
     * @param appInfoQueryString 앱 정보 URL쿼리스트링 데이터
     * @param showTitleBar       타이틀바 표시 여부
     * @param title              타이틀
     * @param red                배경색의 R
     * @param green              배경색의 G
     * @param blue               배경색의 B
     */
    public void openWebViewByUnity(String url, String appInfoQueryString, boolean showTitleBar, String title, int red, int green, int blue) {
        try {
            int color = Color.rgb(red, green, blue);
            startWebViewActivity(url, appInfoQueryString, showTitleBar, title, color);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 웹뷰 액티비티를 연다
     *
     * @param url                웹뷰 URL
     * @param appInfoQueryString 앱 정보 쿼리스트링 데이터
     * @param showTitleBar       타이틀바 표시 여부
     * @param title              타이틀
     * @param color              타이틀바 배경색
     */
    private void startWebViewActivity(String url, String appInfoQueryString, boolean showTitleBar, String title, int color) {
        Intent intent = new Intent(this.currentActivity, WebViewActivity.class);
        intent.putExtra(Keys.WEBVIEW_URL, url);
        intent.putExtra(Keys.APP_INFO, appInfoQueryString);
        intent.putExtra(Keys.SHOW_TITLE_BAR, showTitleBar);
        intent.putExtra(Keys.TITLE, title);
        intent.putExtra(Keys.COLOR, color);
        currentActivity.startActivity(intent);
    }

    /**
     * 기본적인 디바이스 정보를 검색한다.
     *
     * @return 앱정보
     */
    public AppInfo getAppInfo() {
        return new AppInfo();
    }
}
