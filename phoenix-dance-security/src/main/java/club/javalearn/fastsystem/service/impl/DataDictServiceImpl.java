package club.javalearn.fastsystem.service.impl;

import club.javalearn.fastsystem.common.BootstrapMessage;
import club.javalearn.fastsystem.common.Message;
import club.javalearn.fastsystem.model.DataDict;
import club.javalearn.fastsystem.repository.DataDictRepository;
import club.javalearn.fastsystem.service.DataDictService;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/23
 * Time: 下午12:36
 * Description: No Description
 */
@Slf4j
@Service
public class DataDictServiceImpl implements DataDictService {


    @Autowired
    private DataDictRepository dataDictRepository;

    @Override
    public Message<DataDict> getPageList(DataDict dataDict, Pageable pageable) {
        BootstrapMessage<DataDict> message = new BootstrapMessage<>();
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<DataDict> dataDictPage = dataDictRepository.findAll(new Specification<DataDict>() {
            @Override
            public Predicate toPredicate(Root<DataDict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> nickNamePath = root.get("type");
                Path<String> userNamePath = root.get("dictValue");
                List<Predicate> wherePredicate = new ArrayList<>();
                if (dataDict != null) {
                    if (StringUtils.isNoneBlank(dataDict.getType())) {
                        wherePredicate.add(cb.like(nickNamePath, "%" + dataDict.getType() + "%"));
                    }
                    if (StringUtils.isNoneBlank(dataDict.getDictValue())) {
                        wherePredicate.add(cb.like(userNamePath, "%" + dataDict.getDictValue() + "%"));
                    }
                }
                Predicate[] predicates = new Predicate[]{};
                //这里可以设置任意条查询条件
                if (CollectionUtils.isNotEmpty(wherePredicate)) {
                    query.where(wherePredicate.toArray(predicates));
                }
                //这种方式使用JPA的API设置了查询条件，所以不需要再返回查询条件Predicate给Spring Data Jpa，故最后return null;即可。
                return null;
            }
        }, pageRequest);

        message.setLimit(dataDictPage.getSize());
        message.setRows(dataDictPage.getContent());
        message.setTotal(dataDictPage.getTotalElements());
        message.setStart(dataDictPage.getNumber());
        log.debug("limit=" + dataDictPage.getSize() + ",total=" +
                dataDictPage.getTotalElements() + ",start=" +
                dataDictPage.getNumber() + ",numberOfElements=" +
                dataDictPage.getNumberOfElements());
        return message;
    }

    @Override
    public List<DataDict> findAll() {
        List<Sort.Order> orders=new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "type"));
        orders.add(new Sort.Order(Sort.Direction.ASC, "orderNum"));
        return dataDictRepository.findAll(new Sort(orders));
    }

    @Override
    public DataDict findById(Long id) {
        return dataDictRepository.getById(id);
    }

    @Override
    public DataDict save(DataDict dataDict) {
        return dataDictRepository.save(dataDict);
    }

    @Override
    public void deleteDataDict(Long id) {
        dataDictRepository.deleteById(id);
    }

    @Override
    public List<DataDict> findAllType() {
        List<Object[]> list = dataDictRepository.findAllType();
        List<DataDict> dataDictList = new ArrayList<>();
        DataDict dataDict;
        for (Object[] l:list){
            System.out.println(Arrays.toString(l));
            dataDict = new DataDict();
            if(l[0]!=null && StringUtils.isNoneBlank(l[0].toString())){
                dataDict.setType(l[0].toString());
            }
            if(l[1]!=null && StringUtils.isNoneBlank(l[1].toString())){
                dataDict.setDictValue(l[1].toString());
            }
            if(l[2]!=null && StringUtils.isNoneBlank(l[2].toString())){
                dataDict.setId(Long.parseLong(l[2].toString()));
            }
            dataDictList.add(dataDict);
        }
        return dataDictList;
    }
}
