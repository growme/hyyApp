package com.ccnet.cps.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.UserInfoDao;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbAdvertInfoDao;
import com.ccnet.cps.dao.SbAdvertPicDao;
import com.ccnet.cps.entity.SbAdvertInfo;
import com.ccnet.cps.entity.SbAdvertPic;
import com.ccnet.cps.service.SbAdvertiseInfoService;

/**
 * 广告业务
 * 
 * @author Administrator
 *
 */
@Service("sbAdvertiseInfoService")
public class SbAdvertiseInfoServiceImpl extends BaseServiceImpl<SbAdvertInfo> implements SbAdvertiseInfoService {
	@Autowired
	private SbAdvertInfoDao sbAdvertInfoDao;
	@Autowired
	private SbAdvertPicDao sbAdvertPicDao;
	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public Page findSbAdvertiseInfoByPage(SbAdvertInfo sbAdvertInfo, Page<SbAdvertInfo> page, Dto pdDto) {
		Page SbAdvertiseInfoPage = sbAdvertInfoDao.findSbContentInfoByPage(sbAdvertInfo, page, pdDto);
		List<SbAdvertInfo> advertList = SbAdvertiseInfoPage.getResults();
		// 处理操作人
		List<Integer> userIds = sbAdvertInfoDao.getValuesFromField(advertList, "userId");
		Map<Integer, UserInfo> userMap = userInfoDao.getUserMapByIds(userIds);
		if (!CPSUtil.checkListBlank(advertList)) {
			for (SbAdvertInfo advertInfo : advertList) {
				if (CPSUtil.isNotEmpty(advertInfo.getUserId())) {
					advertInfo.setUserInfo(userMap.get(advertInfo.getUserId()));
				}
				// 设置图片
				// setAdvertPics(advertInfo);
				advertInfo.setAdTagName(CPSUtil.getCodeName("ADV_TAG", advertInfo.getAdTag()));

			}
		}
		return SbAdvertiseInfoPage;
	}

	@Override
	protected BaseDao<SbAdvertInfo> getDao() {
		return sbAdvertInfoDao;
	}

	@Override
	public SbAdvertInfo getSbAdvertInfoByID(Integer adId) {
		SbAdvertInfo sbAdvertInfo = sbAdvertInfoDao.getSbAdvertInfoByID(adId);
		if (CPSUtil.isNotEmpty(sbAdvertInfo)) {
			// 设置广告图片列表
			setAdvertPics(sbAdvertInfo);
		}
		return sbAdvertInfo;
	}

	@Override
	public boolean saveSbAdvertInfo(SbAdvertInfo sbAdvertInfo) {
		boolean flag = false;
		if (CPSUtil.isNotEmpty(sbAdvertInfo.getAdId())) {
			SbAdvertInfo old = sbAdvertInfoDao.getSbAdvertInfoByID(sbAdvertInfo.getAdId());
			sbAdvertInfo.setCreateTime(old.getCreateTime());
			// 处理文章预览图片保存
			dealAdvertPic(sbAdvertInfo);
			flag = sbAdvertInfoDao.editSbAdvertInfo(sbAdvertInfo);
		} else {
			dealAdvertPic(sbAdvertInfo);
			flag = sbAdvertInfoDao.saveSbAdvertInfo(sbAdvertInfo);
		}
		return flag;
	}

