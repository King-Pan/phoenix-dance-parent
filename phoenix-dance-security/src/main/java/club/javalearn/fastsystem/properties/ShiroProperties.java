package club.javalearn.fastsystem.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:37
 * Description: No Description
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {

    PasswordProperties password = new PasswordProperties();

}
