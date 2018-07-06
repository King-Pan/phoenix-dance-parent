package club.javalearn.fastsystem.service;

import club.javalearn.fastsystem.common.Message;
import club.javalearn.fastsystem.common.ServerResponse;
import club.javalearn.fastsystem.model.User;
import club.javalearn.fastsystem.parameter.UserInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:47
 * Description: No Description
 */
public interface UserService {
    /**
     * 通过用户名查找用户
     *
     * @param userName 用户名
     * @return 用户信息
     */
    User findByUserName(String userName);


    /**
     * 通过用户编号查询用户
     *
     * @param userId 用户编号
     * @return 用户信息
     */
    User findByUserId(Long userId);

    /**
     * 新增或者修改用户
     *
     * @param userInfo 用户信息
     * @return 修改后的用户信息
     */
    User save(UserInfo userInfo);

    /**
     *  更新用户最后登录时间
     * @param user 用户信息
     */
    void updateLastLoginTime(User user);

    /**
     * 分页查询用户信息
     *
     * @param userInfo 查询参数
     * @param pageable 分页参数
     * @return 用户信息列表
     */
    Message<User> getPageList(final UserInfo userInfo, Pageable pageable);

    /**
     * 批量删除用户
     *
     * @param userIds 用户id集合
     * @return 成功个数
     */
    int deleteUsers(List<Long> userIds);
}
