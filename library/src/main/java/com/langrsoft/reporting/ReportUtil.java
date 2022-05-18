package com.langrsoft.reporting;

public class ReportUtil {
    public enum StringOp {
        PAD, UNDER
    }

    public static String transform(String string, int count, int spacing, ReportUtil.StringOp op) {
        switch (op) {
            case UNDER: return pad("-".repeat(count), spacing);
            case PAD:   return pad(string, spacing);
            default:    return "";
        }
    }

    private static String pad(String string, int spacing) {
        return string + " ".repeat(spacing);
    }
}
