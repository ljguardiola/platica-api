import com.gubrit.platica.PlaticaApplication;
import com.gubrit.platica.config.TestLocalContainersConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPlaticaApplication {

  public static void main(String[] args) {
    SpringApplication.from(PlaticaApplication::main)
                     .with(TestLocalContainersConfiguration.class)
                     .run(args);
  }

}
