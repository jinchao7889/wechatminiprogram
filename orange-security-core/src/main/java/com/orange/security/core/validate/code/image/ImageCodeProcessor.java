/**
 * 
 */
package com.orange.security.core.validate.code.image;

import javax.imageio.ImageIO;

import com.orange.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;

/**
 * 图片验证码处理器
 *
 *
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

	/**
	 * 发送图形验证码，将其写到响应中
	 */
	@Override
	protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
		BASE64Encoder encoder = new BASE64Encoder();
		ByteArrayOutputStream in = new ByteArrayOutputStream();


		ImageIO.write(imageCode.getImage(), "JPEG", in);
		byte[] data = in.toByteArray();
		String base64Image= "data:image/jpeg;base64," + encoder.encode(data);
		request.getResponse().setStatus(200);
		request.getResponse().setContentType("application/json;charset=UTF-8");
		request.getResponse().getWriter().write(base64Image);
	}

}
