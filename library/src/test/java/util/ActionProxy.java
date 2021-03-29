package util;

import java.util.HashMap;
import java.util.Map;

public class ActionProxy {
    private String name;
    private String validationActionName;
    private HashMap<String, Object> extraContext;
    private ValidationAware validationAware;

    public void setName(String name) {
        this.name = name;
    }

    public void setValidationActionName(String validationActionName) {
        this.validationActionName = validationActionName;
    }

    public void execute() {
        validationAware = new ValidationAware();
        Map<String, Object> parms = (Map<String, Object>) extraContext.get(ActionContext.PARAMETERS);
        for (Map.Entry<String, Object> entry : parms.entrySet())
            validationAware.addFieldError(entry.getKey(), propertyValue(entry.getKey()));
    }

    private String propertyValue(String key) {
        return Properties.getProperty(key);
    }

    public ValidationAware getAction() {
        return validationAware;
    }

    public void setExtraContext(HashMap<String, Object> extraContext) {
        this.extraContext = extraContext;
    }
}
