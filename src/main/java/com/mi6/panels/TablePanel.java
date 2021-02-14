package com.mi6.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DropMode;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.mi6.model.ButtonListener;
import com.mi6.model.Product;
import com.mi6.model.ProductModel;
import com.mi6.renderers.ButtonColumn;
import com.mi6.utils.TableRowTransferHandler;
import com.mi6.utils.Util;

public class TablePanel extends JPanel implements ComponentListener, ButtonListener{

	private static final long serialVersionUID = 1L;
	
	private ProductModel model;
	private JTable table;
	private Font font;

	public TablePanel() {
		initComponents();
		initLayout();
		initListener();
	}

	private void initComponents() {
		model = new ProductModel();
		font = new Font(Util.FONT_NAME, Font.BOLD, Util.FONT_SIZE); 
		table = new JTable(model) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);

				if (!isRowSelected(row))
					c.setBackground(row % 2 == 0 ? getBackground() : Color.GRAY);
				
				if (!isRowSelected(row))
					c.setForeground(row % 2 == 0 ? getForeground() : Color.BLACK);
				
				Object value = getValueAt(row, 0);
				if (value instanceof Integer ) {
					int quantity = (int) value;
					if (quantity <= 0) {
						c.setBackground(Color.RED);
					}
				}

				return c;
			}
		};
		
		table.setDragEnabled(true);
		table.setDropMode(DropMode.INSERT_ROWS);
		table.setTransferHandler(new TableRowTransferHandler(table));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(font);
		
		ButtonColumn addColumn = new ButtonColumn(table, add, 2);
		addColumn.setMnemonic(KeyEvent.VK_PLUS);
		
		ButtonColumn deleteColumn = new ButtonColumn(table, delete, 3);
		deleteColumn.setMnemonic(KeyEvent.VK_MINUS);
		
	}

	private void initLayout() {
		setLayout(new GridLayout(1, 1, 5, 5));
		add(table);
	}
	
	private void initListener() {
		addComponentListener(this);
	}
	
	public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth,
	        double... percentages) {
	    double total = 0;
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        total += percentages[i];
	    }
	 
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        TableColumn column = table.getColumnModel().getColumn(i);
	        column.setPreferredWidth((int)
	                (tablePreferredWidth * (percentages[i] / total)));
	    }
	}
	
	public static void setJTableRowHeight(JTable table, int total, int rows) {
		if(rows == 0) {
			table.setRowHeight(28);
			return;
		}
		
	    int height = total / rows;
	    
	    if(height < 1) {
			table.setRowHeight(1);
			return;
		}
	    
	    table.setRowHeight(height);
	    
	    int fontSize = (int) (height * 0.70);
	    
	    table.setFont(new Font(Util.FONT_NAME, Font.BOLD, fontSize));
	}

	private Action add = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			int modelRow = Integer.valueOf(e.getActionCommand());
			model.addQuantity(modelRow);
		}
	};
	
	private Action delete = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			int modelRow = Integer.valueOf(e.getActionCommand());
			model.delQuantity(modelRow);
		}
	};

	@Override
	public void componentResized(ComponentEvent e) {
		setJTableColumnsWidth(table, getWidth(), 15, 40, 15, 15, 15);
		setJTableRowHeight(table, getHeight(), model.getRowCount());
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		setJTableColumnsWidth(table, getWidth(), 15, 40, 15, 15, 15);
		setJTableRowHeight(table, getHeight(), model.getRowCount());
	}

	@Override
	public void componentShown(ComponentEvent e) {
		setJTableColumnsWidth(table, getWidth(), 15, 40, 15, 15, 15);
		setJTableRowHeight(table, getHeight(), model.getRowCount());
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		
	}

	@Override
	public void actionperformed(String action) {
		if(action.equals("AddProduct")) {
			model.addProduct(new Product(0, "", 0, 0));
			
			setJTableColumnsWidth(table, getWidth(), 15, 40, 15, 15, 15);
			setJTableRowHeight(table, getHeight(), model.getRowCount());
		}
		
		if(action.equals("DelProduct")) {
			int row = table.getSelectedRow();
			
			if(row < 0) {
				String title = "Error Message";
				String message = "No Product Selected from the Table";
				
				JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
				
				return;
			}
			
			Product product = model.getProduct(row);
			if(product == null) return;
			
			model.delProduct(product);
			
			setJTableColumnsWidth(table, getWidth(), 15, 40, 15, 15, 15);
			setJTableRowHeight(table, getHeight(), model.getRowCount());
		}
		
	}
}
