package io.github.grano22.feedbrico.shared.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//Class<? extends BoundedContextMarker>[] boundedContextMarkers = { FeedbackCollectionBoundedContextMarker.class };
//"io.github.grano22.feedbrico.*.infrastructure.readstore"

@Configuration
@EnableJpaRepositories(
    basePackages = "io.github.grano22.feedbrico.feedbackcollection.infrastructure.readstore.repository",
    entityManagerFactoryRef = "queryEntityManagerFactory",
    transactionManagerRef = "queryTransactionManager"
)
public class ReadSchemaDbConfig {
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean queryEntityManagerFactory(
        DataSource dataSource,
        EntityManagerFactoryBuilder builder
    ) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.default_schema", "core_projections");

        return builder
            .dataSource(dataSource)
            .packages("io.github.grano22.feedbrico.feedbackcollection.infrastructure.readstore.projection")
            .persistenceUnit("query")
            .properties(properties)
            .build()
        ;
    }

    @Primary
    @Bean
    public PlatformTransactionManager queryTransactionManager(
            @Qualifier("queryEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
