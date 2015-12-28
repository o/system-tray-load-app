package com.osmanungur.loadapp;

import com.jcraft.jsch.JSchException;
import com.osmanungur.loadapp.listener.ExitListener;
import com.osmanungur.loadapp.listener.TrayIconRefreshListener;
import com.osmanungur.loadapp.util.RemoteSshUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    public static Image CHECK_IMAGE;
    public static Image NO_IMAGE;

    public static void main(String[] args) {
        try {
            CHECK_IMAGE = ImageIO.read(Application.class.getClassLoader().getResourceAsStream("images/check.png"));
            NO_IMAGE = ImageIO.read(Application.class.getClassLoader().getResourceAsStream("images/no.png"));
        } catch (IOException e) {
            LOGGER.error("Unable to find tray icon images?");
            System.exit(1);
        }


        LOGGER.info("Starting load system tray application");

        if (SystemTray.isSupported()) {
            final SystemTray tray = SystemTray.getSystemTray();

            final RemoteSshUtil remoteSshUtil = new RemoteSshUtil(
                    System.getenv("L_USER"), System.getenv("L_PASSWORD"), 22, System.getenv("L_HOST")
            );

            try {
                remoteSshUtil.connect();
                LOGGER.info("Connecting to remote host: {}");
            } catch (JSchException e) {
                LOGGER.error("An error occured while connecting to remote host: {}", e.getMessage());
                System.exit(1);
            }

            LOGGER.info("Creating Tray icon");
            final PopupMenu popup = new PopupMenu();
            final MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(new ExitListener());
            popup.add(defaultItem);

            final TrayIcon trayIcon = new TrayIcon(NO_IMAGE, "Load averages not ready", popup);
            final ActionListener tooltipListener = new TrayIconRefreshListener(
                    trayIcon,
                    remoteSshUtil
            );
            trayIcon.addActionListener(tooltipListener);

            try {
                tray.add(trayIcon);
                new Timer(10000, tooltipListener).start();
            } catch (AWTException e) {
                LOGGER.error("An AWT exception occured: {}", e.getMessage());
            }

        } else {
            LOGGER.error("System Tray is not supported");
        }

    }

}