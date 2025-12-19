package com.freeing.common.xfile.ftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

public class Sftp extends AbstractClient {
    private Session session;
    private ChannelSftp channel;

    public Sftp(Session session, ChannelSftp channel) {
        this.session = session;
        this.channel = channel;
    }

    @Override
    public boolean cd(String directory) {
        return false;
    }

    @Override
    public String pwd() {
        return null;
    }

    @Override
    public boolean mkdir(String dir) {
        return false;
    }

    @Override
    public List<String> ls(String path) {
        return null;
    }

    @Override
    public boolean delFile(String path) {
        return false;
    }

    @Override
    public boolean delDir(String dirPath) {
        return false;
    }

    @Override
    public boolean upload(String destPath, File file) throws IOException {
        return false;
    }

    @Override
    public void download(String path, File outFile) {

    }

    @Override
    public void download(String remoteFile, Consumer<InputStream> consumer) {

    }

    @Override
    public void close() throws IOException {

    }
}
