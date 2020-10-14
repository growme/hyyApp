package com.ccnet.api.service;

import com.ccnet.api.entity.SbCommonProblem;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;

import java.util.List;

public interface SbCommonProblemService extends BaseService<SbCommonProblem> {

    Page<SbCommonProblem> findProblomByPage(SbCommonProblem sbCommonProblem, Page<SbCommonProblem> page, Dto paramDto);

    SbCommonProblem findProblemByID(SbCommonProblem sbCommonProblem);

    boolean saveProblem(SbCommonProblem sbCommonProblem);

    boolean editProblem(SbCommonProblem sbCommonProblem);

    boolean trashProblem(SbCommonProblem problem);

    List<SbCommonProblem> findProblemList(SbCommonProblem sbCommonProblem);
}
