//package com.freeing.common.ftp;
//
//import com.freeing.common.ftp.exception.FtpException;
//import com.jcraft.jsch.*;
//import com.jcraft.jsch.ChannelSftp.LsEntry;
//import com.jcraft.jsch.ChannelSftp.LsEntrySelector;
//
//import java.io.Closeable;
//import java.io.InputStream;
//import java.nio.charset.Charset;
//import java.util.*;
//import java.util.function.Predicate;
//
//public class Sftp implements Closeable {
//
//	private Session session;
//	private ChannelSftp channel;
//
//	private final Charset charset;
//	private final long timeOut;
//
//	public Sftp(Session session, Charset charset, long timeOut) {
//		this.session = session;
//		this.charset = charset;
//		this.timeOut = timeOut;
//		init();
//	}
//
//	@Override
//	public void close() {
//		if (channel != null && channel.isConnected()) {
//			channel.disconnect();
//		}
//		if (session != null && session.isConnected()) {
//			session.disconnect();
//		}
//	}
//
//	public void init() {
//		ChannelSftp channel = openChannel(session, (int) timeOut);
//		channel.setFilenameEncoding(charset);
//		this.channel = channel;
//
//	}
//
//	public static ChannelSftp openChannel(Session session, int timeout) {
//		Channel channel;
//		try {
//			if (!session.isConnected()) {
//				session.connect();
//			}
//			channel = session.openChannel( "sftp");
//			channel.connect(Math.max(timeout, 0));
//		} catch (JSchException e) {
//			throw new RuntimeException();
//		}
//		return (ChannelSftp) channel;
//	}
//
//	public ChannelSftp getClient() {
//		if(!this.channel.isConnected()){
//			init();
//		}
//		return this.channel;
//	}
//
//	public List<LsEntry> lsEntries(String path) {
//		return lsEntries(path, null);
//	}
//
//
//	public List<LsEntry> lsEntries(String path, Predicate<LsEntry> filter) {
//		final List<LsEntry> entryList = new ArrayList<>();
//		try {
//			getClient().ls(path, entry -> {
//				final String fileName = entry.getFilename();
//				if (!Objects.equals(".", fileName) && !Objects.equals("..", fileName)) {
//					if (null == filter || filter.test(entry)) {
//						entryList.add(entry);
//					}
//				}
//				return LsEntrySelector.CONTINUE;
//			});
//		} catch (SftpException e) {
//			throw new MyRuntimeException(e);
//		}
//		return entryList;
//	}
//
//	public String pwd() {
//		try {
//			return getClient().pwd();
//		} catch (SftpException e) {
//			throw new MyRuntimeException(e);
//		}
//	}
//
//	public boolean mkdir(String dir) {
//		if (isDir(dir)) {
//			// 目录已经存在，创建直接返回
//			return true;
//		}
//		try {
//			getClient().mkdir(dir);
//			return true;
//		} catch (SftpException e) {
//			throw new MyFtpException(e);
//		}
//	}
//
//	public void mkDirs(String dir) {
//		final String[] dirs = StrUtil.trim(dir).split("[\\\\/]+");
//
//		final String now = pwd();
//		if (dirs.length > 0 && StrUtil.isEmpty(dirs[0])) {
//			//首位为空，表示以/开头
//			this.cd(StrUtil.SLASH);
//		}
//		for (String s : dirs) {
//			if (StrUtil.isNotEmpty(s)) {
//				boolean exist = true;
//				try {
//					if (!cd(s)) {
//						exist = false;
//					}
//				} catch (FtpException e) {
//					exist = false;
//				}
//				if (!exist) {
//					//目录不存在时创建
//					mkdir(s);
//					cd(s);
//				}
//			}
//		}
//		// 切换回工作目录
//		cd(now);
//	}
//
//	public boolean isDir(String dir) {
//		final SftpATTRS sftpATTRS;
//		try {
//			sftpATTRS = getClient().stat(dir);
//		} catch (SftpException e) {
//			final String msg = e.getMessage();
//			if (StringUtils.containsIgnoreCase(msg, "No such file")
//				|| StringUtils.containsIgnoreCase(msg, "does not exist")) {
//				return false;
//			}
//			throw new MyFtpException(e);
//		}
//		return sftpATTRS.isDir();
//	}
//
//
//	synchronized public boolean cd(String directory) throws FtpException {
//		if (StringUtils.isBlank(directory)) {
//			// 当前目录
//			return true;
//		}
//		try {
//			getClient().cd(directory.replace('\\', '/'));
//			return true;
//		} catch (SftpException e) {
//			throw new MyFtpException(e);
//		}
//	}
//
//
//	public boolean delFile(String filePath) {
//		try {
//			getClient().rm(filePath);
//		} catch (SftpException e) {
//			throw new MyFtpException(e);
//		}
//		return true;
//	}
//
//	public boolean upload(String destPath, String fileName, InputStream fileStream) {
//		destPath = StrUtil.addSuffixIfNot(destPath, StrUtil.SLASH) + StrUtil.removePrefix(fileName, StrUtil.SLASH);
//		put(fileStream, destPath, null, Mode.OVERWRITE);
//		return true;
//	}
//
//
//	public Sftp put(InputStream srcStream, String destPath, SftpProgressMonitor monitor, Mode mode) {
//		try {
//			getClient().put(srcStream, destPath, monitor, mode.ordinal());
//		} catch (SftpException e) {
//			throw new JschRuntimeException(e);
//		}
//		return this;
//	}
//
//	public enum Mode {
//		/**
//		 * 完全覆盖模式，这是JSch的默认文件传输模式，即如果目标文件已经存在，传输的文件将完全覆盖目标文件，产生新的文件。
//		 */
//		OVERWRITE,
//		/**
//		 * 恢复模式，如果文件已经传输一部分，这时由于网络或其他任何原因导致文件传输中断，如果下一次传输相同的文件，则会从上一次中断的地方续传。
//		 */
//		RESUME,
//		/**
//		 * 追加模式，如果目标文件已存在，传输的文件将在目标文件后追加。
//		 */
//		APPEND
//	}
//}
