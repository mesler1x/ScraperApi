package ru.urfu.scraperapi.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeterRegistryConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> meterRegistry() {
        return registry -> registry.config().commonTags("application", "aigineer");
    }
}