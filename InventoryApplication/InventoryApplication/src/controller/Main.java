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

					
			