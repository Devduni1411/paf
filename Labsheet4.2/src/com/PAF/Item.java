package com.PAF;

import java.sql.*;

public class Item {

	
	public Connection connect() 
	{  
		
		Connection con = null;            
	try         
	{          
		
	Class.forName("com.mysql.cj.jdbc.Driver");         
	con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?useTimezone=true&serverTimezone=UTC","root","");    
	
	
	
	
	 
	 //For testing          
	System.out.print("Successfully connected");            
	}
        
		catch(Exception e)         
			{         
	
	
					e.printStackTrace();  
					System.out.print("not connected");   
			}              

			return con;
			
	} 
	
	
	
	public String insertItem (String code , String name ,String price , String desc ) {
		
		String output = "";
		
		try
		{
				Connection con = connect();
				
				if(con == null) 
				{
					return "Error while connecting to the database";
				}
				
				//create a prepared statement
				
				String query =  " insert into items (`itemId`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`)"  + " values (?, ?, ?, ?, ?)"; 
				 
				 
				
				PreparedStatement preparedStmt = con.prepareStatement(query);	
				
				
				//binding values
				
				preparedStmt.setInt(1,0);
				preparedStmt.setString(2,code);
				preparedStmt.setString(3,name);
				preparedStmt.setDouble(4,Double.parseDouble(price));
				preparedStmt.setString(5,desc);
				
				//execute the statement
				preparedStmt.execute();
				con.close();
				
				output ="Inserted successfully";
				
				
			}
		catch(Exception e) {
			
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}
		
		return output;
		
		
}
	
	
	
	
	//develop read operation 
	
	public String readItems() {
		
		String output = "";
		try
		{
			Connection con = connect();
			
			if(con == null) 
			{
				return "Error while connecting to the database for reading.";
			}
			
			//preapare the html table to be displayed 
			output =  "<table class=\"table\" border=\"1\">"
					+ "<tr><th scope=\"col\">Item Code</th>"
					+ "<th scope=\"col\">Item Name</th>"
					+ "<th scope=\"col\">Item Price</th>"
					+ "<th scope=\"col\">Item Description</th>"
					+ "<th scope=\"col\">Update</th>"
					+ "<th scope=\"col\">Remove</th></tr>";
			
			
			String query = "Select * from items";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			
			//iterate through the rows in the result set
			while(rs.next()) {
				
				String itemId = Integer.toString(rs.getInt("itemId"));
				String itemCode = rs.getString("itemCode"); 
				String itemName = rs.getString("itemName"); 
				String itemPrice = Double.toString(rs.getDouble("itemPrice")); 
				String itemDesc = rs.getString("itemDesc"); 
				
				
				
				//Add into the html table
				
				output += "<tr><td>" + itemCode + "</td>"; 
				output += "<td>" + itemName + "</td>"; 
				output += "<td>" + itemPrice + "</td>"; 
				output += "<td>" + itemDesc + "</td>"; 
				
				//buttons
				
				output += "<td><input name=\"btnUpdate\" "
						 + " type=\"button\" value=\"Update\" class=\"btn btn-danger\"></td>"
						 + "<td><form method=\"post\" action=\"items.jsp\">"
						 + "<input name=\"btnRemove\" "
						 + " type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						 + "<input name=\"itemID\" type=\"hidden\" "
						 + " value=\"" + itemId + "\">" + "</form></td></tr>";
				
			}
			
			con.close();
			
			//complete the html table
			output += "</table>";
			
			
		}
		catch (Exception e)
		
		{
			output = "Error while reading the items.";   
			System.err.println(e.getMessage()); 
			
		}
		
		return output;
		
	}
	
	
	public String deleteItem(String itemId) {
		
		String output = ""; 

		  try {
			  
			  Connection con = connect(); 
			  
			  if (con == null)   
			  {    
				  return "Error while connecting to the database for deleting.";   
				  
			  } 
			  
			// create a prepared statement   
			  
			  String query = "delete from items where itemId=?"; 
			  
			  PreparedStatement preparedStmt = con.prepareStatement(query); 
			  
			// binding values  
			  preparedStmt.setInt(1, Integer.parseInt(itemId)); 
			  
			  
			// execute the statement   
			  preparedStmt.execute();   
			  con.close(); 
			  
			  output = "Deleted successfully"; 
		  	}
		  
		  catch (Exception e)  
		  {  
			  output = "Error while deleting the item.";   
			  System.err.println(e.getMessage());  
			  
		  }
		  
		  return output; 
		
	}
	
	
}
