package club.javalearn.fastsystem.aspect;

import club.javalearn.fastsystem.model.Log;
import club.javalearn.fastsystem.model.User;
import club.javalearn.fastsystem.service.LogService;
import club.javalearn.fastsystem.utils.FastJsonUtils;
import club.javalearn.fastsystem.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午6:00
 * Description: No Description
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogService logService;

    /**
     * 定义切面
     */
    @Pointcut("@annotation(club.javalearn.fastsystem.aspect.SysLog)")
    public void serviceAspect() {
    }

    @Before("serviceAspect()")
    public void doBefore(JoinPoint joinPoint) {
        //前置通知
        if (log.isDebugEnabled()) {
            log.debug("-->: 前置通知");
        }
    }


    @AfterReturning(pointcut = "serviceAspect()", returning = "rvt")
    public Object doAfterReturning(JoinPoint joinPoint, Object rvt) {
        if (log.isDebugEnabled()) {
            log.debug("-->: AfterReturning");
        }
        try {
            //记录日志
        } catch (Exception e) {
            log.error("记录日志出错", e);
        }
        return rvt;
    }

    @Around(value = "serviceAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object rvt;
        Log logInfo = new Log();
        long beginTime = 0L;
        if (log.isDebugEnabled()) {
            log.debug("-->: 开始记录日志");
        }
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            SysLog syslog = method.getAnnotation(SysLog.class);
            if (syslog != null) {
                //注解上的描述
                logInfo.setModuleName(syslog.module());
                logInfo.setOperation(syslog.operation());
            }

            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if (user != null) {
                //记录用户名
                logInfo.setUserName(user.getUserName());
            }
            logInfo.setCreateDate(new Date());

            //请求的参数
            Object[] args = joinPoint.getArgs();
            try {
                logInfo.setParams(FastJsonUtils.getBeanToJson(args));
            } catch (Exception e) {
                log.error("记录日志解析参数失败 : " + e.getMessage());
            }
            //获取request
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //获取请求方法
            logInfo.setMethod(request.getMethod());
            //获取用户请求地址
            logInfo.setUrl(request.getRequestURI());
            //设置IP地址
            logInfo.setIp(IpUtils.getIpAddr(request));
            beginTime = System.currentTimeMillis();
            rvt = joinPoint.proceed();
            //执行时长(毫秒)
            logInfo.setTime(System.currentTimeMillis() - beginTime);
            logInfo.setResult(FastJsonUtils.getBeanToJson(rvt));
            logInfo.setStatus("成功");
            //记录成功
        } catch (Throwable throwable) {
            //记录失败
            logInfo.setResult("访问失败");
            logInfo.setErrorMessage(throwable.getMessage());
            logInfo.setStatus("失败");
            logInfo.setTime(System.currentTimeMillis() - beginTime);
            throw throwable;
        }
        if (log.isDebugEnabled()) {
            log.debug("-->: 结束记录日志");
        }
        logService.saveLog(logInfo);
        return rvt;
    }

    @After("serviceAspect()")
    public void doAfter(JoinPoint joinPoint) {
        if (log.isDebugEnabled()) {
            log.debug("After...");
        }
    }
}
