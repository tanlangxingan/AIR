package flight.tuipiao;

import flight.assist.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class TuiPiao extends JFrame implements ActionListener
{
	private JTextField jtfDingDan = new JTextField(12),jtfID = new JTextField(15),
	                   jtfName = new JTextField(7),jtfFlightInfo = new JTextField(20),
//	                   jtfChildNum = new JTextField(5),jtfAdultNum = new JTextField(5),
	                   jtfOriginCost = new JTextField(9),jtfTuiPiaoCost = new JTextField(9),
	                   jtfTime1 = new JTextField(20),
	                   jtfTime2 = new JTextField(20);
//	                   jtfChildTuiPiaoShu = new JTextField("0",5),
//	                   jtfAdultTuiPiaoShu = new JTextField("0",5);
	                   
	private JButton jbQuery = new JButton("查询"),jbOK = new JButton("退票"),
	                jbCancel = new JButton("退出"),jbRewrite = new JButton("重填");
	                
	private Object[] items = {"15位身份证","18位身份证"	};
	private JComboBox jcb = new JComboBox(items);
	
	private JLabel jlTime1 = new JLabel("出发时间"),jlTime2 = new JLabel("            ");
	
	private String dingdan,name,id,flight1,flight2,ticketType,leaveTime1,leaveTime2,
	               childNum,adultNum,cost,year,month,day,hour;
	               
//	private File file ;
	
	private long locationOfRecord;
	
	private SeatInfo seatInfo = new SeatInfo();
	private SqlBean sqlBean = new SqlBean();
	
	public TuiPiao()
	{
//		File f = new File(".","data");
//    	f.mkdir();
//    	file = new File(f,"ClientInfo.txt");
    	    	
    	jtfName.setEditable(false);
		jtfFlightInfo.setEditable(false);
		jtfOriginCost.setEditable(false);
		jtfTuiPiaoCost.setEditable(false);
		jtfTime1.setEditable(false);
		jtfTime2.setEditable(false);
    	        
        //******************************************************************
        JLabel jlDingdan = new JLabel("订 单 号 ");
        JLabel jlID = new JLabel("   身份证号");

        
        JPanel jpInput = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpInput.setBorder(new TitledBorder("输入信息"));
        
        jpInput.add(jlDingdan);
        jpInput.add(jtfDingDan);
        jpInput.add(jlID);
        jpInput.add(jtfID);      
                
        //****************************************************************
        
        JLabel jlName = new JLabel("客户姓名");
        JLabel jlFlight = new JLabel("  航班信息");
        JLabel jlOriCost = new JLabel(" 票  价");  
        
        JPanel jpTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpTop.add(jlName);
        jpTop.add(jtfName);
        jpTop.add(jlFlight);
        jpTop.add(jtfFlightInfo);
            
        
        JPanel jpCost = new JPanel();
        jpCost.setLayout(new GridLayout(2,1));
	        JPanel jp3 = new JPanel();
	        jp3.add(jlOriCost);
	        jp3.add(jtfOriginCost);
        jpCost.add(jp3);
      
        
        JPanel jpTime = new JPanel();
        jpTime.setLayout(new GridLayout(2,1));
	        JPanel jp5 = new JPanel();
	        jp5.add(jlTime1);
	        jp5.add(jtfTime1);
	        JPanel jp6 = new JPanel();
	        jp6.add(jlTime2);
	        jp6.add(jtfTime2);
        jpTime.add(jp5);
        jpTime.add(jp6);
       
        
        JPanel jpCenter = new JPanel();
        jpCenter.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpCenter.add(jpCost);
        jpCenter.add(jpTime);
//        jpCenter.add(jpTime,BorderLayout.EAST);   
        
        JPanel jpInfo = new JPanel();
        jpInfo.setBorder(new TitledBorder("基本信息"));
        jpInfo.setLayout(new BorderLayout()); 
        jpInfo.add(jpTop,BorderLayout.NORTH);
        jpInfo.add(jpCenter,BorderLayout.CENTER);       
        
        jlTime2.setVisible(false);
		jtfTime2.setVisible(false);
			
		
		JPanel jpButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpButtons.setBorder(new TitledBorder("操作"));
		jpButtons.add(jbQuery);
		jpButtons.add(jbRewrite);
		jpButtons.add(jbOK);
		jpButtons.add(jbCancel);
		
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		jp.add(jpInput,BorderLayout.NORTH);
		jp.add(jpInfo,BorderLayout.CENTER);
		
		JPanel jpTotal = new JPanel();
		jpTotal.setBorder(new MatteBorder(new ImageIcon("src/f.gif")));
		jpTotal.setLayout(new BorderLayout());
		jpTotal.add(jp,BorderLayout.CENTER);
		jpTotal.add(jpButtons,BorderLayout.SOUTH);
       
        //******************************************************************
               
		this.getContentPane().add(jpTotal);		
		
		//add actionListener for the buttons
		jbQuery.addActionListener(this);
		jbRewrite.addActionListener(this);
		jbOK.addActionListener(this);
		jbCancel.addActionListener(this);
		
		//add actionListener for the window
		this.addWindowListener(new WindowAdapter()
	                          {
	                          	public void windowClosing(WindowEvent e)
	                          	{
	                          		TuiPiao.this.setVisible(false);
	                          		TuiPiao.this.dispose();
	                          	}
	                          }
	                      );		
	}
	//********************************************************************
	
	//the buttons' action
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == jbQuery)
			try {
				query();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		else if (e.getSource() == jbRewrite)
		   rewrite();
		else if (e.getSource() == jbOK)
		   tuiPiao();
		else if (e.getSource() == jbCancel)
		{
			//set the frame unvisible
			this.setVisible(false);
			//close the window
			this.dispose();
		}   
	}
	//********************************************************************
	
	//operations for query
	private void query() throws SQLException
	{
		dingdan = jtfDingDan.getText().trim();
	    //if dingdan is wrong,display the error message
	    if (dingdan.length() == 0)
	    {
	    	JOptionPane.showMessageDialog(null,"订单号不能为空",
	    	                              "错误信息",JOptionPane.ERROR_MESSAGE);
	    	return;
	    }
	    
	    id = jtfID.getText().trim();
	    //if dingdan is wrong,display the error message
	    if (id.length() == 0)
	    {
	    	JOptionPane.showMessageDialog(null,"身份证号不能为空",
	    	                              "错误信息",JOptionPane.ERROR_MESSAGE);
	    	return;
	    }

		else {
			String query = "select * from booking_record where dingdanhao='" + dingdan + "'and ID='" + id + "'";
			ResultSet rs = sqlBean.executeQuery(query);
			if (rs.next()) {
				name = rs.getString("name");
				cost = String.valueOf(rs.getFloat("price"));
				flight1=rs.getString("airCompany");
				leaveTime1=rs.getString("time");
				jtfName.setText(name);
				jtfOriginCost.setText("  " + cost + "元");
				jtfFlightInfo.setText(flight1);
				jtfTime1.setText(leaveTime1);
				sqlBean.CloseDataBase();
				int sign = returnTime();
				// if the time for return ticket is over,display the error
				// message
				if (sign == 0)
					JOptionPane.showMessageDialog(null, "抱歉，退票时间已过，您现在不能退票!", "错误信息", JOptionPane.ERROR_MESSAGE);
				// display the returning money across the current time and the
				// flight time
				if (sign == 1)
					jtfTuiPiaoCost.setText("退还65%价钱");
				if (sign == 1)
					jtfTuiPiaoCost.setText("退还70%价钱");

				if (ticketType.equals("单程")) {
					jlTime1.setText("出发时间");
					jtfTime1.setText(leaveTime1);

					jlTime2.setVisible(false);
					jtfTime2.setVisible(false);

					jtfFlightInfo.setText("(单程机票)" + "航班号:" + flight1);
				}

				else if (ticketType.equals("往返")) {
					jlTime1.setText("出发时间");
					jtfTime1.setText(leaveTime1);

					jlTime2.setText("返回时间");
					jlTime2.setVisible(true);

					jtfTime2.setText(leaveTime2);
					jtfTime2.setVisible(true);

					jtfFlightInfo.setText("(往返机票) " + " 去: " + flight1 + "; 返: " + flight2);
				}

				else if (ticketType.equals("联程")) {
					jlTime1.setText("第一出发时间");
					jtfTime1.setText(leaveTime1);

					jlTime2.setText("第二出发时间");
					jlTime2.setVisible(true);
					jtfTime2.setText(leaveTime2);
					jtfTime2.setVisible(true);

					jtfFlightInfo.setText("(联程机票) " + "航班1: " + flight1 + "; 航班2: " + flight2);
				}
			} else {
				JOptionPane.showMessageDialog(null, "没有此次订单记录，请核对订单号与身份证号!", "错误信息", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	//********************************************************************
	
	//clean the textFilds
	private void rewrite()
	{
	    jtfDingDan.setText("");
	    jtfID.setText("");
		jtfName.setText("");
		jtfFlightInfo.setText("");
		
    	jtfOriginCost.setText("");
    	jtfTuiPiaoCost.setText("");
    	
    	jtfTime1.setText("");
    	jtfTime2.setText("");
    	jlTime1.setText("出发时间");   
    
    	jlTime2.setVisible(false);
    	jtfTime2.setVisible(false);
	}
	//********************************************************************
	//determine wether can return ticket and performs
		private void tuiPiao()
		{
		    String  dingdan = jtfDingDan.getText().trim();
		    if (dingdan.length() == 0)
		    {
		    	JOptionPane.showMessageDialog(null,"订单号不能为空",
		    	                              "错误信息",JOptionPane.ERROR_MESSAGE);
		    	return;
		    }
		    
		    String id = jtfID.getText().trim();
		    if (id.length() == 0)
		    {
		    	JOptionPane.showMessageDialog(null,"身份证号不能为空",
		    	                              "错误信息",JOptionPane.ERROR_MESSAGE);
		    	return;
		    }
		           
		       
		    else
		    {		    	
		    	operationForTuiPiao(dingdan);
		    }
			
		}
		//********************************************************************
	//operation after returning
	private void operationForTuiPiao(String dingdan) 
	{
		
		String sqlString = "select price from booking_record where dingdanhao='"+dingdan+"'";
		ResultSet rs = sqlBean.executeQuery(sqlString);
		try {
			if(rs.next()){
				String sql="delete from booking_record where dingdanhao='"+dingdan+"'";
				sqlBean.executeDelete(sql);
			}else{
				JOptionPane.showMessageDialog(null,"订单号不存在",
                        "错误信息",JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlBean.CloseDataBase();
		JOptionPane.showMessageDialog(null, "退票成功");
		
	}
	//********************************************************************
	
	//caculate the returning money
	private float caculateTuiPiaoCost()
	{
		float tuiPiaoCost = 0;
		int sign=0;
		
		try
		{
			//get message about the flight
			String sqlString = "select price from booking_record where dingdanhao='"+flight1+"'";
			ResultSet rs = sqlBean.executeQuery(sqlString);
			
//			float childFare1 = 0;
//			float adultFare1 = 0;
			double fare1=0;
			while(rs.next()){
				fare1=rs.getFloat("price");
			}
			double fare2=0;
			
			if (flight2.length() != 0)
			{
				String sqlString2 = "select price from booking_record where dingdanhao='"+flight2+"'";
			    ResultSet rs2 = sqlBean.executeQuery(sqlString2);
			    
			    while(rs2.next())
				{
			    	fare2=rs.getFloat("price");	
				}
			}
			
			sign=returnTime();
			
			if (flight2.length() == 0){
				
			  if(sign==0)
			    JOptionPane.showMessageDialog(null,"抱歉，您现在不能退票!","错误信息",JOptionPane.ERROR_MESSAGE);
			  if(sign==1)
			    tuiPiaoCost = (float) ((fare1) * 0.65);
			  if(sign==2)
				tuiPiaoCost = (float) ((fare1) * (float)0.7);
			  
			}
			  
			else
			{
			  if(sign==0)
			    JOptionPane.showMessageDialog(null,"抱歉，退票时间已过，您现在不能退票!","错误信息",JOptionPane.ERROR_MESSAGE);
			  if(sign==1)
			    tuiPiaoCost = (float) ((fare1 + fare2) * (float)0.65);
			  if(sign==2)
				tuiPiaoCost =(float) (( fare1 + fare2) * (float)0.7);
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return tuiPiaoCost;		
	}
	//********************************************************************
	
	public String getCurrentTime(){
		//Get the instance for the class Calendar which used to get the present time
    	Calendar cal = Calendar.getInstance();
    	
    	   //Because there are two Date classes(java.util.Date--java.sql.Date)
    	   //So we should designate the full name for the java.util.Date class 
    	cal.setTime(new java.util.Date());
    	
    	   //Get the present year,month,day
    	String year = String.valueOf(cal.get(Calendar.YEAR));
    	String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
    	String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    	String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
    	//String minute = String.valueOf(cal.get(Calendar.MINUTE));
    	//String second = String.valueOf(cal.get(Calendar.SECOND));
    	return year.concat(month.concat(day.concat(hour)));
	}
	
	//determine wether can return ticket right now
	public int returnTime(){
		
	    int y=Integer.parseInt(year);
		int m=Integer.parseInt(month);
		int d=Integer.parseInt(day);
		int h=Integer.parseInt(hour);
				
		String st=getCurrentTime();
		int cy=Integer.parseInt(st.substring(0,4));
		int cm=Integer.parseInt(st.substring(4,6));
		int cd=Integer.parseInt(st.substring(6,8));
		int ch=Integer.parseInt(st.substring(8,10));
		
		//compare flight time and current time
		if(y-cy==1)
		  cm=m-1;
		  
		else if(y!=cy)   
		  return 0;
		  
		if(m-cm==1){
		      		switch(m){
		      			case 1:  
		      			case 3:  
		      			case 5: 
		      			case 7:  
		      			case 8:  
		      			case 10:  
		      			case 12:   cd=cd-31; break;
		      			case 2:    {
		      				         if(isLeapYear(cy))   cd=cd-29;
		      			             else  cd=cd-28;
		      			             break;
		      			           }
		      			case 4:  
		      			case 6:  
		      			case 9:  
		      			case 11:   cd=cd-30;   break;
		      			  
		      		}
		  }
		  else if(m!=cm)  return 0;
		  //if the flight is leave the day after tomorrow day,the return can't perform
		  if(d==cd){
		  	if(h-ch<2)  return 0;
		    else   return 1;
		  }
		    
		  else 
		      {
		         if(d-cd<5&&d-cd>0)   return 1;
		         else if(d-cd>5)   return 2;
		         else return 0;
		      }
		  	
		  	  
	}
	
	//wether the year is leap year
	public boolean isLeapYear(int year){
		if(year%4==0&&year%100!=0&&year%400==0)
		  return true;
		else return false;
	}
	//********************************************************************
	
//	//get the clients' information
//	private boolean getClientInfo(String dingdan,String id)
//	{
//		RandomAccessFile raf = null;
//		
//		try
//		{				
//		    raf = new RandomAccessFile(file,"rw");
//		    
//		    boolean isDingDanExist = false;
//		    boolean isIDRight = false;
//		    
//		    long tempLocation = 0;
//		    raf.seek(0);
//		   
//		   //find message in file
//		   try{
//		   	
//		   	 //if can't continue reading,throw exception
//		   	 while (raf.getFilePointer() < raf.length())
//		    {
//			    //record current position
//			    tempLocation = raf.getFilePointer();
//			    
//			    dingdanNum = raf.readUTF();
//			    name = raf.readUTF();
//			    idNum = raf.readUTF();
//			    flight1 = raf.readUTF();
//			    flight2 = raf.readUTF();
//			    ticketType = raf.readUTF();
//			    leaveTime1 = raf.readUTF();
//			    leaveTime2 = raf.readUTF();
//			    childNum = raf.readUTF();
//			    adultNum = raf.readUTF();
//			    cost = raf.readUTF();
//			    
//			    if (dingdanNum.equals(dingdan))
//			    {
//			    	isDingDanExist = true;
//			    	
//			    	if (idNum.equals(id))
//			    	{
//			    		isIDRight = true;
//			    		this.locationOfRecord = tempLocation;
//			    		break;
//			    	}
//			    	
//			    	else 
//			    	{
//			    		isIDRight = false;
//			    		break;
//			    	}
//			    }		    
//		    }
//		   }
//		   catch(Exception e){
//		   	
//		   }
//		    
//		    //display the fist leave time and the second time
//		    if (isDingDanExist == true && isIDRight == true)
//		    {
//		    	String year2="";
//		    	String month2="";
//		    	String day2="";
//		    	String hour2="";
//		    	
//		    	if (leaveTime1.length() != 0)
//		    	{
//		    		year = leaveTime1.substring(0,4);
//		    	    month = leaveTime1.substring(4,6);
//		    	    day = leaveTime1.substring(6,8);
//		    	    hour=leaveTime1.substring(8,10);
//		    	    
//		    	    leaveTime1 = year.concat("-").concat(month).concat("-").concat(day);
//		    	}
//		    	
//		    	if (leaveTime2.length() != 0)
//		    	{
//		    		year2 = leaveTime2.substring(0,4);
//		    	    month2 = leaveTime2.substring(4,6);
//		    	    day2 = leaveTime2.substring(6,8);
//		    	    hour2 =leaveTime1.substring(8,10);
//		    	    
//		    	    leaveTime2 = year2.concat("-").concat(month2).concat("-").concat(day2);
//		    	}
//		    	
//		    	if (Integer.parseInt(childNum) == 0 && Integer.parseInt(adultNum) == 0)
//		    	{
//		    		JOptionPane.showMessageDialog(null,"该订单号已经无效!",
//		    		                              "错误信息",JOptionPane.ERROR_MESSAGE);
//		    		return false;
//		    	}
//		    	
//		    	return true;
//		    	
//		    }
//		        
//		    
//		    if (isDingDanExist == true && isIDRight == false)
//		    {
//		    	JOptionPane.showMessageDialog(null,"身份证号不正确","错误信息",JOptionPane.ERROR_MESSAGE);
//		    	return false;
//		    }		      
//		       
//		    if (isDingDanExist == false)
//		    {
//		    	JOptionPane.showMessageDialog(null,"订单号不存在","错误信息",JOptionPane.ERROR_MESSAGE);
//		    	return false;
//		    	
//		    }		   	
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			try
//			{
//				//close the file
//				raf.close();
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//		
//		return false;	
//	}
    //********************************************************************	

}