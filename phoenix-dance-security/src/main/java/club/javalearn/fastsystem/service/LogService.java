package club.javalearn.fastsystem.service;

import club.javalearn.fastsystem.model.Log;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午6:00
 * Description: No Description
 */
@Transactional(rollbackFor = RuntimeException.class)
public interface LogService {
    /**
     * 保存日志
     * @param log 日志信息
     */
    void saveLog(Log log);
}
