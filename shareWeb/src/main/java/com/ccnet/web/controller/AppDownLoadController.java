package com.ccnet.web.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("appDownLoadController")
@RequestMapping("/app/")
public class AppDownLoadController {

	/**
	 * app下载
	 * @return
	 */
	@RequestMapping("download")
	public String download(Model model){
		model.addAttribute("url", "");
		return "user/jsp/app_download/download";
	}
	
}
