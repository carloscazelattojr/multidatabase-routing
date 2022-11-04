package br.com.carlos.multidatabase.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DatasourceConfiguration {

    @Bean
    @ConfigurationProperties("master.datasource")
    public DataSourceProperties masterDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("master.datasource.configuration")
    public HikariDataSource masterDataSource(DataSourceProperties masterDataSourceProperties) {
        HikariDataSource hikariDataSource = masterDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        hikariDataSource.setAutoCommit(false);
        return hikariDataSource;
    }


    @Bean
    @ConfigurationProperties("slave.datasource")
    public DataSourceProperties slaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("slave.datasource.configuration")
    public HikariDataSource slaveDataSource(DataSourceProperties slaveDataSourceProperties) {
        HikariDataSource hikariDataSource = slaveDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        hikariDataSource.setAutoCommit(false);
        return hikariDataSource;
    }

    @Bean
    @Primary
    public TransactionRoutingDataSource routingDataSource(DataSource masterDataSource, DataSource slaveDataSource) {
        TransactionRoutingDataSource routingDataSource = new TransactionRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.READ_WRITE, masterDataSource);
        dataSourceMap.put(DataSourceType.READ_ONLY, slaveDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }


    @Bean
    public BeanPostProcessor dialectProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof HibernateJpaVendorAdapter) {
                    ((HibernateJpaVendorAdapter) bean).getJpaDialect().setPrepareConnection(false);
                }
                return bean;
            }
        };
    }
}