	@Override
	public boolean trashSbAdvertInfo(String advertIds) {
		boolean temp = false;
		try {
			if (CPSUtil.isNotEmpty(advertIds)) {
				SbAdvertInfo advertInfo = null;
				String cids[] = advertIds.split(",");
				for (String cid : cids) {
					advertInfo = getSbAdvertInfoByID(Integer.valueOf(cid));
					// 删除文章图片
					sbAdvertPicDao.trashPicByAdvertId(advertInfo.getAdCode());
					sbAdvertInfoDao.trashSbContentInfo(advertInfo);
				}
				temp = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 随机获取广告
	 */
	public List<SbAdvertInfo> getRandomAdvert(int i) {
		List<SbAdvertInfo> randomlist = new ArrayList<SbAdvertInfo>();
		List<SbAdvertInfo> list = findList(new SbAdvertInfo());
		if (list.size() > i) {
			Random random = new Random();
			for (int x = 0; x < i; x++) {
				int j = random.nextInt(list.size());
				randomlist.add(list.get(j));
			}
		} else {
			return list;
		}
		return randomlist;
	}

	/**
	 * 设置广告图片
	 * 
	 * @param content
	 * @throws @author
	 *             Jackie Wang
	 * @date 2017-9-5
	 */
	private void setAdvertPics(SbAdvertInfo advert) {
		// 获取广告唯一码对应的图片
		List<SbAdvertPic> pics = sbAdvertPicDao.findPicsByAdvertCode(advert.getAdCode());
		if (CPSUtil.listNotEmpty(pics)) {
			advert.setPicList(pics);
			// 给文章设置一张列表预览图
			advert.setAdvertPic(pics.get(0).getAdvertPic());
			// 拼接字符串 广告图片列表
			int picIndex = 0;
			StringBuffer sbBuffer = new StringBuffer("");
			List<String> picList = sbAdvertPicDao.getValuesFromField(pics, "advertPic");
			if (CPSUtil.listNotEmpty(picList)) {
				for (String pic : picList) {
					sbBuffer.append(pic);
					if (picIndex < picList.size()) {
						sbBuffer.append(",");
					}
				}
				advert.setAdvertPics(sbBuffer.toString());
			}
			advert.setPicList(pics);
		}
	}

	/**
	 * 处理图片 加入广告图片列表
	 * 
	 * @param SbAdvertInfo
	 * @return
	 */
	public boolean dealAdvertPic(SbAdvertInfo advertInfo) {
		boolean temp = false;
		String advertPics = advertInfo.getAdvertPics();
		if (CPSUtil.isNotEmpty(advertPics)) {
			int picIndex = 0;
			SbAdvertPic sapic = null;
			String cpics[] = advertPics.split(",");
			for (String pic : cpics) {
				if (CPSUtil.isNotEmpty(pic)) {
					sapic = new SbAdvertPic();
					sapic.setAdvertId(advertInfo.getAdCode());
					sapic.setCreateTime(new Date());
					sapic.setAdvertPic(pic);
					sapic.setSortNum(picIndex);
					if (!checkContentPicExist(advertInfo.getAdCode(), pic)) {
						temp = sbAdvertPicDao.saveSbAdvertPic(sapic);
					}
					picIndex++;
				}
			}
		}
		return temp;
	}

	/**
	 * 检查图片是否已经存在
	 * 
	 * @param contentId
	 * @param picPath
	 * @return
	 */
	public boolean checkContentPicExist(String advertCode, String picPath) {
		boolean temp = false;
		List<SbAdvertPic> picList = sbAdvertPicDao.findPicsByAdvertCode(advertCode);
		if (CPSUtil.listNotEmpty(picList)) {
			for (SbAdvertPic sapic : picList) {
				if (sapic.getAdvertPic().equals(picPath)) {
					temp = true;
					break;
				}
			}
		}
		return temp;
	}

	@Override
	public boolean trashSbAdvertInfo(SbAdvertInfo sbAdvertInfo) {
		sbAdvertPicDao.trashPicByAdvertId(sbAdvertInfo.getAdCode());
		return sbAdvertInfoDao.trashSbContentInfo(sbAdvertInfo);
	}

	/**
	 * 根据广告类型获取广告列表
	 * 
	 * @return
	 */
	@Override
	public List<SbAdvertInfo> getSbAdvertInfoByType(int typeId) {
		List<SbAdvertInfo> advertInfos = null;
		advertInfos = sbAdvertInfoDao.getAdvertInfosByType(typeId);
		// 处理操作人
		List<Integer> userIds = sbAdvertInfoDao.getValuesFromField(advertInfos, "userId");
		Map<Integer, UserInfo> userMap = userInfoDao.getUserMapByIds(userIds);
		if (!CPSUtil.checkListBlank(advertInfos)) {
			for (SbAdvertInfo _advertInfo : advertInfos) {
				if (CPSUtil.isNotEmpty(_advertInfo.getUserId())) {
					_advertInfo.setUserInfo(userMap.get(_advertInfo.getUserId()));
				}
				// 设置图片
				setAdvertPics(_advertInfo);
			}
		}
		return advertInfos;
	}

	/**
	 * 查询所有广告信息 并设置广告图
	 */
	@Override
	public List<SbAdvertInfo> findSbAdvertiseInfos() {
		List<SbAdvertInfo> advertInfos = sbAdvertInfoDao.findList(new SbAdvertInfo());
		// 处理操作人
		List<Integer> userIds = sbAdvertInfoDao.getValuesFromField(advertInfos, "userId");
		Map<Integer, UserInfo> userMap = userInfoDao.getUserMapByIds(userIds);
		if (!CPSUtil.checkListBlank(advertInfos)) {
			for (SbAdvertInfo advertInfo : advertInfos) {
				if (CPSUtil.isNotEmpty(advertInfo.getUserId())) {
					advertInfo.setUserInfo(userMap.get(advertInfo.getUserId()));
				}
				// 设置图片
				// setAdvertPics(advertInfo);
			}
		}
		return advertInfos;
	}

	/**
	 * 批量修改状态
	 * 
	 * @param adIds
	 * @param state
	 * @return
	 */
	public boolean updateAdvertiseStateById(String adIds, Integer state) {
		boolean temp = false;
		List<String> adList = new ArrayList<String>();
		if (CPSUtil.isNotEmpty(adIds)) {
			String ids[] = adIds.split(",");
			for (String _id : ids) {
				adList.add(_id);
			}

			// 处理批量更新
			temp = sbAdvertInfoDao.updateAdvertiseStateById(adList, state);
		}
		return temp;
	}

	@Override
	public List<SbAdvertInfo> getAdvertInfosByAdIdAndDate(Integer userId, Integer asId, String date) {
		if(StringUtils.isBlank(date)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.format(new Date());
		}
		return sbAdvertInfoDao.getAdvertInfosByAdIdAndDate(userId, asId, date);
	}

}
