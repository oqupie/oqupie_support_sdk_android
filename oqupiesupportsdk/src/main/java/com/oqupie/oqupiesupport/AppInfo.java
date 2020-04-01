package com.oqupie.oqupiesupport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 앱 정보를 담아서 전송하는 데이터 클래스
 */
public class AppInfo {
    /**
     * 추가정보
     */
    private HashMap<String, String> additionalInfo = new HashMap<String, String>();

    /**
     * 추가 정보 저장
     *
     * @param key   키
     * @param value 값
     */
    public void addInfo(String key, String value) {
        this.additionalInfo.put(key, value);
    }

    private String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private String urlEncodeUTF8(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(
                    String.format(
                            "%s=%s",
                            urlEncodeUTF8(entry.getKey().toString()),
                            urlEncodeUTF8(entry.getValue().toString())
                    )
            );
        }
        return sb.toString();
    }

    String toQueryString() {
        return urlEncodeUTF8(this.additionalInfo);
    }
}
