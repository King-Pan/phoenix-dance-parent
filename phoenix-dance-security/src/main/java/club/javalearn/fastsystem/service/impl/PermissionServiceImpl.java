package club.javalearn.fastsystem.service.impl;

import club.javalearn.fastsystem.model.Permission;
import club.javalearn.fastsystem.parameter.PermissionInfo;
import club.javalearn.fastsystem.repository.PermissionRepository;
import club.javalearn.fastsystem.service.PermissionService;
import club.javalearn.fastsystem.utils.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Date: 2018/7/12
 * Time: 下午3:35
 * Description: No Description
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> getList() {
        return getList("", Constants.NOT_SELECT);
    }

    @Override
    public List<Permission> getList(String permissionName, String status) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderNum");
        return permissionRepository.findAll(new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> permissionNamePath = root.get("resourceName");
                Path<String> statusPath = root.get("status");
                List<Predicate> wherePredicate = new ArrayList<>();
                if (StringUtils.isNoneBlank(permissionName)) {
                    wherePredicate.add(cb.like(permissionNamePath, "%" + permissionName + "%"));
                }
                if (StringUtils.isNoneBlank(status) && !Constants.NOT_SELECT.equals(status)) {
                    wherePredicate.add(cb.equal(statusPath, status));
                }
                Predicate[] predicates = new Predicate[]{};
                //这里可以设置任意条查询条件
                if (CollectionUtils.isNotEmpty(wherePredicate)) {
                    query.where(wherePredicate.toArray(predicates));
                }
                return null;
            }
        },sort);
    }

    @Override
    public Permission save(PermissionInfo permissionInfo) {
        Permission result;
        Permission permission = permissionInfo.convertPermission();
        if(permission.getPermissionId()!=null){
            Permission p = permissionRepository.getOne(permission.getPermissionId());
            p.setUpdateTime(new Date());
            p.setIcon(permission.getIcon());
            p.setExpression(permission.getExpression());
            p.setUrl(permission.getUrl());
            p.setDescription(permission.getDescription());
            p.setPermissionType(permission.getPermissionType());
            p.setPermissionName(permission.getPermissionName());
            result = permissionRepository.save(p);
        }else{
            permission.setCreateTime(new Date());
            permission.setStatus(Constants.DEFAULT_STATUS);
            result = permissionRepository.save(permission);
        }
        return result;
    }

    @Override
    public void updateStatus(List<Long> permissionIds,String status) {
        permissionRepository.updateStatus(permissionIds,status);
    }
}
