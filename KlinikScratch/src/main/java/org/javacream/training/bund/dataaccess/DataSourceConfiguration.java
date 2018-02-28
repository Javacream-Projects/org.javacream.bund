package org.javacream.training.bund.dataaccess;

import java.sql.Connection;
import java.util.HashMap;

import javax.sql.DataSource;

import org.javacream.training.bund.util.SimpleTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManager", transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public class DataSourceConfiguration {

	@Autowired
	private SimpleTenant tenant;
	@Autowired
	private Environment environment;

	/**
	 * Adds all available datasources to datasource map.
	 *
	 * @return datasource of current context
	 */
	@Bean
	@Primary
	public DataSource dataSource() {
		DataSourceRouter router = new DataSourceRouter();

		final HashMap<Object, Object> dataSourcesMap = new HashMap<>(3);
		boolean defaultDatabaseIsSet = false;
		for (String profile : environment.getActiveProfiles()) {
			if (!"prod".equals(profile)) {
				String url = environment.getProperty(profile + ".datasource.url");
				String username = environment.getProperty(profile + ".datasource.username");
				String password = environment.getProperty(profile + ".datasource.password");
				String driverClassName = environment.getProperty(profile + ".datasource.driver-class-name");
				DataSource dataSource = DataSourceBuilder.create().driverClassName(driverClassName).password(password)
						.username(username).url(url).build();
				createTable(dataSource);
				dataSourcesMap.put(profile, dataSource);
				if (!defaultDatabaseIsSet) {
					router.setDefaultTargetDataSource(dataSource);
					defaultDatabaseIsSet = true;
				}
			}
		}

		router.setTargetDataSources(dataSourcesMap);
		router.setDefaultTargetDataSource(dataSourcesMap.values().iterator().next());
		return router;
	}

	private void createTable(DataSource dataSource) {
		try (Connection con = dataSource.getConnection()){
			con.createStatement().execute("create table SIMPLE_DATA (dataId number, message varchar(256), primary key(dataId))");
		}
		catch (Exception e) {
		}
	}

	class DataSourceRouter extends AbstractRoutingDataSource {
		@Override
		protected Object determineCurrentLookupKey() {
			try {
				return tenant.getTenantId();
				
			} catch (Exception e) {
				//HACK: Hibernate needs Database Metadata using AutoConfiguration during startup. So we must use a 
				return "K1";
			}

		}

	}

	@Bean
	JpaProperties jpaProperties() {
		JpaProperties props = new JpaProperties();
		props.setGenerateDdl(false);
		props.setShowSql(true);
		return props;
	}
}
