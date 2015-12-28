package com.osmanungur.loadapp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitListener implements ActionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExitListener.class);

    public void actionPerformed(ActionEvent e) {
        LOGGER.info("Exiting, bye!");
        System.exit(0);
    }
}
