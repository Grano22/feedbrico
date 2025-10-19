package io.github.grano22.feedbrico.shared.config;

import io.github.grano22.feedbrico.shared.infrastructure.logging.EachRequestLogsEnricher;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.time.Clock;

@Configuration
public class AppConfig {
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public FilterRegistrationBean<EachRequestLogsEnricher> earlyFilterRegistration() {
        FilterRegistrationBean<EachRequestLogsEnricher> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new EachRequestLogsEnricher());

        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}
