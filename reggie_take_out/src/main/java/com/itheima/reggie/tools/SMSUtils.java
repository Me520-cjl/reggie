package com.itheima.reggie.tools;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 短信发送工具类
 */
public class SMSUtils {

	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 */
	public static void sendMessage(String signName, String templateCode,String phoneNumbers,String param){
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
				"LTAI5tFKRwmSJsxAm9Juyk7x", "IpsLuulHivYFu9L4giRT7X9syAuJ0s");
		IAcsClient client = new DefaultAcsClient(profile);

		SendSmsRequest request = new SendSmsRequest();
		//request.setSysRegionId("cn-hangzhou");
		request.setPhoneNumbers("15113594279");
		request.setSignName("阿里云短信测试");
		request.setTemplateCode("SMS_154950909");
		request.setTemplateParam("{\"code\":\""+param+"\"}");
		try {
			SendSmsResponse response = client.getAcsResponse(request);
			System.out.println("短信发送成功");
		}catch (ClientException e) {
			e.printStackTrace();
		}
	}
	//DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "<your-access-key-id>", "<your-access-key-secret>");
	/** use STS Token
	 DefaultProfile profile = DefaultProfile.getProfile(
	 "<your-region-id>",           // The region ID
	 "<your-access-key-id>",       // The AccessKey ID of the RAM account
	 "<your-access-key-secret>",   // The AccessKey Secret of the RAM account
	 "<your-sts-token>");          // STS Token
	 **/

//	IAcsClient client = new DefaultAcsClient(profile);
//
//	SendSmsRequest request = new SendSmsRequest();
//        request.setSignName("阿里云短信测试");
//        request.setTemplateCode("SMS_154950909");
//        request.setPhoneNumbers("15113594279");
//        request.setTemplateParam("{\"code\":\"1234\"}");
//
//        try {
//		SendSmsResponse response = client.getAcsResponse(request);
//		System.out.println(new Gson().toJson(response));
//	} catch (ServerException e) {
//		e.printStackTrace();
//	} catch (ClientException e) {
//		System.out.println("ErrCode:" + e.getErrCode());
//		System.out.println("ErrMsg:" + e.getErrMsg());
//		System.out.println("RequestId:" + e.getRequestId());
//	}

}
