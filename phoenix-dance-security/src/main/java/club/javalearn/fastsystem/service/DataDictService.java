package club.javalearn.fastsystem.service;

import club.javalearn.fastsystem.common.Message;
import club.javalearn.fastsystem.model.DataDict;
import org.springframework.data.domain.Pageable;

import javax.xml.crypto.Data;

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
}
