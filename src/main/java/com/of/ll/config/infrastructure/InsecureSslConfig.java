package com.of.ll.config.infrastructure;

import java.net.http.HttpClient;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@Profile("insecure-ssl")
public class InsecureSslConfig {

    @Bean
    @Primary
    RestClient.Builder restClientBuilder() throws Exception {
        final TrustManager[] trustAll = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        } };

        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAll, new SecureRandom());

        final HttpClient client = HttpClient.newBuilder().sslContext(sslContext).build();
        final JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(client);

        return RestClient.builder().requestFactory(factory);
    }

}
