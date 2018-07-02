package club.javalearn.fastsystem.security.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:40
 * Description: No Description
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * 原FormAuthenticationFilter的认证方法
     * @param request 请求对象
     * @param response 响应对象
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 在这里进行验证码的校验

        // 从session获取正确验证码
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        // 取出session的验证码（正确的验证码）
        String validateCode = (String) session.getAttribute("validateCode");

        // 取出页面的验证码
        // 输入的验证和session中的验证进行对比
        String randomCode = httpServletRequest.getParameter("randomCode");
        if (randomCode != null && validateCode != null && !randomCode.equals(validateCode)) {
            // 如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
            httpServletRequest.setAttribute("shiroLoginFailure", "randomCodeError");
            // 拒绝访问，不再校验账号和密码
            return true;
        }
        return super.onAccessDenied(request, response);
    }
}
