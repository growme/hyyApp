package com.ccnet.admin.cps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ccnet.cps.entity.FinancialInfo;
import com.ccnet.core.controller.BaseController;
/**
 * 财务管理
 * @author 1234
 *
 */
@Controller
@RequestMapping("/backstage/financial/")
public class FinancialController extends BaseController<FinancialInfo> {

}
