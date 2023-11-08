package gonzalo.tenpo.infrastructure.config;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class ServletOutputWrapper extends ServletOutputStream {


    private final OutputStream outputStream;
    private final ByteArrayOutputStream byteArrayOutputStream;
    public  ServletOutputWrapper(OutputStream outputStream){
        this.outputStream = outputStream;
        this.byteArrayOutputStream = new ByteArrayOutputStream();
    }
    @Override
    public void write(int b) throws IOException {
        this.outputStream.write(b);
        this.byteArrayOutputStream.write(b);
    }

    public byte[] getByteArray(){
        return this.byteArrayOutputStream.toByteArray();
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener listener) {

    }
}
