package com.langrsoft.util;

import java.util.HashMap;
import java.util.Map;

public class ActionProxyFactory {
    public ActionProxy createActionProxy(String name, String validationActionName, HashMap<String, Map<String, Object>> extraContext) {
        var proxy = new ActionProxy();
        proxy.setName(name);
        proxy.setValidationActionName(validationActionName);
        proxy.setExtraContext(extraContext);
        return proxy;
    }
}
