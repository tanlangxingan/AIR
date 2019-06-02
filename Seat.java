package flight.dingpiao;
import flight.assist.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.MatteBorder;

class Seat extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private String[] myString;
	private static int myIndex=-1;
	public static final int width = 830;
	public static final int height = 500;
	private static final long serialVersionUID = 1L;
	private SqlBean sqlBean = new SqlBean();
	private Client client=new Client();
	static JFrame clientFrame=new JFrame();
	
	
	int zuoweihao = -1;
	String s = "波音737-300（128座）";
	JLabel a = new JLabel(s);
	JButton b[]={new JButton("确定"),new JButton("取消")};
	JButton d[]={new JButton(""),new JButton(""),new JButton(""),new JButton(""),
			new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),
	new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton(""),new JButton("")};
	int juli1[]={-10,40,125,175};
	int juli2[]={0,35,70,130,165,200};
	JLabel e=new JLabel("1   2    3   4   5   6  7   8  9  10  11  12 13  14 15  16 17  "
			+ "18  19 20  21 22  23 24  25 26  27 28  29  30");
	JLabel f[]={new JLabel("A"),new JLabel("B"),new JLabel("E"),new JLabel("F"),new JLabel("A"),new JLabel("B"),
			new JLabel("C"),new JLabel("D"),new JLabel("E"),new JLabel("F")};
	public Seat(Integer index,String[]string) throws SQLException
	{
		myString=string;
		myIndex=index;
		setTitle("选择座位");
		setSize(width,height);
		Container c = getContentPane();
		c.setLayout(null);
		((JComponent) c).setBorder(new MatteBorder(new ImageIcon("src/f.gif")));
		//为标签设置及添加到框架
		a.setBounds(300,50,250,25);
		a.setFont(new Font("宋体",Font.PLAIN,20));
		c.add(a);
		String query="SELECT seatNumber FROM seat WHERE myIndex = "+index;
		ResultSet result=sqlBean.executeQuery(query);
		ArrayList<Integer> seatList = new ArrayList<Integer>();
		while(result.next()){
			seatList.add(result.getInt("seatNumber"));
		}
		//添加座位
		for(int n=0;n<2;n++){
			for(int m=0+n*4;m<4*(n+1);m++){
				d[m].setBounds(30+n*25,150+juli1[m-4*n],15,35);
				c.add(d[m]);
				d[m].setBackground(Color.white);
				//
				if( isExist(seatList,m+1) ){
					d[m].setBackground(Color.GRAY);
					d[m].setSelected(false);
				}
				d[m].addActionListener(this);
			}
		}
		for(int n=0;n<28;n++){
			for(int m=8+n*6;m<8+6*(n+1);m++){
				d[m].setBounds(95+n*25,135+juli2[m-8-6*n],15,30);
				c.add(d[m]);
				d[m].setBackground(Color.white);
				//
				if( isExist(seatList,m+1) ){
					d[m].setBackground(Color.GRAY);
					d[m].setSelected(false);
				}
				d[m].addActionListener(this);
			}
		}
		//座位分布123&abc
		e.setBounds(30,110,820,25);
		e.setFont(new Font("宋体",Font.PLAIN,14));
		c.add(e);
		for(int n=0;n<4;n++)
		{
			f[n].setFont(new Font("宋体",Font.PLAIN,16));
			f[n].setBounds(15,160+juli1[n],15,15);
			c.add(f[n]);
		}
		for(int n=4;n<10;n++)
		{
			f[n].setFont(new Font("宋体",Font.PLAIN,16));
			f[n].setBounds(80,145+juli2[n-4],15,15);
			c.add(f[n]);
		}
		//确定&取消
		b[0].setBounds(220,400,95,25);
		c.add(b[0]); 
		b[1].setBounds(535,400,95,25); 
		c.add(b[1]);
		b[0].addActionListener(this);
		b[1].addActionListener(this);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent evt)
    {
		for(int i=0;i<128;i++){
			if(evt.getSource()==d[i]&&zuoweihao==-1){
				zuoweihao = i+1;
				d[i].setBackground(Color.blue);
			}
		}
		if(evt.getSource()==b[1]){
			if(zuoweihao>0){
				d[zuoweihao-1].setBackground(Color.white);
				zuoweihao=-1;
			}
		}else if(evt.getSource()==b[0]){
			clientFrame.getContentPane().add(client.panel(myString,zuoweihao,myIndex));
			clientFrame.setTitle("客户资料");
			clientFrame.setSize(450,460);					
			clientFrame.setVisible(true);
			this.setVisible(false);
			
			client.jbtname.setText("");
			client.jbtid.setText("");
//				client.jbtqq.setText("");
			client.jbtps.setText("");
			client.jbtadultticketnumber.setText("1");
		}
		
    }
	public boolean isExist(ArrayList<Integer>array,int num){
		for(int seat:array){
			if(seat==num){
				return true;
			}
		}
		return false;
	}
//	public static void main(String[] args) throws SQLException
//	{
//		int index=1;
//		JFrame frame = new Seat(index);
//	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    frame.show(); 
//	    frame.setVisible(true);
//	}
}
