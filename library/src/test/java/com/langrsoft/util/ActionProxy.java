package com.langrsoft.util;

import java.util.HashMap;
import java.util.Map;

public class ActionProxy {
    private String name;
    private String validationActionName;
    private HashMap<String, Map<String, Object>> extraContext;
    private ValidationAware validationAware;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValidationActionName() {
        return validationActionName;
    }

    public void setValidationActionName(String validationActionName) {
        this.validationActionName = validationActionName;
    }

    public void execute() {
        validationAware = new ValidationAware();
        var parms = (Map<String, Object>) extraContext.get(ActionContext.PARAMETERS);
        for (var entry : parms.entrySet())
            validationAware.addFieldError(entry.getKey(), propertyValue(entry.getKey()));
    }

    private String propertyValue(String key) {
        return Properties.getProperty(key);
    }

    public ValidationAware getAction() {
        return validationAware;
    }

    public void setExtraContext(HashMap<String, Map<String, Object>> extraContext) {
        this.extraContext = extraContext;
    }

    @Override
    public String toString() {
        return getName() + ":" + getValidationActionName();
    }
}
