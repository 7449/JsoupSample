package com.movie.manager;

/**
 * by y on 2016/8/7.
 */
public class HtmlUtils {

    public static String getHtml(String content) {
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/web.css\" type=\"text/css\">";
        return "<!DOCTYPE html>\n"
                + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n"
                + "\t<meta charset=\"utf-8\" />\n</head>\n"
                + css
                + "\n<body>"
                + content
                + "</body>\n</html>";
    }

    public static String getCoding() {
        return "utf-8";
    }

    public static String getMimeType() {
        return "text/html";
    }

}
