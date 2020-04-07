# 오큐파이 포털파인더 Android SDK

* 이 저장소에는 오큐파이 포털파인더를 사용하기 위한 Android SDK 원본 소스와 빌드된 aar 파일, 예제 소스코드가 포함돼있습니다. 
* 기존 오큐파이에서 제공하던 모바일 SDK 소스코드를 포털파인더를 사용할 수 있게 수정한 것입니다. 
* 본 소스코드는 Android Studio 3.6.1에서 실행 및 테스트되었습니다.

## 라이브러리 추가 및 설정

### 1. 라이브러리 추가 및 gradle 설정

- 저장소를 clone하거나 다운 받으신 후 `dist`폴더 아래 있는 `oqupiesupportsdk-release.aar` 파일을 안드로이드 프로젝트 libs 폴더에 복사하고 build.gradle 설정을 추가합니다.
```
implementation files('libs/oqupiesupportsdk-release.aar')
implementation 'androidx.legacy:legacy-support-v4:1.0.0'
```

**주의: 만약 다른 버전의 support-v4 라이브러리를 이미 사용 중이이서 충돌이 발생한다면 적절한 버전의 support-v4로 일치시킨 후 충분히 테스트해 본 후에 적용하시기 바랍니다.**

- minSdkVersion를 21, targetSdkVersion을 28 이상으로 설정합니다.
```
minSdkVersion 21
targetSdkVersion 28
```

### 2. 권한 설정

웹 뷰를 통한 인터넷 연결 및 고객이 파일 첨부를 할 수 있도록 "AndroidManifest.xml"에 아래의 권한을 추가합니다.

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

### 3. 포털파인더 연결

SDK를 연결하려는 모바일 앱의 1:1 문의, 고객센터 등의 고객지원 관련 버튼 클릭 시 아래의 코드가 실행될 수 있도록 내용을 추가합니다. 예제 코드는 `app/src/main/java/com/oqupie/portalfinderexample/MainActivity.java`에 있습니다.

예제 코드
```java
import android.graphics.Color;
import com.oqupie.deviceinfo.AppInfo;
import com.oqupie.deviceinfo.OqupieManager;

OqupieManager oqupieManager = OqupieManager.create(this);
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
String url = "https://{subdomain}.oqupie.com/portals/finder";
boolean showTitleBar = true;
String title = "고객센터";
int color = Color.rgb(127, 115, 231);

oqupieManager.openWebView(url, appInfo, showTitleBar, title, color);
```
1. OqupieManager와 AppInfo를 임포트합니다.
    ```java
    import com.oqupie.deviceinfo.AppInfo;
    import com.oqupie.deviceinfo.OqupieManager;
    ```
2. OqupieManager 객체를 생성합니다. `this`는 현재 액티비티를 나타냅니다.
    ```java
    OqupieManager oqupieManager = OqupieManager.create(this)
    ```
3. AppInfo 인스턴스를 OqupieManager에서 호출하여 포털파인더에 요청할 정보를 담을 수 있습니다.
    ```java
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
    ```
4. 액세스키와 시크릿키 및 브랜드키 설정은 [포털파인더 가이드](https://www.notion.so/onionfivecorp/2d67a57c22074cfd9e1d2d68f1a3e84d)에서 다시 확인할 수 있습니다.
    ```java
    appInfo.addInfo("access_key", "2190ffccd8dbb478");  // access_key는 필수 입니다.
    appInfo.addInfo("secret_key", "dde1cc31a14524bf903b2b1e71a8afde");  // secret_key는 필수 입니다.

    // brand_key1, 2, 3은 포털파인더 설정에 따라 입력해주세요. brand_key1은 필수이지만 나머지는 필수가 아닐 수도 있습니다.
    appInfo.addInfo("brand_key1", "ko");
    appInfo.addInfo("brand_key2", "goodgame");
    appInfo.addInfo("brand_key3", "asia");
    ```
5. 포털파인더 엔드포인트는 `https://{subdomain}.oqupie.com/portals/finder` 입니다. `{subdomain}`부분은 해당 계정의 서브도메인으로 변경하시면 됩니다. 
    ```java
    String url = "https://{subdomain}.oqupie.com/portals/finder";
    boolean showTitleBar = true;
    String title = "고객센터";
    int color = Color.rgb(127, 115, 231);

    oqupieManager.openWebView(url, appInfo, showTitleBar, title, color);
    ```
