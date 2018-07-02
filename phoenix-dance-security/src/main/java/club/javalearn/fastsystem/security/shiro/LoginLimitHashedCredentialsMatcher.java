package club.javalearn.fastsystem.security.shiro;

import club.javalearn.fastsystem.properties.ShiroProperties;
import club.javalearn.fastsystem.utils.Constants;
import lombok.Getter;
import lombok.Setter;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:40
 * Description: No Description
 */
@Getter
@Setter
@Component
public class LoginLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {


    @Autowired
    private CacheManager cacheManager;

    private Ehcache passwordRetryCache;

    @Autowired
    private ShiroProperties shiroProperties;


    public LoginLimitHashedCredentialsMatcher() {
        //设置加密次数
        this.setHashIterations(10);
        //设置加密算法
        this.setHashAlgorithmName("sha-1");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        //获取缓存对象
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");

        String username = (String)token.getPrincipal();
        //retry count + 1
        Element element = passwordRetryCache.get(username);
        if(element == null) {
            element = new Element(username , new AtomicInteger(0));
            passwordRetryCache.put(element);
        }
        AtomicInteger retryCount = (AtomicInteger)element.getObjectValue();
        if(retryCount.incrementAndGet() > Constants.TRY_LOGIN_TIME) {
            //if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry count
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
