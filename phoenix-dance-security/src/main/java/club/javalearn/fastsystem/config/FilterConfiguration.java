package club.javalearn.fastsystem.config;

import club.javalearn.fastsystem.web.filter.XssEscapeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/25
 * Time: 上午10:17
 * Description: No Description
 */
@Configuration
public class FilterConfiguration {

    /**
     *
     * xss过滤拦截器
     *
     * 使用 jsoup 是可以过滤掉所有的html标签，但是也有个问题，比如
     * 参数是: {“name”:”<html”,”passwd”:”12345”},过滤后的结果是：{“name”:”
     * 因为没有找到标签的结束位置，所以就会过滤掉后面所有的参数。
     * 这样就会导致 controller 获取参数的时候异常。
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean initXssFilterBean = new FilterRegistrationBean();
        initXssFilterBean.setFilter(new XssEscapeFilter());
        initXssFilterBean.setOrder(1);
        initXssFilterBean.setEnabled(true);
        initXssFilterBean.addUrlPatterns("/*");
        initXssFilterBean.setDispatcherTypes(DispatcherType.REQUEST);
        return initXssFilterBean;
    }
}
