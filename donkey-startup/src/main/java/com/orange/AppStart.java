package com.orange;

import com.orange.security.core.validate.code.sms.SmsCodeSender;
import com.orange.security.rbac.JwtTokenEnhancer;
import com.orange.security.rbac.TxSmsCodeSender;
import com.spring4all.mongodb.EnableMongoPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.MultipartConfigElement;


@EnableMongoPlus
@SpringBootApplication
@Controller
public class AppStart {


    public static void main( String[] args )
    {
        SpringApplication.run(AppStart.class,args);
        System.out.println( "Hello World!" );
    }
    @GetMapping("/hello")
    public String hello(){
        return "index";
    }

    @Bean(name = "jwtTokenEnhancer")
    public TokenEnhancer jwtTokenEnhancer(){
        return new JwtTokenEnhancer();
    }

//    @Bean
//    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter(){
//        return new OpenEntityManagerInViewFilter();
//    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //允许上传的文件最大值
        factory.setMaxFileSize("50MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("50MB");
//        factory.setLocation("/app/tmp");
        return factory.createMultipartConfig();
    }

    @Bean
    public SmsCodeSender smsCodeSender() {

        return new TxSmsCodeSender();
    }

}
