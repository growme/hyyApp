package com.ccnet.admin.cps.controller;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ccnet.admin.bh.entity.SbCashLogExportVo;
import com.ccnet.core.common.*;
import com.ccnet.cps.dao.SbUserMoneyDao;
import com.ccnet.cps.entity.*;
import com.ccnet.cps.service.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.bh.utils.wxUtils.WeiXinPayUtils;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户提现记录
 * 
 * @author JackieWang
 *
 */
@Controller("SbCashLogController")
@RequestMapping("/backstage/cash/")
public class SbCashLogController extends BaseController<SbCashLog> {

	// 静态路径
	public static String GO_PAY_URL = "/backstage/cash/gopay";
	public static String GO_CHECK_URL = "/backstage/cash/check";

	@Autowired
	SbCashLogService sbCashLogService;
	@Autowired
	SbPayLogService sbPayLogService;
	@Autowired
	SbMoneyCountService sbMoneyCountService;
	@Autowired
	SbContentVisitLogService sbVisitLogService;
	@Autowired
	SbUserMoneyService sbUserMoneyService;

	@Autowired
	SbUserMoneyDao sbUserMoneyDao;

	/*
	 * 提现记录
	 */
	@RequestMapping("index")
	public ModelAndView cashIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<SbCashLog> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			SbCashLog cashLog = new SbCashLog();
			page = sbCashLogService.findSbCashLogByPage(cashLog, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("stateList", PayState.values());
			mav.addObject("state", paramDto.getAsString("state"));
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "会员提现管理");
			mav.setViewName("admincps/jsp/money/user_cash_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/*
	 * 审核提现
	 */
	@RequestMapping("check")
	public ModelAndView checkCash() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.BUTTON) || isAuthedReq(ResourceTypes.FUNC)) {
			Integer cash_id = paramDto.getAsInteger("cash_id");
			if (CPSUtil.isNotEmpty(cash_id)) {
				// 获取提现信息
				SbCashLog cashLog = sbCashLogService.findSbCashLogById(cash_id, paramDto);
				mav.addObject("stateList", PayState.allState());
				mav.addObject("cashLog", cashLog);
				mav.addObject(Const.CT_MENU_NAV, "会员提现审核");
				mav.setViewName("admincps/jsp/money/check_user_cash");
			} else {
				mav.setViewName(Const.NO_AUTHORIZED_URL);
			}
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/*
	 * 提现支付
	 */
	@RequestMapping("gopay")
	public ModelAndView payCash() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.BUTTON) || isAuthedReq(ResourceTypes.FUNC)) {
			Integer cash_id = paramDto.getAsInteger("cash_id");
			if (CPSUtil.isNotEmpty(cash_id)) {
				SbCashLog cashLog = sbCashLogService.findSbCashLogById(cash_id, paramDto);
				mav.addObject("cashLog", cashLog);
				mav.addObject(Const.CT_MENU_NAV, "提现支付");
				mav.setViewName("admincps/jsp/money/pay_user_cash");
			} else {
				mav.setViewName(Const.NO_AUTHORIZED_URL);
			}
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 提交审核
	 * 
	 * @return
	 */
	@RequestMapping(value = "check/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveCheckCash() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_CHECK_URL))) {
			try {
				Dto paramDto = getParamAsDto();
				Integer cashId = paramDto.getAsInteger("ucId");
				Integer state = paramDto.getAsInteger("state");
				String remark = paramDto.getAsString("remark");
				if (StringHelper.checkParameter(cashId, state, remark)) {
					// 处理数据存储
					if (sbCashLogService.updateUserCashState(cashId, state, remark)) {
						if (state.toString().equals(PayState.drawback.getPayStateId().toString())||state
						.toString().equals(PayState.failured.getPayStateId().toString())||
						state.toString().equals(PayState.refused.getPayStateId().toString())) {
							SbCashLog cashLog = sbCashLogService.findSbCashLogById(cashId, paramDto);
							String rate = CPSUtil.getParamValue("PEPEAT_CONVERSION_RATIO");
							double money = cashLog.getCmoney() * Double.valueOf(rate);
							/*SbMoneyCount moneyCount = new SbMoneyCount();
							moneyCount.setCreateTime(new Date());
							moneyCount.setUserId(cashLog.getUserId());
							moneyCount.setUmoney(money);
							moneyCount.setmType(AwardType.darwback.getAwardId());
							moneyCount.setVindex(0);
							sbMoneyCountService.saveSbMoneyCountInfo(moneyCount);*/
							//金币退回余额
							SbUserMoney userMoney = new SbUserMoney();
							userMoney.setUserId(cashLog.getUserId());
							userMoney.setProfitsMoney(money);
							userMoney.setTmoney(money);
							userMoney.setUpdateTime(new Date());
							userMoney.setLastProDate(userMoney.getUpdateTime());
							sbUserMoneyDao.insertOrUpdate(userMoney);

							ar.setSucceedMsg(Const.SAVE_SUCCEED);
						} else {
							ar.setFailMsg(Const.DATA_UNEXIST);
						}
						ar.setSucceedMsg(Const.SAVE_SUCCEED);
					} else {
						ar.setFailMsg(Const.SAVE_FAIL);
					}

				} else {
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}

			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	/**
	 * 提交支付
	 * 
	 * @return
	 */
	@RequestMapping(value = "pay/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes savePayCash() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_PAY_URL))) {
			try {
				Dto paramDto = getParamAsDto();
				UserInfo userInfo = getCurrentUser();
				Integer cashId = paramDto.getAsInteger("cashId");
				Dto cashDto = new BaseDto();
				SbCashLog cashLog = sbCashLogService.findSbCashLogById(cashId, cashDto);

				// String payAccount = paramDto.getAsString("payAccount");
				// String accountName = paramDto.getAsString("accountName");
				// String serialCode = paramDto.getAsString("serialCode");
				// String payTime = paramDto.getAsString("payTime");
				String remark = paramDto.getAsString("remark");
				String payAccount = cashLog.getPayAccount();
				String accountName = cashLog.getAccountName();
				if (StringHelper.checkParameter(cashId, payAccount, accountName)) {
					// 记录不为空 且状态为审核通过
					if (CPSUtil.isNotEmpty(cashLog)
							&& cashLog.getState().equals(PayState.underReview.getPayStateId())) {
						// 处理数据存储
						SbPayLog payLog = new SbPayLog();
						payLog.setAccountName(accountName);
						payLog.setPayAccount(payAccount);
						payLog.setCreateTime(new Date());
						payLog.setPayTime(new Date());
						payLog.setPayMoney(cashLog.getCmoney());
						payLog.setOperater(userInfo.getLoginAccount());
						if (CPSUtil.isEmpty(remark)) {
							remark = "已给会员【" /*
												 * + cashLog.getMemberInfo().
												 * getLoginAccount() +
												 */ + "】支付【" + cashLog.getCmoney() + "】元佣金";
							payLog.setRemark(remark);
						} else {
							payLog.setRemark(remark);
						}
						payLog.setUcId(cashId);
						//todo 支付宝提现
						System.out.println("提现方式：{}，校验；{}"+cashLog.getPayType()+PayType.ebank.getPayId().equals(cashLog.getPayType()));
						if (PayType.ebank.getPayId().equals(cashLog.getPayType())){//微信
							Map<String, Object> map = WeiXinPayUtils.withdrawals(accountName, cashLog.getPayAccount(), "",
									cashLog.getCmoney().toString());
							if (map.get("code").equals("0")) {
								String str = "";
								Object obj = map.get("value");
								if (obj != null) {
									str = obj.toString();
								}
								if (str.length() > 9) {
									ar.setFailMsg(str.substring(str.indexOf("<![CDATA[") + 9, str.indexOf("]]>")));
								} else {
									ar.setFailMsg(Const.SAVE_FAIL);
								}
								return ar;
							}
							payLog.setAlipayCode(map.get("partner_trade_no").toString());
						}else{
							//支付宝
							payLog.setAlipayCode(cashLog.getUcId().toString());
						}

						int flag = sbPayLogService.insert(payLog);
						if (flag > 0) {
							if (sbCashLogService.updateUserCashState(cashId, PayState.prepaid.getPayStateId(),
									"佣金支付成功")) {
								//提现成功后取消给邀请人返现
								//sbCashLogService.updateUserCashMoney(cashLog.getUserId(), cashLog.getCmoney());
								ar.setSucceedMsg(Const.SAVE_SUCCEED);
							} else {
								ar.setFailMsg(Const.SAVE_FAIL);
							}
						} else {
							ar.setFailMsg(Const.SAVE_FAIL);
						}

					} else {
						ar.setFailMsg(Const.DATA_UNEXIST);
					}

				} else {
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}

			} catch (Exception e) {
				e.printStackTrace();
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	@RequestMapping(value = "drawback", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes drawback() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_PAY_URL))) {
			try {
				Dto paramDto = getParamAsDto();
				Integer cashId = paramDto.getAsInteger("cashId");
				Dto cashDto = new BaseDto();
				SbCashLog cashLog = sbCashLogService.findSbCashLogById(cashId, cashDto);
				String payAccount = cashLog.getPayAccount();
				String accountName = cashLog.getAccountName();
				if (StringHelper.checkParameter(cashId, payAccount, accountName)) {
					// 记录不为空 且状态为审核通过
					if (CPSUtil.isNotEmpty(cashLog) && cashLog.getState().equals(PayState.failured.getPayStateId())) {
						sbCashLogService.updateUserCashState(cashId, PayState.drawback.getPayStateId(), "佣金退还成功");
						SbMoneyCount moneyCount = new SbMoneyCount();
						moneyCount.setCreateTime(new Date());
						moneyCount.setUserId(cashLog.getUserId());
						moneyCount.setUmoney(cashLog.getCmoney());
						moneyCount.setmType(AwardType.darwback.getAwardId());
						moneyCount.setVindex(0);
						sbMoneyCountService.saveSbMoneyCountInfo(moneyCount);
						ar.setSucceedMsg(Const.SAVE_SUCCEED);
					} else {
						ar.setFailMsg(Const.DATA_UNEXIST);
					}

				} else {
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}

			} catch (Exception e) {
				e.printStackTrace();
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	/*
	 * 收益明细
	 */
	@RequestMapping("goMoney")
	public ModelAndView goMoney() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.BUTTON) || isAuthedReq(ResourceTypes.FUNC)) {
			Integer cash_id = paramDto.getAsInteger("cash_id");
			if (CPSUtil.isNotEmpty(cash_id)) {
				SbCashLog cash = sbCashLogService.findSbCashLogById(cash_id, paramDto);
				Page<SbMoneyCount> page = newPage(paramDto);
				SbMoneyCount sbMoneyCount = new SbMoneyCount();
				sbMoneyCount.setUserId(cash.getUserId());
				page = sbMoneyCountService.getMoneyCountByPage(sbMoneyCount, page, paramDto);
				mav.addObject("pm", page);
				mav.addObject("awardTypes", AwardType.getAwardType());
				mav.addObject("end_date", paramDto.getAsString("end_date"));
				mav.addObject("start_date", paramDto.getAsString("start_date"));
				mav.addObject("cash_id", cash_id);
				mav.addObject(Const.CT_MENU_NAV, "收益明细");
				mav.setViewName("admincps/jsp/money/pay_user_money");
			} else {
				mav.setViewName(Const.NO_AUTHORIZED_URL);
			}
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/*
	 * 收益明细
	 */
	@RequestMapping("goContentLog")
	public ModelAndView goContentLog() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Integer cash_id = paramDto.getAsInteger("cash_id");
		String userIP = paramDto.getAsString("userIP");
		if (CPSUtil.isNotEmpty(cash_id)) {
			SbCashLog cash = sbCashLogService.findSbCashLogById(cash_id, paramDto);
			Page<SbContentVisitLog> page = newPage(paramDto);
			SbContentVisitLog visitLog = new SbContentVisitLog();
			Integer accountState = paramDto.getAsInteger("accountState");
			if (CPSUtil.isNotEmpty(accountState)) {
				visitLog.setAccountState(accountState);
			}
			// if (CPSUtil.isNotEmpty(userIP)) {
			//
			// } else {
			visitLog.setUserId(cash.getUserId());
			visitLog.setAccountState(1);
			visitLog.setRequestIp(userIP);
			// }
			page = sbVisitLogService.findSbContentVisitLogByPage(visitLog, page, paramDto);// 分页查询
			mav.addObject("pm", page);
			mav.addObject("memberId", paramDto.getAsString("memberId"));
			mav.addObject("userIP", userIP);
			mav.addObject("accountState", accountState);
			mav.addObject("channelList", ShareType.values());
			mav.addObject("stlist", AccountState.values());
			mav.addObject("awardTypes", AwardType.getAwardType());
			mav.addObject("cash_id", cash_id);
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject(Const.CT_MENU_NAV, "收益明细");
			mav.setViewName("admincps/jsp/money/visit_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	@RequestMapping("export")
	public void export(HttpServletResponse response, HttpServletRequest request) {
		Dto paramDto = getParamAsDto();
		SbCashLog sbCashLog = new SbCashLog();
		List<SbCashLog> list = sbCashLogService.getTotalCashByUser(sbCashLog, paramDto);
		String excelName = "提现记录.xls";
		String[] headers = {"序号(必填)","收款方支付宝账号(必填)", "收款方姓名(必填)", "金额(必填,单位:元)", "备注(选填)"};

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("提现记录");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 16);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell0 = row0.createCell(0);
		cell0.setCellValue("支付宝批量付款文件模板（前面两行请勿删除）");
		cell0.setCellStyle(cellStyle);
		CellRangeAddress region = new CellRangeAddress(0,1,0,4);
		sheet.addMergedRegion(region);
		//设置表头
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont cellFont = wb.createFont();
		cellFont.setFontHeightInPoints((short) 12);
		style.setFont(cellFont);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFRow header = sheet.createRow(2);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = header.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(style);
		}
		try{
			Field[] fields = SbCashLogExportVo.class.getDeclaredFields();
			// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
			for (int i = 0; i < list.size(); i++) {
				header = sheet.createRow((int) i + 3);
				SbCashLog cashLog = list.get(i);

				for (int j = 0; j < fields.length; j++) {
					HSSFCell cell = header.createCell(j);
					String fileName = fields[j].getName();

					if ("index".equals(fileName)){
						cell.setCellValue(i+1);
					}else{
						//设置对象的访问权限，保证对private的属性的访问
						fileName = fileName.substring(0, 1).toUpperCase() + fileName.substring(1);
						Method method = cashLog.getClass().getMethod("get" +fileName);
						Object invoke = method.invoke(cashLog);
						cell.setCellValue(invoke==null?"":invoke.toString());
					}
					cell.setCellStyle(style);
				}
			}
			for (int k = 0; k < fields.length; k++) {
				sheet.autoSizeColumn(k);
			}
			// 处理中文不能自动调整列宽的问题
			setSizeColumn(sheet, fields.length);
			//获取输出流
			OutputStream output = response.getOutputStream();
			response.reset();
			//设置分区中文名
			response.setContentType("application/octet-stream");
			String userAgent = request.getHeader("User-Agent").toLowerCase();
			// 火狐和Ie的下载文件名乱码问题
			if (userAgent.contains("MSIE")||userAgent.contains("TRIDENT")||userAgent.contains("LIKE GECKO")
					|| userAgent.contains("Mozilla")) {
				response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(excelName, "UTF-8")); // 火狐和Ie的下载文件名乱码问题
				System.out.println("ie----------------------------" + userAgent.toString());
				//其他浏览器下载文件名乱码问题
			}else{
				response.setHeader("Content-Disposition", "attachment;filename="+ new String(excelName.getBytes("utf-8"), "ISO8859-1")); //其他浏览器下载文件名乱码问题
				System.out.println("chrom----------------------------" + userAgent.toString());
			}
			wb.write(output);
			output.flush();
			output.close();
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("导出提现记录时异常："+e.getMessage());
		}

	}

	// 自适应宽度(中文支持)
	private static void setSizeColumn(HSSFSheet sheet, int size) throws Exception{
		for (int columnNum = 0; columnNum < size; columnNum++) {
			int columnWidth = sheet.getColumnWidth(columnNum) / 256;
			for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
				HSSFRow currentRow;
				//当前行未被使用过
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				} else {
					currentRow = sheet.getRow(rowNum);
				}

				if (currentRow.getCell(columnNum) != null) {
					HSSFCell currentCell = currentRow.getCell(columnNum);
					if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
						int length = currentCell.getStringCellValue().getBytes("UTF-8").length;
						if (columnWidth < length) {
							columnWidth = length;
						}
					}
				}
			}
			sheet.setColumnWidth(columnNum, columnWidth * 256);
		}
	}
}
