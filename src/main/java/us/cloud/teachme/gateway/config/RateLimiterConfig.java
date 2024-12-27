package us.cloud.teachme.gateway.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

import io.github.bucket4j.caffeine.CaffeineProxyManager;
import io.github.bucket4j.distributed.proxy.AsyncProxyManager;
import io.github.bucket4j.distributed.remote.RemoteBucketState;

@Configuration
public class RateLimiterConfig {

  @Bean
  AsyncProxyManager<String> asyncProxyManager() {
    Caffeine<String, RemoteBucketState> builder = (Caffeine) Caffeine.newBuilder().maximumSize(100);
    return new CaffeineProxyManager<>(builder, Duration.ofMinutes(1)).asAsync();
  }
  
}
