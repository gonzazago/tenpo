package gonzalo.tenpo.infrastructure.config;


import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class HttpResponseWrapper extends HttpServletResponseWrapper {

    private ServletOutputStream outputStream;
    private ServletOutputWrapper wrapper;
    private PrintWriter writer;
    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response
     * @throws IllegalArgumentException if the response is null
     */
    public HttpResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if( outputStream != null){
            throw new IllegalStateException("output stream already been called");
        }
        if( writer == null){
         wrapper = new ServletOutputWrapper(getResponse().getOutputStream());
         writer = new PrintWriter(new OutputStreamWriter(wrapper,getResponse().getCharacterEncoding()),true);
        }

        return writer;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if(writer != null){
            throw new IllegalStateException("output stream already been called");
        }
        if(outputStream == null){
            outputStream = getResponse().getOutputStream();
            wrapper = new ServletOutputWrapper(outputStream);
        }
        return  wrapper;
    }

    public byte[] getContentAsByteArray(){
        if(outputStream != null){
           return wrapper.getByteArray();
        }else {
            return  new byte[0];
        }
    }

}
