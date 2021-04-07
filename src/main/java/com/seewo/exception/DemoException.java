package com.seewo.exception;

import com.seewo.honeycomb.web.exception.BizException;

/** 
 * @Author: Nuwa 
 * @Description: 由IDEA插件Nuwa生成的类
 */
public class DemoException extends BizException {

    public DemoException(String message) {
        super(message);
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}