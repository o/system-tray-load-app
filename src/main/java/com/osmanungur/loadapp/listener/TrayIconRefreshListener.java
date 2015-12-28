package com.osmanungur.loadapp.listener;

import com.jcraft.jsch.JSchException;
import com.osmanungur.loadapp.Application;
import com.osmanungur.loadapp.util.RemoteSshUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TrayIconRefreshListener implements ActionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrayIconRefreshListener.class);
    private final TrayIcon trayIcon;
    private final RemoteSshUtil remoteSshUtil;

    public TrayIconRefreshListener(TrayIcon trayIcon, RemoteSshUtil remoteSshUtil) {
        this.trayIcon = trayIcon;
        this.remoteSshUtil = remoteSshUtil;
    }

    public void actionPerformed(ActionEvent e) {
        LOGGER.info("Refreshing Tray icon");
        String[] averages;

        try {
            averages = remoteSshUtil.getRemoteLoad();
            LOGGER.info("Data successfully fetched");

            Double oneMinuteAverage = Double.valueOf(averages[0]);
            Double fiveMinuteAverage = Double.valueOf(averages[1]);
            Double fifteenMinuteAverage = Double.valueOf(averages[2]);

            if (oneMinuteAverage > 2) { // TODO: TrayIcon comes from main thread and it clashes with AWT Event Queue. Logging will be stopped after this point.
                trayIcon.setImage(Application.NO_IMAGE);
            } else {
                trayIcon.setImage(Application.CHECK_IMAGE);
            }

            trayIcon.setToolTip(String.format("1min: %s, 5min: %s, 15min: %s", oneMinuteAverage, fiveMinuteAverage, fifteenMinuteAverage));
            LOGGER.info("Changed icon successfully");
        } catch (IOException | JSchException exception) {
            trayIcon.setImage(Application.NO_IMAGE);
            trayIcon.setToolTip("An error occured: " + exception.getMessage());
            LOGGER.error("An error occured while fetching data: {}", exception.getMessage());
        }

    }
}
