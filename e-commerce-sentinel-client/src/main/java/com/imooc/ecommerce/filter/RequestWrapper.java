package com.imooc.ecommerce.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Guank
 * @version 1.0
 * @description: HttpServletRequest 包装类 缓存 request 中的 body
 * @date 2022-08-12 16:19
 */
@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        byte[] bytes = null;
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            bytes = IOUtils.toByteArray(inputStream);
        }catch (IOException ex) {
            log.error("request wrapper error");
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
        body = bytes;
    }

    /**
     * 重写 getInputStream() 方法 让其返回我们缓存的 body
     * @return
     */
    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;
    }
}
