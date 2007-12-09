package ru.spb.client.gui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import ru.spb.client.entities.Server;
import ru.spb.client.gui.trees.ServerTree;

public class IRCWindow implements Runnable {

    private static IRCWindow instance;

    @Override
    public void run() {
	JFrame frame = new JFrame("IRC (c) eav 3351");

	JSplitPane pane = new JSplitPane();
	pane.setLeftComponent(ServerTree.getInstance(new Server()));
	pane.setRightComponent(IRCTabbedPanel.getInstance());

	frame.getContentPane().add(pane);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
	frame.setVisible(true);
    }

    public static IRCWindow getInstance() {
	if (instance == null) {
	    instance = new IRCWindow();
	}
	return instance;
    }

}
