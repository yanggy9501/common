package com.freeing.common.xfile.ftp.wrapper;

import com.freeing.common.xfile.exception.FtpException;
import org.apache.commons.net.ftp.FTPSClient;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.Locale;
import java.util.Map;


/**
 * --add-opens java.base/sun.security.ssl=ALL-UNNAMED
 * 降低TLS版本到TLS 1.2及以下，解决：522 SSL connection failed; session reuse required.
 */
public class FTPSClientWithResumeSun extends FTPSClient {
    @Override
    protected void _prepareDataSocket_(Socket socket) throws IOException {
        if (socket instanceof javax.net.ssl.SSLSocket) {
            final SSLSession session = ((SSLSocket) _socket_).getSession();
            final SSLSessionContext context = session.getSessionContext();
            final String hostName = socket.getInetAddress().getHostName();
            final String hostAddress = socket.getInetAddress().getHostAddress();
            final String key1 = String.format("%s:%s", hostName, String.valueOf(socket.getPort())).toLowerCase(Locale.ENGLISH);
            final String key2 = String.format("%s:%s", hostName, String.valueOf(socket.getPort())).toLowerCase(Locale.ENGLISH);

            try {
                // 反射访问 SSLSessionContextImpl 的私有字段（JDK 21 兼容）
                Field cacheField = context.getClass().getDeclaredField("sessionHostPortCache");
                cacheField.setAccessible(true);
                @SuppressWarnings("unchecked")
                Map<String, SSLSession> cache = (Map<String, SSLSession>) cacheField.get(context);
                cache.put(key1, session);  // 缓存会话到数据端口键
                cache.put(key2, session);  // 缓存会话到数据端口键
            } catch (Exception e) {
                // 忽略反射失败（日志可选）
                throw new FtpException(e.getMessage(), e);
            }
        }
        super._prepareDataSocket_(socket);
    }
}