package club.javalearn.fastsystem.config;

import club.javalearn.fastsystem.properties.ShiroProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:39
 * Description: No Description
 */
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
@Order(-1)
public class ShiroCoreConfig {
}
