package cn.com.newloading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QrCodeController {

	@RequestMapping("qrcode")
	public String test() {
		return "index/myPosterInfo";
	}
	
}
