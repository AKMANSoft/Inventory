package com.mi6.inventory;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.mi6.panels.RightPanel;
import com.mi6.panels.TablePanel;

public class MainWindow extends JFrame {
	private final static String TITLE = "Lite Inventory Manager";
	private static final long serialVersionUID = 1L;

	private TablePanel tablePanel;
	private RightPanel rightPanel;

	public MainWindow() {
		setTitle(TITLE);
		initComponents();
		initLayout();
		initListeners();
		setPreferredSize(new Dimension(500, 400));
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initComponents() {
		tablePanel = new TablePanel();
		rightPanel = new RightPanel();
	}

	private void initLayout() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = getGridBagConstrain();
		add(tablePanel, gc);

		gc.gridx++;
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1;
		add(rightPanel, gc);
	}
	
	private void initListeners() {
		rightPanel.addButtonListener(tablePanel);
	}

	private GridBagConstraints getGridBagConstrain() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.weightx = 10;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		return gc;
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			
			LafManager.install(new DarculaTheme());
			
			MainWindow main = new MainWindow();
			main.setVisible(true);

		});
	}
}
