package com.ccnet.admin.bh.service;

import com.ccnet.admin.bh.entity.PsAppInfo;
import com.ccnet.admin.bh.entity.SbCommonProblem;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;

public interface SbCommonProblemService extends BaseService<SbCommonProblem> {

    Page<SbCommonProblem> findProblomByPage(SbCommonProblem sbCommonProblem, Page<SbCommonProblem> page, Dto paramDto);

    SbCommonProblem findProblemByID(SbCommonProblem sbCommonProblem);

    boolean saveProblem(SbCommonProblem sbCommonProblem);

    boolean editProblem(SbCommonProblem sbCommonProblem);

    boolean trashProblem(SbCommonProblem problem);
}
