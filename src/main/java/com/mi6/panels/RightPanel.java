package com.mi6.panels;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.mi6.model.ButtonListener;
import com.mi6.utils.Util;

public class RightPanel extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private JButton calculator;
	private JButton notepad;
	private JButton addProduct;
	private JButton delProduct;
	
	private ButtonListener l;
	
	public RightPanel() {
		initComponents();
		initLayout();
		initListeners();
		setSize(300, 600);
	}
	
	private void initComponents() {
		calculator = new JButton("Calculator");
		calculator.setFont(new Font(Util.FONT_NAME, Font.BOLD, Util.FONT_SIZE));
		
		notepad = new JButton("Notepad");
		notepad.setFont(new Font(Util.FONT_NAME, Font.BOLD, Util.FONT_SIZE));
		
		addProduct = new JButton("Add");
		addProduct.setFont(new Font(Util.FONT_NAME, Font.BOLD, Util.FONT_SIZE));
		
		delProduct = new JButton("Delete");
		delProduct.setFont(new Font(Util.FONT_NAME, Font.BOLD, Util.FONT_SIZE));
	}
	
	private void initLayout() {
		setLayout(new GridLayout(4, 1, 5, 5));
		add(calculator);
		add(notepad);
		add(addProduct);
		add(delProduct);
	}
	
	private void initListeners() {
		calculator.addActionListener(this);
		notepad.addActionListener(this);
		addProduct.addActionListener(this);
		delProduct.addActionListener(this);
	}
	
	public void addButtonListener(ButtonListener l) {
		this.l = l;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == calculator) {
			try {
				Runtime.getRuntime().exec("calc");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(e.getSource() == notepad) {
			try {
				Runtime.getRuntime().exec("notepad");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(e.getSource() == addProduct) {
			l.actionperformed("AddProduct");
		}
		
		if(e.getSource() == delProduct) {
			l.actionperformed("DelProduct");
		}
	}

}
