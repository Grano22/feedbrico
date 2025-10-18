package io.github.grano22.feedbrico.shared.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
    basePackages = "io.github.grano22.feedbrico.feedbackcollection.infrastructure.persistance.repository",
    entityManagerFactoryRef = "commandEntityManagerFactory",
    transactionManagerRef = "commandTransactionManager"
)
public class WriteSchemaDbConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean commandEntityManagerFactory(
        DataSource dataSource,
        EntityManagerFactoryBuilder builder
    ) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.default_schema", "core");

        return builder
            .dataSource(dataSource)
            .packages("io.github.grano22.feedbrico.feedbackcollection.infrastructure.persistance.entity")
            .persistenceUnit("command")
            .properties(properties)
            .build()
        ;
    }

    @Bean
    public PlatformTransactionManager commandTransactionManager(
            @Qualifier("commandEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}