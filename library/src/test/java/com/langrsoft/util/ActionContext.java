package com.langrsoft.util;

import java.util.Locale;

public class ActionContext {
    public static final String PARAMETERS = "params";
    private ValueStack valueStack;
    private Locale locale;
    private static ActionContext context;

    public ActionContext(ActionContext context) {
        this.context = context;
    }

    public ActionContext() {
        this.valueStack = new ValueStack();
    }

    public static ActionContext getContext() {
        if (ActionContext.context == null)
            ActionContext.context = new ActionContext();
        return ActionContext.context;
    }

    public static void setContext(ActionContext context) {
        ActionContext.context = context;
    }

    public ValueStack getValueStack() {
        return valueStack;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
