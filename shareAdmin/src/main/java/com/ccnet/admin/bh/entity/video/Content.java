package com.ccnet.admin.bh.entity.video;

import java.util.List;

public class Content {

	private String abstract1;
    private List<Action_list> action_list;
    private int aggr_type;
    private boolean allow_download;
    private int article_sub_type;
    private int article_type;
    private String article_url;
    private int article_version;
    private int ban_comment;
    private long behot_time;
    private int bury_count;
    private long cell_flag;
    private int cell_layout_style;
    private int cell_type;
    private int comment_count;
    private String content_decoration;
    private long cursor;
    private int digg_count;
    private String display_url;
    private List<Filter_words> filter_words;
    private Forward_info forward_info;
    private long group_id;
    private int group_source;
    private boolean has_m3u8_video;
    private int has_mp4_video;
    private boolean has_video;
    private int hot;
    private int ignore_web_transform;
    private String interaction_data;
    private boolean is_stick;
    private boolean is_subject;
    private long item_id;
    private int item_version;
    private String keywords;
    private String label;
    private Label_extra label_extra;
    private int label_style;
    private int level;
    private Log_pb log_pb;
    private Media_info media_info;
    private String media_name;
    private int need_client_impr_recycle;
    private long publish_time;
    private long read_count;
    private int repin_count;
    private String rid;
    private int share_count;
    private Share_info share_info;
    private String share_url;
    private boolean show_dislike;
    private boolean show_portrait;
    private boolean show_portrait_article;
    private String source;
    private int source_icon_style;
    private String source_open_url;
    private String stick_label;
    private int stick_style;
    private String tag;
    private long tag_id;
    private int tip;
    private String title;
    private Ugc_recommend ugc_recommend;
    private String url;
    private User_info user_info;
    private int user_repin;
    private int user_verified;
    private String verified_content;
    private int video_style;
    private List<Large_image_list> large_image_list;
    
    private String action_extra;
    private boolean ban_danmaku;
    private Control_panel control_panel;
    private int danmaku_count;
    private int group_flags;
    private Middle_image middle_image;
    private String play_auth_token;
    private String play_biz_token;
    private int share_type;
    private Video_detail_info video_detail_info;
    private int video_duration;
    private String video_id;
    
