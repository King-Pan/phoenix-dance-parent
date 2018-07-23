package club.javalearn.fastsystem.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/23
 * Time: 上午11:30
 * Description: 分类管理
 */
@Data
@Entity
@Table(name = "sys_data_dict")
public class DataDict implements Serializable {

    /**
     * 主键字段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 父类ID
     */
    private Long parentId;
    /**
     * 类型
     */
    private String type;
    /**
     * 字典code
     */
    private String dictCode;
    /**
     * 字典值
     */
    @Column(length = 4000)
    private String dictValue;
    /**
     * 排序字段
     */
    private int orderNum;
}
