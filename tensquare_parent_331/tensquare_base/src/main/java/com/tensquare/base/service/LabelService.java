package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private LabelDao labelDao;

    //增
    public void save(Label label) {
        long id = idWorker.nextId();
        label.setId(id + "");
        labelDao.save(label);
    }

    //删
    public void delete(String id) {
        labelDao.deleteById(id);
    }

    //改
    public void update(String id, Label label) {
        label.setId(id);
        labelDao.save(label);
    }

    //查 根据id
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    //查 查询所有
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    //查 条件查询
    public List<Label> findByCondition(Label label) {
        return labelDao.findAll(generateSpec(label));
    }

    //查 分页条件查询
    public PageResult<Label> findPageByCondition(Integer page, Integer size, Label label) {
        //new PageRequest(page,size);

        Page<Label> pageList = labelDao.findAll(generateSpec(label), PageRequest.of(page - 1, size));

        return new PageResult<Label>(pageList.getTotalElements(), pageList.getContent());
    }

    private Specification<Label> generateSpec(Label label) {
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> pList = new ArrayList<>();

                //labelname条件
                if (!StringUtils.isEmpty(label.getLabelname())) {  //拼装条件
                    Predicate p = criteriaBuilder.like(root.get("labelname").as(String.class), label.getLabelname());
                    pList.add(p);
                }
                //state条件
                if (!StringUtils.isEmpty(label.getState())) {  //拼装条件
                    Predicate p = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    pList.add(p);
                }
                //recommend条件
                if (!StringUtils.isEmpty(label.getRecommend())) {  //拼装条件
                    Predicate p = criteriaBuilder.equal(root.get("recommend").as(String.class), label.getRecommend());
                    pList.add(p);
                }
                if (pList.isEmpty()) {
                    return null;
                }
                return criteriaBuilder.and(pList.toArray(new Predicate[pList.size()]));

            }
        };
    }

}
