package com.oqupie.oqupiesupport;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * 웹뷰에서 파일을 업로드를 처리하기 위한 클라이언트
 */
public class FileChooseClient extends WebChromeClient {
    public ValueCallback<Uri> uploadMsgHolder = null;
    public ValueCallback<Uri[]> filePathCallbackHolder = null;
    public final static int REQUEST_FILE_CHOOSE = 120;
    public final static int REQUEST_FILE_CHOOSE_LOLLIPOP = 121;
    public final static int REQUEST_STORAGE_PERMISSION_FOR_FILE_UPLOAD = 130;

    private Activity currentActivity = null;

    public FileChooseClient(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    /**
     * 롤리팝 이하에서 파일 선택 처리
     */
    private void startFileChoose() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        this.currentActivity.startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQUEST_FILE_CHOOSE);
    }

    /**
     * 롤리팝 이상에서 파일 선택 처리. (런타임 퍼미션 처리
     */
    public void startFileChooseLolliPop() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        this.currentActivity.startActivityForResult(intent, REQUEST_FILE_CHOOSE_LOLLIPOP);
    }


    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        uploadMsgHolder = uploadMsg;
        startFileChoose();
    }

    // For Android 3.0+, above method not supported in some android 3+ versions, in such case we use this
    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        uploadMsgHolder = uploadMsg;
        startFileChoose();
    }

    //For Android 4.1+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        uploadMsgHolder = uploadMsg;
        startFileChoose();
    }

    //For Android 5.0+
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        Logger.setLogLevel(Logger.VERBOSE);
        Logger.debug("파일선택");
        if (filePathCallbackHolder != null) {
            filePathCallbackHolder.onReceiveValue(null);
        }
        filePathCallbackHolder = filePathCallback;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (ActivityCompat.checkSelfPermission(this.currentActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this.currentActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION_FOR_FILE_UPLOAD);
            return true;
        }

        startFileChooseLolliPop();
        return true;
    }
}
