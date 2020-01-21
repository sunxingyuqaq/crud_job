package com.study.boot.job.http;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * @author Xingyu Sun
 * @date 2020/1/21 14:55
 */
@Slf4j
@Component
public class HttpUtil {

    private static final int SUCCESS = 200;

    @Autowired
    private OkHttpClient okHttpClient;

    public JSONObject get(String url, Map<String, String> params) {
        Request request = getRequest(url, params);
        Call call = okHttpClient.newCall(request);
        JSONObject result = new JSONObject();
        try {
            Response execute = call.execute();
            int code = execute.code();
            if (code == SUCCESS) {
                result.put("code", code);
                result.put("success", true);
                ResponseBody body = execute.body();
                if (body != null) {
                    result.put("body", body.string());
                }
            } else {
                result.put("code", code);
                result.put("success", false);
                result.put("body", null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Object post(String url, Map<String, String> params) {
        postRequest(url, params, null);
        return null;
    }

    public Request getRequest(String url, Map<String, String> params) {
        if (params != null) {
            StringBuilder stringBuilder = new StringBuilder();
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                stringBuilder.append(StrUtil.format("&" + entry.getKey() + "={}", urlEncode(entry.getValue())));
            }
            if (url.contains("?")) {
                url += stringBuilder.toString();
            } else {
                url += stringBuilder.toString().replaceFirst("&", "?");
            }
        }
        log.info("url is {}", url);
        return new Request.Builder()
                .method("get", null)
                .url(url)
                .build();
    }

    public Request postRequest(String url, Map<String, String> params, RequestBody body) {
        return new Request.Builder()
                .method("post", body)
                .url(url)
                .build();
    }

    public String urlEncode(String str) {
        String encode = null;
        try {
            encode = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("encode str is {}", str, e);
        }
        return encode;
    }
}
