package club.javalearn.fastsystem.service.impl;

import club.javalearn.fastsystem.common.BootstrapMessage;
import club.javalearn.fastsystem.common.Message;
import club.javalearn.fastsystem.model.QUser;
import club.javalearn.fastsystem.model.User;
import club.javalearn.fastsystem.parameter.UserInfo;
import club.javalearn.fastsystem.repository.UserRepository;
import club.javalearn.fastsystem.service.UserService;
import club.javalearn.fastsystem.utils.Constants;
import club.javalearn.fastsystem.utils.PasswordHelper;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:50
 * Description: No Description
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public User findByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        return user;
    }

    @Override
    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public User save(UserInfo userInfo) {
        User user = userInfo.convertUser();
        if(user.getUserId()==null){
            //生成加密密码
            passwordHelper.encryptPassword(user,true);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setStatus(Constants.DEFAULT_STATUS);
        }
        return userRepository.save(user);
    }

    @Override
    public void updateLastLoginTime(User user) {
        //更新登录信息
        user.setLastLoginTime(new Date());
        userRepository.save(user);
    }


    @Override
    public Message<User> getPageList(UserInfo userInfo, Pageable pageable) {
        BootstrapMessage<User> message = new BootstrapMessage<>();
        QUser qUser = QUser.user;
        User user = userInfo.convertUser();
        BooleanExpression predicate = null;
        if (user.getStatus() != null && !Constants.NOT_SELECT.equals(user.getStatus())) {
            predicate = qUser.status.endsWith(user.getStatus());
        }
        if (StringUtils.isNoneBlank(user.getUserName())) {
            if (predicate != null) {
                predicate.and(qUser.userName.like(user.getUserName()));
            } else {
                predicate = qUser.userName.like(user.getUserName());
            }

        }
        if (StringUtils.isNoneBlank(user.getNickName())) {
            if (predicate != null) {
                predicate.and(qUser.nickName.like(user.getNickName()));
            } else {
                predicate = qUser.nickName.like(user.getNickName());
            }
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "updateTime"));
        sort.and(new Sort(Sort.Direction.ASC, "status"));
        sort.and(new Sort(Sort.Direction.ASC, "userId"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<User> userPage = userRepository.findAll(predicate, pageRequest);
        message.setLimit(userPage.getSize());
        message.setRows(userPage.getContent());
        message.setTotal(userPage.getTotalElements());
        message.setStart(userPage.getNumber());
        log.debug("limit=" + userPage.getSize() + ",total=" + userPage.getTotalElements() + ",start=" + userPage.getNumber() + ",numberOfElements=" + userPage.getNumberOfElements());
        return message;
    }

    @Override
    public int deleteUsers(List<Long> userIds) {
        return userRepository.deleteUserByUserIds(userIds);
    }
}
