package club.javalearn.fastsystem.common;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:48
 * Description: No Description
 */
@Data
public class BootstrapMessage<T> implements Message<T> {
    private List<T> rows;
    private Integer start;
    private Integer limit;
    private Long total;
}
