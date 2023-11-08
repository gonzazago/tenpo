package gonzalo.tenpo.infrastructure.config;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;

public class HttpRequestWrapper extends HttpServletRequestWrapper {

    private byte[] bodyAsByte;

    public HttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.bodyAsByte = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputWrapper(this.bodyAsByte);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(this.bodyAsByte);
        return new BufferedReader(new InputStreamReader(byteArrayOutputStream));
    }

    public byte[] getContentAsByteArray() {
        return this.bodyAsByte;
    }
}
