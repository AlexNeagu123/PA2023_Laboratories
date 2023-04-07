package com.utils;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;

public class FreeMarkerUtils {
    /**
     * @param cfg A {@link Configuration} object
     * @throws IOException If the file location of the template (.ftl file) is invalid
     */
    public static void configureFreeMarker(Configuration cfg) throws IOException {
        cfg.setDirectoryForTemplateLoading(new File("src/main/java/templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
    }
}
