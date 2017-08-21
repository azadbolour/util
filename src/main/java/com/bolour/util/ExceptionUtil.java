/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;

import static com.bolour.util.SystemUtil.NL;

/**
 * Utility methods for exceptions. WORK IN PROGRESS.
 * TODO. Add tests for exception templates in different bundles and locales.
 *
 * Exception message templates may be customized for different languages in
 * resource bundles. The full class name of the exception identifies
 * that exception's message in a bundle.
 */
public final class ExceptionUtil {


    private static final String DEFAULT_BUNDLE = "exception.default";

    private ExceptionUtil() {
    }

    public static void printStackTraceAndCauses(Throwable thrown) {
        while (thrown != null) {
            thrown.printStackTrace();
            thrown = thrown.getCause();
        }
    }

    public static String dumpStackTraceAndCauses(Throwable thrown) {
        CharArrayWriter traceWriter = new CharArrayWriter();
        PrintWriter stringWriter = new PrintWriter(traceWriter);

        boolean first = true;
        while (thrown != null) {
            if (!first)
                stringWriter.println("Caused by ...");
            thrown.printStackTrace(stringWriter);
            Throwable cause = thrown.getCause();
            if (cause == thrown)
                break;
            thrown = cause;
            first = false;
        }
        return traceWriter.toString();
    }

    public static void logThrowableAndCauses(Throwable throwable, Logger logger) {
        String throwableCascade = dumpStackTraceAndCauses(throwable);
        logger.error(NL + throwableCascade);
    }

    public static <T> String getLocalizedMessage(String bundle, Class<T> cls, Object... args) {
        Locale locale = Locale.getDefault();
        return getLocalizedMessage(locale, bundle, cls, args);
    }

    public static <T> String getLocalizedMessage(Locale locale, String bundle, Class<T> cls, Object... args) {
        String template = getTemplate(locale, cls, bundle);
        String formatted = MessageFormat.format(template, args);
        return formatted;
    }

    private static <T> String getTemplate(Locale locale, Class<T> cls, String bundleName) {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
        String key = cls.getName();
        String template = bundle.getString(key);
        return template;
    }
}
