package org.example.config.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.example.exception.CustomException;

import java.io.IOException;

/**
 * @author zhouyw
 * 复写原有默认feign异常解析类
 */
@Slf4j
public class OpenFeignErrorDecoder implements ErrorDecoder {

    /**
     * Feign异常解析
     * @param methodKey 方法名
     * @param response 响应体
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("feign client error,response is {}:",response);
        try {
            //获取数据
            String errorContent = IOUtils.toString(response.body().asInputStream());
            return new CustomException(errorContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CustomException("Feign client 调用异常");
    }

}
