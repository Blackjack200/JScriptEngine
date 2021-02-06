package site.misaka.quercus;

import com.caucho.vfs.StreamImpl;
import com.caucho.vfs.WriteStream;

import java.io.IOException;

public class QuercusWriteStream extends WriteStream {

    public byte[] _writeBuffer;

    public QuercusWriteStream() {
    }

    public QuercusWriteStream(StreamImpl source) {
        super(source);
    }

    @Override
    public void write(byte[] buf, int offset, int length) throws IOException {
        if (_writeBuffer != null) {
            super.write(buf, offset, length);
        }
    }
}
