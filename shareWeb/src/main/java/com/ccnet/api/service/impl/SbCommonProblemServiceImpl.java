package com.ccnet.api.service.impl;

import com.ccnet.api.dao.SbCommonProblemDao;
import com.ccnet.api.entity.SbCommonProblem;
import com.ccnet.api.service.SbCommonProblemService;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sbCommonProblemService")

public class SbCommonProblemServiceImpl extends BaseServiceImpl<SbCommonProblem> implements SbCommonProblemService
{
    @Autowired
    SbCommonProblemDao sbCommonProblemDao;
    @Override
    protected BaseDao<SbCommonProblem> getDao() {
        return sbCommonProblemDao;
    }

    @Override
    public Page<SbCommonProblem> findProblomByPage(SbCommonProblem sbCommonProblem, Page<SbCommonProblem> page, Dto paramDto) {
        return sbCommonProblemDao.findProblomByPage(sbCommonProblem, page, paramDto);
    }

    @Override
    public SbCommonProblem findProblemByID(SbCommonProblem sbCommonProblem) {
        return sbCommonProblemDao.findProblemByID(sbCommonProblem);
    }

    @Override
    public boolean saveProblem(SbCommonProblem sbCommonProblem) {
        return sbCommonProblemDao.saveProblem(sbCommonProblem);
    }

    @Override
    public boolean editProblem(SbCommonProblem sbCommonProblem) {
        return  sbCommonProblemDao.editProblem(sbCommonProblem);
    }

    @Override
    public boolean trashProblem(SbCommonProblem problem) {
        return  sbCommonProblemDao.trashProblem(problem);
    }

    @Override
    public List<SbCommonProblem> findProblemList(SbCommonProblem sbCommonProblem) {
        return  sbCommonProblemDao.findProblemList(sbCommonProblem);
    }
}
