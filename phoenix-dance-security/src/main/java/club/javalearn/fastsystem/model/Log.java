package club.javalearn.fastsystem.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:43
 * Description: No Description
 */
@Table(name = "sys_log")
@Entity
@Data
public class Log implements Serializable {

    public static Long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 访问时间
     */
    private Date createDate;

    /**
     * 时长
     */
    private Long time;

    /**
     * 用户ID
     */
    private String userName;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 访问IP
     */
    private String ip;

    /**
     * 参数
     */
    private String params;

    /**
     * 访问路径
     */
    private String url;

    /**
     * 方法类型
     */
    private String method;

    /**
     * 状态
     */
    private String status;

    /**
     * 返回值
     */
    @Column(length = 4000)
    private String result;

    /**
     * 错误消息
     */
    @Column(length = 4000)
    private String errorMessage;

}
