package com.mi6.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.mi6.controller.ProductController;

public class ProductModel extends AbstractTableModel implements Reorderable{

	private static final long serialVersionUID = 1L;
	
	private List<Product> products;
	private ProductController controller;
	
	public ProductModel() {
		products = new ArrayList<>();
		controller = new ProductController();
		products = controller.selectProduct();
		Collections.sort(products);
	}
	
	public int getRowCount() {
		return products.size();
	}

	public int getColumnCount() {
		return 5;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(products.isEmpty()) return null;
		Product product = products.get(rowIndex);
		switch (columnIndex) {
		case 0: return product.getQuanity();
		case 1: return product.getName();
		case 2: return "+";
		case 3: return "-";
		case 4: return product.getCurrent();
		default: return product;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (products.isEmpty())
			return;
		Product product = products.get(rowIndex);
		switch (columnIndex) {
		case 0:
			if (aValue instanceof Integer)
				product.setQuanity((int) aValue);
			break;
		case 1:
			if (aValue instanceof String)
				product.setName((String) aValue);
			break;
		case 4:
			if (aValue instanceof Integer)
				product.setCurrent((int) aValue);
			break;
		default:
			break;
		}
		controller.update(product);
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public void addProduct(Product product) {
		int id = controller.getMaxId();
		product.setId(id+1);
		product.setOrderId(products.size());
		controller.create(product);
		products.add(product);
		int size = products.size();
		fireTableRowsInserted(size, size);
	}
	
	public void delProduct(Product product) {
		int index = products.indexOf(product);
		products.remove(index);
		controller.delete(product);
		fireTableRowsDeleted(index, index);
	}
	
	public void addQuantity(int row) {
		if(products.isEmpty() || row >= getRowCount()) {
			System.err.println("Product is empty or row is greater then the total row count");
			return;
		}
		Product product = products.get(row);
		product.setQuanity(product.getQuanity()+1);
		
		controller.update(product);
		fireTableCellUpdated(row, 0);
	}
	
	public void delQuantity(int row) {
		if(products.isEmpty() || row >= getRowCount()) {
			System.err.println("Product is empty or row is greater then the total row count");
			return;
		}
		Product product = products.get(row);
		product.setCurrent(product.getCurrent()-1);
		product.setQuanity(product.getQuanity()-1);
		
		controller.update(product);
		fireTableCellUpdated(row, 0);
		fireTableCellUpdated(row, 4);
	}
	
	public Product getProduct(int index) {
		if(index >= products.size()) return null;
		return products.get(index);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4: return true;
		default: return false;
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0: return Integer.class;
		case 1: return String.class;
		case 4: return Integer.class;
		default: break;
		}
		return super.getColumnClass(columnIndex);
	}

	@Override
	public void reorder(int fromIndex, int toIndex) {
		
		if(toIndex >= products.size()) return;
		
		Product p1 = products.get(fromIndex);
		Product p2 = products.get(toIndex);
		
		p1.setOrderId(p2.getOrderId());
		
		controller.update(p1);
		
		int orderId = p2.getOrderId();
		for(int i = toIndex; i < products.size(); i++) {
			Product p = products.get(i);
			if(p.equals(p1)) continue;
			p.setOrderId(++orderId);
			controller.update(p);
		}
		
		Collections.sort(products);
		
		fireTableDataChanged();
	}
	
}
