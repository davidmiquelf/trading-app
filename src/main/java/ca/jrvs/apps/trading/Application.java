package ca.jrvs.apps.trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {
    JdbcTemplateAutoConfiguration.class,
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class})
public class Application {

  public static void main(String... args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.setWebApplicationType(WebApplicationType.SERVLET);
    app.run(args);
  }
}
