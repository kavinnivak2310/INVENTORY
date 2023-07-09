package controller;

import java.io.*;
import java.sql.SQLException;

import dao.AgentDao;
import dao.LoginDAO;
import dao.ProductDAO;
import model.Login;
import model.Product;

public class Main {

	public static void main(String[] args) throws NumberFormatException, ClassNotFoundException, IOException, SQLException {
		
		BufferedReader br = new BufferedReader(new 
				InputStreamReader(System.in));
		
		Login login = new Login();
		Product product = new Product();
		LoginDAO logindao = new LoginDAO();
		ProductDAO productdao = new ProductDAO();
		AgentDao agentdao=new AgentDao();
		int option;
		do
		{
			System.out.println("1.Admin");
			System.out.println("2.Agent");
			System.out.println("3.Exit");
			System.out.println("---------------------------------------------");
			option = Integer.parseInt(br.readLine());
			
			switch(option)
			{
			case 1: System.out.println("---------------------------------------------");
			System.out.println("Enter username");
			String username = br.readLine();
			System.out.println("Enter password");
			String password = br.readLine();
			login.setUSERNAME(username);
			login.setPASSWORD(password);
			boolean result = logindao.validate(login);
			if(result == true)
			{
				System.out.println("Login Successful");
				System.out.println("---------------------------------------------");
				do
				{
					System.out.println("1.Add Product");
					System.out.println("2.Display Inventory Details");
					System.out.println("3.Logout");
					System.out.println("---------------------------------------------");
					option = Integer.parseInt(br.readLine());
					switch(option)
					{
					case 1: System.out.println("Enter product name");
					String productName = br.readLine();
					System.out.println("Enter product id");
					int productId = Integer.parseInt(br.readLine());
					System.out.println("Enter the min selling quantity");
					int minsell = Integer.parseInt(br.readLine());
					System.out.println("Enter the price of the product");
					int price = Integer.parseInt(br.readLine());
					System.out.println("Enter the quantity");
					int quantity = Integer.parseInt(br.readLine());
					product.setPRODUCTNAME(productName);
					product.setPRODUCTID(productId);
					product.setMINSELL(minsell);
					product.setQUANTITY(quantity);
					product.setPRICE(price);
					productdao.addproduct(product);
					System.out.println("---------------------------------------------");
					break;
					case 2: productdao.display();break;
					case 3: break;
					}
				}
				while(option != 3);
			}
			else
			{
				System.out.println("Username & Password is not incorrect");
			}
			break;
			case 2: 
			System.out.println("---------------------------------------------");
			System.out.println("Enter username");
			String agentusername = br.readLine();
			System.out.println("Enter password");
			String agentpassword = br.readLine();
			login.setUSERNAME(agentusername);
			login.setPASSWORD(agentpassword);
			result = logindao.validate(login);
			if(result == true)
			{
				System.out.println("Login Successful");
				System.out.println("---------------------------------------------");
				do
				{
					System.out.println("1.Buy/Sell");
					System.out.println("2.Show History");
					System.out.println("3.Logout");
					System.out.println("---------------------------------------------");
					option = Integer.parseInt(br.readLine()); 
					switch(option) {
					case 1:{
						System.out.println("------------------------------------------------");
						System.out.println("Enter your option (Buy or Sell)");
						String choice=br.readLine();
						
						if(choice.equalsIgnoreCase("Buy")){
							System.out.println("Enter protect name");
							String productName=br.readLine();
							System.out.println("Enter protect id");
							int productId=Integer.parseInt(br.readLine());
							System.out.println("Enter the min selling quentity");
							int minsell=Integer.parseInt(br.readLine());
							System.out.println("Enter the price of the product");
							int price=Integer.parseInt(br.readLine());
							System.out.println("Enter the quentity");
							int quentity=Integer.parseInt(br.readLine());
							
							product.setPRODUCTNAME(productName);
							product.setPRODUCTID(productId);
							product.setMINSELL(minsell);
							product.setQUANTITY(quentity);
							product.setPRICE(price);
							productdao.addproduct(product);
							break;
							}
						
						 if(choice.equalsIgnoreCase("Sell")) {
							
							System.out.println("------------------------------------------------------");
							System.out.println("Enter the Product Id");
							int productId=Integer.parseInt(br.readLine());
							System.out.println("Enter the quantity");
							int quentity=Integer.parseInt(br.readLine());
							
							if(productdao.checkQuantity(productId,quentity)) {
								int totalcast=productdao.totalCast(productId,quentity);
								System.out.println("------------------------------------------------------");
								System.out.println("The product total cast is "+totalcast);
								System.out.println("------------------------------------------------------");
								agentdao.AgentaddProduct(productId);
								System.out.println("Confirm Booking the product (yes/no)");
								
									
		
							}
							
						}
						break;
					}
					case 2:{
						System.out.println("-----------------------------------------------------------");
						System.out.println("All Previous Product List");
						productdao.display();
						System.out.println("-----------------------------------------------------------");
						System.out.println("New Updated valus in the List");
						productdao.display();
						break;
					}
					case 3:{
						break;
					}
						
					}
				
				} while(option!=3);break;
			}
		}
	

}while(option!=3);
}
}
package dao;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connectionManager.ConnectionManager;

