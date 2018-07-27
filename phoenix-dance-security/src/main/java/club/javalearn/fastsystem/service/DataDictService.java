package club.javalearn.fastsystem.service;

import club.javalearn.fastsystem.common.Message;
import club.javalearn.fastsystem.model.DataDict;
import com.sun.org.apache.bcel.internal.generic.DADD;
import org.springframework.data.domain.Pageable;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/23
 * Time: 下午12:24
 * Description: No Description
 */
public interface DataDictService {
    /**
     * 分页查询用户信息
     *
     * @param dataDict 查询参数
     * @param pageable 分页参数
     * @return 分类信息列表
     */
    Message<DataDict> getPageList(final DataDict dataDict, Pageable pageable);


    List<DataDict> findAll();

    /**
     * 通过ID查询字典信息
     * @param id 字典ID
     * @return 字典信息
     */
    DataDict findById(Long id);

    /**
     * 新增或者修改分类信息
     * @param dataDict 分类信息
     * @return 分类信息
     */
    DataDict save(DataDict dataDict);

    /**
     * 通过分类ID删除分类信息
     * @param id 分类ID
     */
    void deleteDataDict(Long id);


    List<DataDict> findAllType();
}
