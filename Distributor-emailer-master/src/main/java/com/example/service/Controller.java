package com.example.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MailDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/mail")
public class Controller {

	@Autowired
	JavaMailSender jms;
	@Autowired
	Configuration conf;
	JsonMapper mapper = new JsonMapper();
	Map<String, String> res = new HashMap<>();
	
	public void res(HttpServletResponse response, String message) {
		response.setContentType("application/json");
		try {
			res.put("message", message);
			mapper.writeValue(response.getOutputStream(), res);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public void sendMail(@RequestBody MailDto dto, HttpServletResponse response) {
		MimeMessage message=  jms.createMimeMessage();
		try {
			MimeMessageHelper mmh = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			Map<String, String> map = new HashMap<>();
			map.put("receiverName", dto.getReceiverName());
			map.put("message", dto.getMessage());
			map.put("mail", dto.getTo());
			map.put("otp", dto.getOtp());
			map.put("deliveryCode", dto.getDeliveryCode());
			map.put("orderName", dto.getOrderName());
			Template template = conf.getTemplate(dto.getTemplateName()+".ftl");
			String htmlpage = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
			
			mmh.setTo(dto.getTo());
			mmh.setSubject(dto.getSubject());
			mmh.setFrom("jgathiru91@gmail.com");
			mmh.setText(htmlpage, true);
			jms.send(message);
		this.res(response, "success");
		}catch(Exception e) {
			this.res(response, "fail");
			System.out.println(e.getMessage());
		}
		
	}

	@Autowired
	RestExchanger exchanger;

	@GetMapping("/validate/{email}")
	public JsonNode getValidation(@PathVariable String email){
		return exchanger.getValidation(email).getBody();
	}
}