public class AgentDao {
	public void AgentaddProduct(int id) throws SQLException, ClassNotFoundException {
			
		ConnectionManager conn1=new ConnectionManager();
		Connection con=conn1.establishConnection();
		
		PreparedStatement ps1=con.prepareStatement("SELECT * FROM PRODUCT WHERE PRODUCTID=?");
		ResultSet rs=ps1.executeQuery();
		int id1 = 0;
		String name=null;
		int minsell=0,price=0,quentity=0;
		if(rs.next()) {
			id1=rs.getInt("PRODUCTID");
			name=rs.getString("PRODUCTNAME");
			minsell=rs.getInt("MINSELL");
			price=rs.getInt("PRICE");
			quentity=rs.getInt("QUANTITY");
			
		}
		
		String query="insert into TRANSACTION(PRODUCTID,PRODUCTNAME,MINSELL,PRICE,QUANTITY)"+
				"values(?,?,?,?,?)";
	
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1,id1);
		ps.setString(2, name);
		ps.setInt(3, minsell);
		ps.setInt(4,price);
		ps.setInt(5, quentity);
		
		ps.executeUpdate();
		conn1.closeConnection();
		
	}

	public void displayAgent() throws ClassNotFoundException,SQLException{
	
		ConnectionManager conn=new ConnectionManager();
		Connection con=conn.establishConnection();
		
		String query="SELECT * FROM TRANSACTION ";
		PreparedStatement ps=con.prepareStatement(query);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			System.out.println("---------------------------------------------------------------------------------");
			System.out.println(rs.getInt("PRODUCTID")+"   "+rs.getInt("MINSELL")+"   "+rs.getString("PRODUCTNAME")+"   "+rs.getInt("PRICE")+"  "+rs.getInt("QUANTITY"));
			System.out.println("----------------------------------------------------------------------------------");
		}
		conn.closeConnection();
	}
}
package model;

public class Product {
	private int PRODUCTID;
	private String PRODUCTNAME;
	private int MINSELL;
	private int PRICE;
	private int QUANTITY;
	public int getPRODUCTID() {
		return PRODUCTID;
	}
	public void setPRODUCTID(int pRODUCTID) {
		PRODUCTID = pRODUCTID;
	}
	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}
	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}
	public int getMINSELL() {
		return MINSELL;
	}
	public void setMINSELL(int mINSELL) {
		MINSELL = mINSELL;
	}
	public int getPRICE() {
		return PRICE;
	}
	public void setPRICE(int pRICE) {
		PRICE = pRICE;
	}
	public int getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(int qUANTITY) {
		QUANTITY = qUANTITY;
	}

}
package connectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager { 
	Connection con=null;
	public Connection establishConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/INVENTORY","root","rima@123");
		return con;
		
	}
	public void closeConnection()throws SQLException
	{
		con.close();
	}
}

package model;

public class Login {
	private int ID;
	private String USERNAME;
	private String PASSWORD;
	private long MOBILE_NUMBER;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public long getMOBILE_NUMBER() {
		return MOBILE_NUMBER;
	}
	public void setMOBILE_NUMBER(long mOBILE_NUMBER) {
		MOBILE_NUMBER = mOBILE_NUMBER;
	}
	

}
package dao;

import model.Login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connectionManager.ConnectionManager;

public class LoginDAO {
	public boolean validate(Login login) throws ClassNotFoundException, SQLException
	{
		String username=login.getUSERNAME();
		String password=login.getPASSWORD();
		ConnectionManager conn=new ConnectionManager();
		Connection con=conn.establishConnection();
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery("SELECT * FROM LOGIN");
		while(rs.next())
		{
			if(username.equals(rs.getString("USERNAME"))
					&&password.equals(rs.getString("PASSWORD")))
			{
				conn.closeConnection();
				return true;
		    }
		}
		conn.closeConnection();
		return false;
		
	}

}



					
			
