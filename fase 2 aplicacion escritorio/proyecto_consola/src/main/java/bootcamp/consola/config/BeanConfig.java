package bootcamp.consola.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

// esta clase configura el jdbctemplate para que pueda emplear el datasource para conectarse a la BBDD
// así trae la configuración desde el archivo application.properties
// y se lo pasa a jdbctemplate para se conecte y consulte la BBDD

@Configuration
public class BeanConfig {

	@Bean
	JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
