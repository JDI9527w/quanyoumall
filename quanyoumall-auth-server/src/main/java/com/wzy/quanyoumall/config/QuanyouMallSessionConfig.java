package com.wzy.quanyoumall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * SpringSession配置类
 */
@Configuration
public class QuanyouMallSessionConfig {

	@Bean
	public CookieSerializer cookieSerializer() {

		DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();

		//放大作用域
		cookieSerializer.setDomainName("8.152.219.128");
		cookieSerializer.setCookieName("QYSESSION");

		return cookieSerializer;
	}


	@Bean
	public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}

}