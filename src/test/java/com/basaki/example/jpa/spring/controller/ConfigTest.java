package com.basaki.example.jpa.spring.controller;

import javax.sql.DataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {ConfigTest.ContextConfiguration.class})
public class ConfigTest {

    @Autowired
    private Environment env;

    @TestConfiguration
    static class ContextConfiguration {
        @Bean(name = "dataSource")
        public DataSource dataSourceTest() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(EmbeddedDatabaseType.H2)
                    .setScriptEncoding("UTF-8")
                    .addScripts("schema.sql", "data.sql")
                    .build();
        }

        @Bean
        public JpaVendorAdapter jpaVendorAdapterTest() {
            HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
            adapter.setShowSql(false);
            adapter.setDatabase(Database.H2);
            adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
            // adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
            adapter.setGenerateDdl(true);
            return adapter;
        }
    }


    @Test
    public void test() {

    }
}
