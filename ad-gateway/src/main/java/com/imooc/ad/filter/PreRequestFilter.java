package com.imooc.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {
    // 定义pre类型的过滤器
    // 在微服务进来前的过滤器
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    // 过滤器的执行顺序
    @Override
    public int filterOrder() {
        return 0;
    }

    // 是否执行过滤器
    @Override
    public boolean shouldFilter() {
        return true;
    }

    // filter执行的具体操作
    @Override
    public Object run() throws ZuulException {
        // 请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("startTime", System.currentTimeMillis());

        return null;
    }
}
