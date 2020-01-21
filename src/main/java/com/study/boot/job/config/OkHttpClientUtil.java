package com.study.boot.job.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Xingyu Sun
 * @date 2020/1/21 14:34
 */
@Configuration
public class OkHttpClientUtil {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().build();
    }
}
