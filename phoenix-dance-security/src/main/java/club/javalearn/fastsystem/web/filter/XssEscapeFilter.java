package club.javalearn.fastsystem.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/25
 * Time: 上午10:15
 * Description: No Description
 */
public class XssEscapeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig)  {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //后面会有 XssHttpServletRequestWrapper 的代码。这个类是自己定义的
        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
    }
}
