package club.javalearn.fastsystem.service.impl;

import club.javalearn.fastsystem.common.BootstrapMessage;
import club.javalearn.fastsystem.common.Message;
import club.javalearn.fastsystem.model.Role;
import club.javalearn.fastsystem.parameter.RoleInfo;
import club.javalearn.fastsystem.repository.RoleRepository;
import club.javalearn.fastsystem.service.RoleService;
import club.javalearn.fastsystem.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/10
 * Time: 下午5:46
 * Description: No Description
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Message<Role> getPageList(RoleInfo roleInfo, Pageable pageable) {
        BootstrapMessage<Role> message = new BootstrapMessage<>();
        final Role role = roleInfo.convertRole();
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "updateTime"));
        sort.and(new Sort(Sort.Direction.ASC, "status"));
        sort.and(new Sort(Sort.Direction.ASC, "roleId"));

        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Role> rolePage = roleRepository.findAll(new Specification<Role>(){
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> roleNamePath = root.get("roleName");
                Path<String> roleCodePath = root.get("roleCode");
                Path<String> statusPath = root.get("status");
                List<Predicate> wherePredicate = new ArrayList<>();
                if(role!=null){
                    if(StringUtils.isNoneBlank(role.getRoleName())){
                        wherePredicate.add(cb.like(roleNamePath,"%"+role.getRoleName()+"%"));
                    }
                    if(StringUtils.isNoneBlank(role.getRoleCode())){
                        wherePredicate.add(cb.like(roleCodePath,"%"+role.getRoleCode()+"%"));
                    }
                    if(StringUtils.isNoneBlank(role.getStatus()) && !Constants.NOT_SELECT.equals(role.getStatus())){
                        wherePredicate.add(cb.equal(statusPath,role.getStatus()));
                    }
                }

                Predicate[] predicates = new Predicate[]{};
                //这里可以设置任意条查询条件
                if (CollectionUtils.isNotEmpty(wherePredicate)){
                    query.where(wherePredicate.toArray(predicates));
                }
                //这种方式使用JPA的API设置了查询条件，所以不需要再返回查询条件Predicate给Spring Data Jpa，故最后return null;即可。
                return null;
            }
        },pageRequest);

        message.setLimit(rolePage.getSize());
        message.setRows(rolePage.getContent());
        message.setTotal(rolePage.getTotalElements());
        message.setStart(rolePage.getNumber());
        log.debug("limit=" + rolePage.getSize() + ",total=" + rolePage.getTotalElements() + ",start=" + rolePage.getNumber() + ",numberOfElements=" + rolePage.getNumberOfElements());
        return message;
    }

    @Override
    public int deleteRoles(List<Long> roleIds) {
        return roleRepository.deleteRoleByIds(roleIds);
    }

    @Override
    public Role save(RoleInfo roleInfo) {
        Role role = roleInfo.convertRole();
        Role result;
        //修改
        if(role.getRoleId()!=null){
            Role r = roleRepository.getOne(role.getRoleId());
            r.setRemark(role.getRemark());
            r.setRoleCode(role.getRoleCode());
            r.setRoleName(role.getRoleName());
            r.setUpdateTime(new Date());
            result = roleRepository.save(r);
        }else{
            role.setCreateTime(new Date());
            role.setStatus(Constants.DEFAULT_STATUS);
            result = roleRepository.save(role);
        }
        return result;
    }

    @Override
    public void modifyStatus(Long roleId, String status) {
        roleRepository.modifyStatus(roleId,status);
    }
}
