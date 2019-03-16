package mjchang.spring_api.configure;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = { "mjchang.spring_api.repository"})
public class CustomConfigure {
	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(new String[] { "mjchang.spring_api.model" });
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(customProperties());
        return em;
    }
 
    @Bean
    public DataSource getDataSource() {
    	
    	return DataSourceBuilder.create()
    			.driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1")
                .username("sa")
                .password("")
                .build();
    }
 
    private final Properties customProperties() {
    	
        Properties customProperties = new Properties();
        customProperties.setProperty("spring.jpa.generate-ddl", "true");
        customProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        customProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        customProperties.setProperty("hibernate.show_sql", "true");
 
        return customProperties;
    }
}
