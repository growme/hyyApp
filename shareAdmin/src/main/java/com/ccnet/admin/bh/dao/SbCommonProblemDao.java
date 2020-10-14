package com.ccnet.admin.bh.dao;

import cn.ffcs.memory.BeanListHandler;
import com.ccnet.admin.bh.entity.SbCommonProblem;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("sbCommonProblemDao")
public class SbCommonProblemDao extends BaseDao<SbCommonProblem> {

    public Page<SbCommonProblem> findProblomByPage(SbCommonProblem sbCommonProblem, Page<SbCommonProblem> page, Dto paramDto) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList();
        String queryParam = paramDto.getAsString("queryParam");
        String title = paramDto.getAsString("title");
        String status = paramDto.getAsString("status");
        String type = paramDto.getAsString("type");
        sql.append("select * from ").append(this.getTableName(SbCommonProblem.class));
        List<String> whereColumns = this.memory.parseWhereColumns(params, SbCommonProblem.class, sbCommonProblem);
        if (CollectionUtils.isNotEmpty(whereColumns)) {
            sql.append(" where ").append(this.appendAnd(whereColumns));
        }

        if (CPSUtil.isNotEmpty(queryParam)) {
            this.appendWhere(sql);
            sql.append(" and (title like ? or type like ? )");
            params.add("%"+queryParam+"%");
            params.add("%"+queryParam+"%");
        }
        if (CPSUtil.isNotEmpty(title)) {
            this.appendWhere(sql);
            sql.append(" and title like ? ");
            params.add("%"+title+"%");
        }
        if (CPSUtil.isNotEmpty(status)) {
            this.appendWhere(sql);
            sql.append(" and status = ? ");
            params.add(status);
        }
        if (CPSUtil.isNotEmpty(type)) {
            this.appendWhere(sql);
            sql.append(" and type = ? ");
            params.add(type);
        }

        sql.append(" order by type asc,sort asc,create_time desc");
        super.queryPager(sql.toString(), new BeanListHandler(SbCommonProblem.class), params, page);
        return page;
    }

    public SbCommonProblem findProblemByID(SbCommonProblem sbCommonProblem) {
        return this.find(sbCommonProblem);
    }

    public boolean saveProblem(SbCommonProblem sbCommonProblem) {
        return this.insert(sbCommonProblem)>0;
    }

    public boolean editProblem(SbCommonProblem sbCommonProblem) {
        return this.update(sbCommonProblem, "id") > 0;

    }

    public boolean trashProblem(SbCommonProblem problem) {
        return this.delete(problem)>0;
    }
}
