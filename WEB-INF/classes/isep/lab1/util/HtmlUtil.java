package isep.lab1.util;

/** Tiny helpers shared by the servlets that print HTML. */
public final class HtmlUtil {

    private HtmlUtil() {
    }

    public static String escape(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '&':
                    sb.append("&amp;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '\'':
                    sb.append("&#39;");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String begin(String contextPath, String title) {
        return "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n<meta charset=\"UTF-8\">\n"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                + "<title>" + escape(title) + "</title>\n"
                + "<link rel=\"stylesheet\" href=\"" + contextPath + "/css/style.css\">\n"
                + "</head>\n<body>\n<main>\n<h1>" + escape(title) + "</h1>\n";
    }

    public static String end(String contextPath) {
        return "<p class=\"nav\"><a href=\"" + contextPath + "/index.html\">&larr; Back to the lab index</a></p>\n"
                + "</main>\n</body>\n</html>\n";
    }
}