	public List<Large_image_list> getLarge_image_list() {
		return large_image_list;
	}
	public void setLarge_image_list(List<Large_image_list> large_image_list) {
		this.large_image_list = large_image_list;
	}
	public String getAction_extra() {
		return action_extra;
	}
	public void setAction_extra(String action_extra) {
		this.action_extra = action_extra;
	}
	public boolean isBan_danmaku() {
		return ban_danmaku;
	}
	public void setBan_danmaku(boolean ban_danmaku) {
		this.ban_danmaku = ban_danmaku;
	}
	public Control_panel getControl_panel() {
		return control_panel;
	}
	public void setControl_panel(Control_panel control_panel) {
		this.control_panel = control_panel;
	}
	public int getDanmaku_count() {
		return danmaku_count;
	}
	public void setDanmaku_count(int danmaku_count) {
		this.danmaku_count = danmaku_count;
	}
	public int getGroup_flags() {
		return group_flags;
	}
	public void setGroup_flags(int group_flags) {
		this.group_flags = group_flags;
	}
	public Middle_image getMiddle_image() {
		return middle_image;
	}
	public void setMiddle_image(Middle_image middle_image) {
		this.middle_image = middle_image;
	}
	public String getPlay_auth_token() {
		return play_auth_token;
	}
	public void setPlay_auth_token(String play_auth_token) {
		this.play_auth_token = play_auth_token;
	}
	public String getPlay_biz_token() {
		return play_biz_token;
	}
	public void setPlay_biz_token(String play_biz_token) {
		this.play_biz_token = play_biz_token;
	}
	public int getShare_type() {
		return share_type;
	}
	public void setShare_type(int share_type) {
		this.share_type = share_type;
	}
	public Video_detail_info getVideo_detail_info() {
		return video_detail_info;
	}
	public void setVideo_detail_info(Video_detail_info video_detail_info) {
		this.video_detail_info = video_detail_info;
	}
	public int getVideo_duration() {
		return video_duration;
	}
	public void setVideo_duration(int video_duration) {
		this.video_duration = video_duration;
	}
	public String getVideo_id() {
		return video_id;
	}
	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}
	public String getAbstract1() {
		return abstract1;
	}
	public void setAbstract1(String abstract1) {
		this.abstract1 = abstract1;
	}
	public List<Action_list> getAction_list() {
		return action_list;
	}
	public void setAction_list(List<Action_list> action_list) {
		this.action_list = action_list;
	}
	public int getAggr_type() {
		return aggr_type;
	}
	public void setAggr_type(int aggr_type) {
		this.aggr_type = aggr_type;
	}
	public boolean isAllow_download() {
		return allow_download;
	}
	public void setAllow_download(boolean allow_download) {
		this.allow_download = allow_download;
	}
	public int getArticle_sub_type() {
		return article_sub_type;
	}
	public void setArticle_sub_type(int article_sub_type) {
		this.article_sub_type = article_sub_type;
	}
	public int getArticle_type() {
		return article_type;
	}
	public void setArticle_type(int article_type) {
		this.article_type = article_type;
	}
	public String getArticle_url() {
		return article_url;
	}
	public void setArticle_url(String article_url) {
		this.article_url = article_url;
	}
	public int getArticle_version() {
		return article_version;
	}
	public void setArticle_version(int article_version) {
		this.article_version = article_version;
	}
	public int getBan_comment() {
		return ban_comment;
	}
	public void setBan_comment(int ban_comment) {
		this.ban_comment = ban_comment;
	}
	public long getBehot_time() {
		return behot_time;
	}
	public void setBehot_time(long behot_time) {
		this.behot_time = behot_time;
	}
	public int getBury_count() {
		return bury_count;
	}
	public void setBury_count(int bury_count) {
		this.bury_count = bury_count;
	}
	public long getCell_flag() {
		return cell_flag;
	}
	public void setCell_flag(long cell_flag) {
		this.cell_flag = cell_flag;
	}
	public int getCell_layout_style() {
		return cell_layout_style;
	}
	public void setCell_layout_style(int cell_layout_style) {
		this.cell_layout_style = cell_layout_style;
	}
	public int getCell_type() {
		return cell_type;
	}
	public void setCell_type(int cell_type) {
		this.cell_type = cell_type;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public String getContent_decoration() {
		return content_decoration;
	}
	public void setContent_decoration(String content_decoration) {
		this.content_decoration = content_decoration;
	}
	public long getCursor() {
		return cursor;
	}
	public void setCursor(long cursor) {
		this.cursor = cursor;
	}
	public int getDigg_count() {
		return digg_count;
	}
	public void setDigg_count(int digg_count) {
		this.digg_count = digg_count;
	}
	public String getDisplay_url() {
		return display_url;
	}
	public void setDisplay_url(String display_url) {
		this.display_url = display_url;
	}
	public List<Filter_words> getFilter_words() {
		return filter_words;
	}
	public void setFilter_words(List<Filter_words> filter_words) {
		this.filter_words = filter_words;
	}
	public Forward_info getForward_info() {
		return forward_info;
	}
	public void setForward_info(Forward_info forward_info) {
		this.forward_info = forward_info;
	}
	public long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public int getGroup_source() {
		return group_source;
	}
	public void setGroup_source(int group_source) {
		this.group_source = group_source;
	}
	public boolean isHas_m3u8_video() {
		return has_m3u8_video;
	}
	public void setHas_m3u8_video(boolean has_m3u8_video) {
		this.has_m3u8_video = has_m3u8_video;
	}
	public int getHas_mp4_video() {
		return has_mp4_video;
	}
	public void setHas_mp4_video(int has_mp4_video) {
		this.has_mp4_video = has_mp4_video;
	}
	public boolean isHas_video() {
		return has_video;
	}
	public void setHas_video(boolean has_video) {
		this.has_video = has_video;
	}
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	public int getIgnore_web_transform() {
		return ignore_web_transform;
	}
	public void setIgnore_web_transform(int ignore_web_transform) {
		this.ignore_web_transform = ignore_web_transform;
	}
	public String getInteraction_data() {
		return interaction_data;
	}
	public void setInteraction_data(String interaction_data) {
		this.interaction_data = interaction_data;
	}
	public boolean isIs_stick() {
		return is_stick;
	}
	public void setIs_stick(boolean is_stick) {
		this.is_stick = is_stick;
	}
	public boolean isIs_subject() {
		return is_subject;
	}
	public void setIs_subject(boolean is_subject) {
		this.is_subject = is_subject;
	}
	public long getItem_id() {
		return item_id;
	}
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
	public int getItem_version() {
		return item_version;
	}
	public void setItem_version(int item_version) {
		this.item_version = item_version;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Label_extra getLabel_extra() {
		return label_extra;
	}
	public void setLabel_extra(Label_extra label_extra) {
		this.label_extra = label_extra;
	}
	public int getLabel_style() {
		return label_style;
	}
	public void setLabel_style(int label_style) {
		this.label_style = label_style;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Log_pb getLog_pb() {
		return log_pb;
	}
	public void setLog_pb(Log_pb log_pb) {
		this.log_pb = log_pb;
	}
	public Media_info getMedia_info() {
		return media_info;
	}
	public void setMedia_info(Media_info media_info) {
		this.media_info = media_info;
	}
	public String getMedia_name() {
		return media_name;
	}
	public void setMedia_name(String media_name) {
		this.media_name = media_name;
	}
	public int getNeed_client_impr_recycle() {
		return need_client_impr_recycle;
	}
	public void setNeed_client_impr_recycle(int need_client_impr_recycle) {
		this.need_client_impr_recycle = need_client_impr_recycle;
	}
	public long getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(long publish_time) {
		this.publish_time = publish_time;
	}
	public long getRead_count() {
		return read_count;
	}
	public void setRead_count(long read_count) {
		this.read_count = read_count;
	}
	public int getRepin_count() {
		return repin_count;
	}
	public void setRepin_count(int repin_count) {
		this.repin_count = repin_count;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public int getShare_count() {
		return share_count;
	}
	public void setShare_count(int share_count) {
		this.share_count = share_count;
	}
	public Share_info getShare_info() {
		return share_info;
	}
	public void setShare_info(Share_info share_info) {
		this.share_info = share_info;
	}
	public String getShare_url() {
		return share_url;
	}
	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}
	public boolean isShow_dislike() {
		return show_dislike;
	}
	public void setShow_dislike(boolean show_dislike) {
		this.show_dislike = show_dislike;
	}
	public boolean isShow_portrait() {
		return show_portrait;
	}
	public void setShow_portrait(boolean show_portrait) {
		this.show_portrait = show_portrait;
	}
	public boolean isShow_portrait_article() {
		return show_portrait_article;
	}
	public void setShow_portrait_article(boolean show_portrait_article) {
		this.show_portrait_article = show_portrait_article;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getSource_icon_style() {
		return source_icon_style;
	}
	public void setSource_icon_style(int source_icon_style) {
		this.source_icon_style = source_icon_style;
	}
	public String getSource_open_url() {
		return source_open_url;
	}
	public void setSource_open_url(String source_open_url) {
		this.source_open_url = source_open_url;
	}
	public String getStick_label() {
		return stick_label;
	}
	public void setStick_label(String stick_label) {
		this.stick_label = stick_label;
	}
	public int getStick_style() {
		return stick_style;
	}
	public void setStick_style(int stick_style) {
		this.stick_style = stick_style;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public long getTag_id() {
		return tag_id;
	}
	public void setTag_id(long tag_id) {
		this.tag_id = tag_id;
	}
	public int getTip() {
		return tip;
	}
	public void setTip(int tip) {
		this.tip = tip;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Ugc_recommend getUgc_recommend() {
		return ugc_recommend;
	}
	public void setUgc_recommend(Ugc_recommend ugc_recommend) {
		this.ugc_recommend = ugc_recommend;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public User_info getUser_info() {
		return user_info;
	}
	public void setUser_info(User_info user_info) {
		this.user_info = user_info;
	}
	public int getUser_repin() {
		return user_repin;
	}
	public void setUser_repin(int user_repin) {
		this.user_repin = user_repin;
	}
	public int getUser_verified() {
		return user_verified;
	}
	public void setUser_verified(int user_verified) {
		this.user_verified = user_verified;
	}
	public String getVerified_content() {
		return verified_content;
	}
	public void setVerified_content(String verified_content) {
		this.verified_content = verified_content;
	}
	public int getVideo_style() {
		return video_style;
	}
	public void setVideo_style(int video_style) {
		this.video_style = video_style;
	}
	
    
}
