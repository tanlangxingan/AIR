package flight.dingpiao;

import flight.assist.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.sql.*;

public class Client extends JFrame implements ActionListener
{
	private JLabel name=new JLabel("** 真实姓名:");
	private JLabel id=new JLabel("** 证件号码:");
	private JLabel start=new JLabel("    出发地点:");
	private JLabel end=new JLabel("    到达地点:");
	private JLabel id1=new JLabel("(身份证号码)");
	private JLabel starttime=new JLabel("    出发时间:");
	private JLabel returntime=new JLabel("    返回时间:");
	private JLabel flight=new JLabel("    航 班 号  :");
	private JLabel ps=new JLabel("     简短附言:  ");
	private JLabel returnflight=new JLabel("返回航班号:");
	private JLabel adultticketnumber=new JLabel("    票数:");
	private JLabel airfirm=new JLabel("    航空公司:");;
	static JTextField jbtname=new JTextField(" ",12);
	static JTextField jbtid=new JTextField(" ",20);
	private JTextField jbtstart=new JTextField(" ",12);
	private JTextField jbtend=new JTextField(" ",12);
	private JTextField jbtstarttime=new JTextField(" ",24);
	private JTextField jbtreturntime=new JTextField(" ",24);
	static JTextField jbtadultticketnumber=new JTextField("0",12);
	private JTextField jbtreturnflight=new JTextField(12);
	private JTextField jbtairfirm=new JTextField(12);
	private JTextField jbtflight=new JTextField(12);
	static JTextArea jbtps=new JTextArea(10,3);
	private JButton handin=new JButton("完成并提交");
	private JButton rewrite=new JButton("重 新 输 入");
	private JButton return1=new JButton("返回实时订票");
	private JPanel p10=new JPanel();
	private String[] string=new String[23];
	private SeatInfo seatinformation=new SeatInfo();
	private SqlBean sqlBean=new SqlBean();
	private int myIndex=-1;
	private int mySeatNumber=-1;
   	public Client()
	{	
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception ex)
		{
		}
		jbtstarttime.setEditable(false);
		jbtreturntime.setEditable(false);
		jbtstart.setEditable(false);
		jbtend.setEditable(false);
		jbtflight.setEditable(false);
		jbtairfirm.setEditable(false);
		jbtreturnflight.setEditable(false);
		jbtadultticketnumber.setEditable(false);
		//jbtstyle.setEditable(false);
		JPanel p1=new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(name);
		p1.add(jbtname);
		JPanel p2=new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		p2.add(id);
		p2.add(jbtid);
		p2.add(id1);
		JPanel p3=new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.add(start);
		p3.add(jbtstart);
	//	p3.add(jbtstyle);
		JPanel p4=new JPanel();
		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		p4.add(end);
		p4.add(jbtend);
//		p4.add(airfirm);
//		p4.add(jbtairfirm);
		JPanel p5=new JPanel();
		p5.setLayout(new FlowLayout(FlowLayout.LEFT));
		p5.add(starttime);
		p5.add(jbtstarttime);
		p5.add(returntime);
		returntime.setVisible(false);
		p5.add(jbtreturntime);
		JPanel p6=new JPanel();
		p6.setLayout(new FlowLayout(FlowLayout.LEFT));
		p6.add(adultticketnumber);
		p6.add(jbtadultticketnumber);
		JPanel p7=new JPanel();
		p7.setLayout(new FlowLayout(FlowLayout.LEFT));
	//	p7.add(jbtemail);
		JPanel p9=new JPanel();
		jbtps.setLineWrap(true);
		jbtps.setBorder(new LineBorder(new Color(220,220,255),2));
		p9.setLayout(new BorderLayout());
		p9.add(ps,BorderLayout.WEST);
		JScrollPane scrollPane=new JScrollPane(jbtps);
		p9.add(scrollPane,BorderLayout.CENTER);
		JPanel p11=new JPanel();
		p11.setLayout(new FlowLayout(FlowLayout.CENTER));
		p11.add(handin);
		p11.add(rewrite);
		p11.add(return1);
		JPanel p12=new JPanel();
		p12.setLayout(new FlowLayout(FlowLayout.LEFT));
		p12.add(flight);
		p12.add(jbtflight);
		p12.add(returnflight);
		returnflight.setVisible(false);
		p12.add(jbtreturnflight);
		p10.setBorder(new MatteBorder(new ImageIcon("src/f.gif")));
		p10.setLayout(null);
		JLabel title=new JLabel("(带**的必须填写)",JLabel.LEFT);
		p10.add(title);
		p10.add(p1);
		p10.add(p2);
		p10.add(p3);
		p10.add(p4);
		p10.add(p5);
		p10.add(p6);
		p10.add(p7);		
		p10.add(p9);
		p10.add(p11);
		p10.add(p12);
		title.reshape(60,20,350,10);
		p1.reshape(40,30,380,30);
		p2.reshape(40,60,380,30);
		p3.reshape(40,90,380,30);
		p4.reshape(40,120,380,30);
		p5.reshape(40,150,380,30);
		p12.reshape(40,180,380,30);
		p6.reshape(40,210,380,30);
		p7.reshape(40,240,380,30);		
		p9.reshape(40,275,345,80);
		p11.reshape(72,365,350,30);
		rewrite.addActionListener(this);
		handin.addActionListener(this);
		return1.addActionListener(this);
	}
	public JPanel panel(String[] string,int seatNumber,int index)
	{
		mySeatNumber=seatNumber;
		myIndex=index;
		this.string=string;	
		jbtstart.setText(string[0]);
		jbtend.setText(string[1]);
		jbtstarttime.setText(string[22]);
		jbtairfirm.setText(string[6]);
		jbtflight.setText(string[4]);
//		jbtstyle.setText(string[8]);
		//single
		if(string[5].equals("单程"))
		{
			jbtreturnflight.setVisible(false);
			returnflight.setVisible(false);
			returntime.setVisible(false);
			jbtreturntime.setVisible(false);
		}
		//double
		jbtreturnflight.setText(string[11]);
		jbtreturntime.setText(string[9]);
		return p10;
	}
	int adultnumber=0;
	int childnumber=0;
	int ticketnumber=0;
	public void actionPerformed(ActionEvent e)
	{
		int len1=jbtname.getText().trim().length();
		int len2=jbtid.getText().trim().length();
		int len3=jbtadultticketnumber.getText().trim().length();

		if(e.getSource()==handin)
		{			
			string[12]=jbtname.getText().trim();
			string[13]=jbtid.getText().trim();			
		
			int i=0;// leftticket for single
			int j=0;// leftticket for double and multiple
			if(len1==0||len2==0||len3==0)
			{
				
				String str=getstring(len1,len2,len3);
				JOptionPane.showMessageDialog(this,str,"错误信息！",JOptionPane.ERROR_MESSAGE);
			
			}   
			else
			{
				if(Integer.parseInt(jbtadultticketnumber.getText().trim())==0)
				{
					JOptionPane.showMessageDialog(this,"票数总数不能为0!","错误信息！",JOptionPane.ERROR_MESSAGE);
			
				}
				else
				{
             		ticketnumber=1;
				////single
				if(string[5].toString().trim().equals("单程"))
				{       
							float piaojia=0;
							try
							{
								
								ResultSet rs = sqlBean.executeQuery("select price,myIndex from flight_information where hangbanhao='"+string[4]+"'"
										+" and "+"leaveTime="+"\'"+string[22]+"'");
								while(rs.next())
								{
									piaojia=rs.getFloat("price");
									myIndex=rs.getInt("myIndex");
								}
								
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
							}
				
							String dingdan=string[3]+string[4]+String.valueOf((int)(100*Math.random()));
							string[16]=dingdan;
							string[21]=""+piaojia;
							JOptionPane.showMessageDialog(this,"   恭喜！提交成功\n你的定单号是"+dingdan+"\n"+"你应付价钱为"+piaojia,
							                              "客户信息",JOptionPane.INFORMATION_MESSAGE);
							String insert="insert into booking_record(myIndex,ID,name,airCompany,time,dingdanhao,price,seatNumber)"
									+ " values("+myIndex+",\'"+string[13]+"\',"+"\'"+string[12]+"\',"+"\'"+
							        string[6]+"\',"+"\'"+string[22]+"\',"+"\'"+dingdan+"\',"+piaojia+","+mySeatNumber+")";
							sqlBean.executeInsert(insert);
							String insert2="insert into seat values("+myIndex+","+mySeatNumber+")";
							sqlBean.executeInsert(insert2);
							ResultSet rs=sqlBean.executeQuery("select seatNumber from flight_information where myIndex="+myIndex);
							int seatNum=0;
							try {
								if(rs.next()){
									seatNum=rs.getInt("seatNumber")-1;
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							sqlBean.executeUpdate("update flight_information set seatNumber="+seatNum+" where myIndex="+myIndex);
							Hangkong.clientFrame.setVisible(false);
							Hangkong.clientFrame.dispose();
							Hangkong.frame.setVisible(true);
				}	
			}
				
	    }
		
	}
		if(e.getSource()==rewrite)
		{
			jbtname.setText("");
			jbtid.setText("");
			jbtadultticketnumber.setText("");
			jbtps.setText("");
		}
		if(e.getSource()==return1)
		{
			Hangkong.clientFrame.setVisible(false);
			Hangkong.clientFrame.dispose();
			Hangkong.frame.setVisible(true);
			jbtname.setText("");
			jbtid.setText("");
//			jbtemail.setText("");
			jbtps.setText("");
			jbtadultticketnumber.setText("0");
	
		}
	
	}
	public String getstring(int i,int j,int r)
	{
		if(i==0)
		return "姓名不能为空！";
		else if(j==0)
		return "证件号不能为空！";
		else if(r==0)
		return "成人票数不能为空！";
		return "ERROR!";
	}
}