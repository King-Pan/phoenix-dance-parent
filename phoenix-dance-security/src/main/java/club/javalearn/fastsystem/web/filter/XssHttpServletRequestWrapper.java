package club.javalearn.fastsystem.web.filter;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/25
 * Time: 上午10:16
 * Description: No Description
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    HttpServletRequest orgRequest;

    public static String CONTEXT = "content";

    public static String WITH_HTML = "WithHtml";

    private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(orgRequest.getInputStream()));
        String line = br.readLine();
        String result = "";
        if (line != null) {
            result += clean(line);
        }
        return new WrappedServletInputStream(new ByteArrayInputStream(result.getBytes()));
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     *
     * @param name 参数名-未过滤
     * @return 参数值-已经xss过滤的
     */
    @Override
    public String getParameter(String name) {
        if ((CONTEXT.equals(name) || CONTEXT.endsWith(name))) {
            return super.getParameter(name);
        }
        name = clean(name);
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = clean(value);
        }
        return value;
    }

    @Override
    public Map getParameterMap() {
        Map map = super.getParameterMap();
        // 返回值Map
        Map<String, String> returnMap = new HashMap<>(20);
        Iterator entries = map.entrySet().iterator();
        Map.Entry entry;
        String name;
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, clean(value).trim());

        }
        return returnMap;
    }

    @Override
    public String getHeader(String name) {
        name = clean(name);
        String value = super.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            value = clean(value);
        }
        return value;
    }

    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }
        return req;
    }

    public String clean(String content) {
        String result = Jsoup.clean(content, "", Whitelist.basic(), outputSettings);
        return result;
    }

    private class WrappedServletInputStream extends ServletInputStream {

        private InputStream stream;

        public void setStream(InputStream stream) {
            this.stream = stream;
        }

        public WrappedServletInputStream(InputStream stream) {
            this.stream = stream;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }
}
