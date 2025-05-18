package ru.yandex.practicum.filmorate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@Slf4j
@SpringBootApplication
public class FilmorateApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(FilmorateApplication.class, args);

//		checkJdbcTemplateBean(context);

	}
/*
	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create()
				.url("jdbc:h2:file:./db/filmorate")
				.username("sa")
				.password("password")
				.driverClassName("org.h2.Driver")
				.build();
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	private static void checkJdbcTemplateBean(ApplicationContext context) {
		try {
			JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
			log.info("JdbcTemplate bean successfully created: {}", jdbcTemplate);
			jdbcTemplate.execute("SELECT 1"); // Проверка подключения
			log.info("Database connection test successful");
		} catch (Exception e) {
			log.error("JdbcTemplate bean creation failed", e);
		}
	}

 */
}
