package com.moneymong.global.config.jpa;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = {
                "com.moneymong.domain"
        },
        entityManagerFactoryRef = "moneyMongEntityManagerFactory",
        transactionManagerRef = "moneyMongTransactionManager"
)
@Configuration(proxyBeanMethods = false)
public class JpaConfig {
    @Bean
    @ConfigurationProperties(prefix = "datasource.db")
    public DataSource moneyMongDataSource() {
        return new HikariDataSource();
    }

    @Primary
    @Bean
    @DependsOn("moneyMongDataSource")
    public DataSource moneyMongLazyDataSource(
            @Qualifier("moneyMongDataSource") DataSource dbDataSource
    ) {
        return new LazyConnectionDataSourceProxy(dbDataSource);
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean moneyMongEntityManagerFactory(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("moneyMongLazyDataSource") DataSource lazyDataSource
    ) {
        return entityManagerFactoryBuilder
                .dataSource(lazyDataSource)
                .packages("com.moneymong")
                .properties(jpaProperties())
                .persistenceUnit("moneymong")
                .build();
    }

    @Bean
    public PlatformTransactionManager moneyMongTransactionManager(
            @Qualifier("moneyMongEntityManagerFactory") EntityManagerFactory moneyMongEntityManagerFactory
    ) {
        return new JpaTransactionManager(moneyMongEntityManagerFactory);
    }

    private Map<String, Object> jpaProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());

        return properties;
    }
}
