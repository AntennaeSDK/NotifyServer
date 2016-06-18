/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.antennae.server.notifier.config;

import java.util.Properties;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.antennae.common.entitybeans.AppInfo;
import org.antennae.common.entitybeans.AuthToken;
import org.antennae.common.entitybeans.Channel;
import org.antennae.common.entitybeans.ChannelClient;
import org.antennae.common.entitybeans.DeviceInfo;
import org.antennae.common.entitybeans.Message;
import org.antennae.common.entitybeans.User;
import org.antennae.server.notifier.ws.WebSocketConfig;
import org.hibernate.SessionFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableTransactionManagement
@Import( {H2Config.class, GcmXmppConfig.class, WebSocketConfig.class} )
public class ApplicationConfig {

    public static final Class[] entityClasses = { User.class, DeviceInfo.class, AppInfo.class, AuthToken.class,Channel.class, Message.class, ChannelClient.class };
	
	@Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
	
    private Properties getHibernateProperties() {
    	Properties properties = new Properties();
    	properties.put("hibernate.show_sql", "true");
    	properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    	return properties;
    }
    
    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
    	LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
    	sessionBuilder.addProperties(getHibernateProperties());
    	
//    	sessionBuilder.addAnnotatedClasses(User.class);
//    	sessionBuilder.addAnnotatedClass(DeviceInfo.class);
//    	sessionBuilder.addAnnotatedClass(AppInfo.class);
//    	sessionBuilder.addAnnotatedClass(AuthToken.class);
//    	sessionBuilder.addAnnotatedClass(Channel.class);
//    	sessionBuilder.addAnnotatedClass(Message.class);
//    	sessionBuilder.addAnnotatedClass(ChannelClient.class);

        for( Class clz : entityClasses ){
            sessionBuilder.addAnnotatedClass(clz);
        }
    	
    	return sessionBuilder.buildSessionFactory();
    }




/*    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        //em.setPackagesToScan(new String[] { "packages.to.scan" });

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        em.setJpaProperties(properties);

        return em;
    }*/

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager( SessionFactory sessionFactory) {
		
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}
	
	@Bean
    public ServletContextAware endpointExporterInitializer(final ApplicationContext applicationContext) {
        return new ServletContextAware() {
            @Override
            public void setServletContext(ServletContext servletContext) {
                ServerEndpointExporter exporter = new ServerEndpointExporter();
                exporter.setApplicationContext(applicationContext);
                exporter.afterPropertiesSet();
            }
        };
    }
}
