package com.mi6.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mi6.model.Product;
import com.mi6.utils.Javaconnect;

public class ProductController {
	
	Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    public ProductController() {}
    
    public int getMaxId() {
    	conn = Javaconnect.ConnecrDb();
    	int id = 0;
		String sql = "Select Max(product_id) AS ID from product_table";
		 try {
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
		         
		         while (rs.next()) {
		        	 id = rs.getInt("ID");
		         }
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
				}
			}
		return id;
    }
	
    public boolean create(Product product) {
    	conn = Javaconnect.ConnecrDb();
		String sql = "insert into product_table (product_id, name, quantity, current, order_id) values (?,?,?,?,?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, product.getId());
			pst.setString(2, product.getName());
			pst.setInt(3, product.getQuanity());
			pst.setInt(4, product.getCurrent());
			pst.setInt(4, product.getOrderId());
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return true;
	}
	
    public boolean delete(Product product) {
    	conn = Javaconnect.ConnecrDb();
		String sql = "delete from product_table where product_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, product.getId());
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return true;
	}
	
    public List<Product> selectProduct(){
    	conn = Javaconnect.ConnecrDb();
		List<Product> products = new ArrayList<>();
		
		String sql = "select * from product_table";
		
		 try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
	         
	         while (rs.next()) {
	        	 int id = rs.getInt("product_id");
	        	 String name = rs.getString("name");
	        	 int quantity = rs.getInt("quantity");
	        	 int current = rs.getInt("current");
	        	 int orderId = rs.getInt("order_id");
	        	 Product product = new Product(id, name, quantity, orderId);
	        	 products.add(product);
	         }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
         
		return products;
	}
	
    public boolean update(Product product) {
    	conn = Javaconnect.ConnecrDb();
		String sql = "Update product_table set name =?, quantity =?, current =?, order_id =? where product_id = ?";
		
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, product.getName());
			pst.setInt(2, product.getQuanity());
			pst.setInt(3, product.getCurrent());
			pst.setInt(4, product.getOrderId());
			pst.setInt(5, product.getId());
			pst.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		return true;
	}
    
}
