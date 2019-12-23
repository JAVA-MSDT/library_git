package com.epam.library.model.configuration;

import com.epam.library.util.PropertiesExtractor;
import com.epam.library.util.constant.BeanNameHolder;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.io.IOException;

@Slf4j
@Configuration
@Profile(value = BeanNameHolder.TESTING_PROFILE)
public class TestConfigurator {

    private final static String HIBERNATE_PROPERTIES = "src/main/resources/properties/hibernate.properties";

    @Bean(value = BeanNameHolder.DATA_SOURCE_BEAN)
    public DataSource getDataSource() {
        EmbeddedPostgres postgres = null;
        try {
            postgres = EmbeddedPostgres.builder().start();

        } catch (IOException e) {
            log.error("Unable to instantiate EmbeddedPostgres", e);
        }
        assert postgres != null;
        return postgres.getPostgresDatabase();
    }


    @Bean(value = BeanNameHolder.ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        log.info("ENTITY_MANAGER_FACTORY_BEAN: " + this.getClass().getSimpleName());
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(BeanNameHolder.BASE_PACKAGE);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(PropertiesExtractor.getProperties(HIBERNATE_PROPERTIES));
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        // em.afterPropertiesSet();
        return em;
    }

    @Bean(BeanNameHolder.TRANSACTION_MANAGER_BEAN)
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }


    @Bean(BeanNameHolder.FLYWAY_BEAN)
    public Flyway flyway() {
        log.info("Flyway From Test configuration is running");
        FluentConfiguration configuration = Flyway.configure().dataSource(entityManagerFactory().getDataSource());
        configuration.baselineOnMigrate(true);
        return new Flyway(configuration);
    }


















   /* @Autowired
    @Qualifier(BeanNameHolder.ENTITY_MANAGER_FACTORY_BEAN)
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @Bean(BeanNameHolder.FLYWAY_BEAN)
    public Flyway flyway() {
        log.info("ConnectionConfigurator Flyway is running");
        FluentConfiguration configuration = Flyway.configure().dataSource(entityManagerFactory.getDataSource());
        configuration.baselineOnMigrate(true);
        return new Flyway(configuration);
    }*/
}
