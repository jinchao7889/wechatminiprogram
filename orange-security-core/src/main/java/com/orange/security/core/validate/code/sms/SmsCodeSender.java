/**
 * 
 */
package com.orange.security.core.validate.code.sms;

/**
 *
 */
public interface SmsCodeSender {
	
	/**
	 * @param mobile
	 * @param code
	 */
	void send(String mobile, String code) throws Exception;

}
