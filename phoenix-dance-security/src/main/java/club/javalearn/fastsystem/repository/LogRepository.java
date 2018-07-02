package club.javalearn.fastsystem.repository;

import club.javalearn.fastsystem.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午6:01
 * Description: 日志持久化对象
 */
public interface LogRepository extends JpaRepository<Log,Long>,QuerydslPredicateExecutor<Log> {

}
