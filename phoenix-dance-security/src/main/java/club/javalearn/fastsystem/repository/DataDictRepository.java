package club.javalearn.fastsystem.repository;

import club.javalearn.fastsystem.model.DataDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/23
 * Time: 下午12:22
 * Description: No Description
 */
public interface DataDictRepository extends JpaRepository<DataDict,Long>,QuerydslPredicateExecutor<DataDict>,JpaSpecificationExecutor<DataDict> {
}
