package util;

import java.util.HashMap;

public class ActionProxyFactory {
    public ActionProxy createActionProxy(String name, String validationActionName, HashMap<String, Object> extraContext) {
        var proxy = new ActionProxy();
        proxy.setName(name);
        proxy.setValidationActionName(validationActionName);
        proxy.setExtraContext(extraContext);
        return proxy;
    }
}
