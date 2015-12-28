package com.osmanungur.loadapp.util;

import com.jcraft.jsch.*;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RemoteSshUtil {

    private static final String CHANNEL = "exec";
    private static final String LOAD_COMMAND = "cat /proc/loadavg";
    private static final Properties SSH_CONFIG = new Properties();
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RemoteSshUtil.class);
    private final String user;
    private final String password;
    private final Integer port;
    private final String host;
    private Session session;

    public RemoteSshUtil(String user, String password, Integer port, String host) {
        this.user = user;
        this.password = password;
        this.port = port;
        this.host = host;

        SSH_CONFIG.put("StrictHostKeyChecking", "no");
    }

    public void connect() throws JSchException {
        LOGGER.info("Connecting to host");

        JSch jSch = new JSch();
        session = jSch.getSession(user, host, port);
        session.setConfig(SSH_CONFIG);
        session.setPassword(password);
        session.connect();
    }

    public String[] getRemoteLoad() throws JSchException, IOException {
        while (!session.isConnected()) {
            LOGGER.warn("No connection to host, retrying...");
            connect();
        }

        Channel channel = session.openChannel(CHANNEL);
        ((ChannelExec) channel).setCommand(LOAD_COMMAND);
        ((ChannelExec) channel).setErrStream(System.err);

        InputStream in = channel.getInputStream();

        channel.connect();

        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) {
                    break;
                }

                String line = new String(tmp, 0, i);

                return line.split("\\s+");

            }
            if (channel.isClosed()) {
                break;
            }
        }

        channel.disconnect();
        return null;
    }
}
