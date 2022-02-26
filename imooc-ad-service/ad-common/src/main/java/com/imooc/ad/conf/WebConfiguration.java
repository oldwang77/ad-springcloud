package com.imooc.ad.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

// 所有引用ad-common模块的地方，都会用这个消息转换器进行转换
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        // 添加消息转换器
        // MappingJackson2HttpMessageConverter可以将Java对象转换为application/json
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
