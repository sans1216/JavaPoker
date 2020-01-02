package card;

import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.*;
import java.awt.Font;


public class Server extends JFrame implements Players{
	public static void main(String[] args){
		Server frame = new Server();
	}
	public JTextArea jtaLog = new JTextArea();
	public JScrollPane scrollPane = new JScrollPane(jtaLog);
	public Server(){

		//创建服务器界面
		
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		
		JLabel label = new JLabel("\u7EA2\u4E94\u68CB\u724C\u5BA4");
		label.setIcon(null);
		label.setFont(new Font("微软雅黑 Light", Font.PLAIN, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(label);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900,800);
		this.setTitle("Server");
		this.setVisible(true);

		try{
			ServerSocket serverSocket = new ServerSocket(8000); 
			jtaLog.append(new Date() + ":欢迎来到快乐棋牌室：）" + '\n');
			//桌子1
			int sessionNO = 1; //Number a session
			jtaLog.append(new Date() + ":等待玩家加入" + sessionNO +"号桌"+ '\n');
			//玩家1
			Socket player1 = serverSocket.accept();
			jtaLog.append(new Date() + ":一号玩家"+ player1.getInetAddress().getHostAddress()+"加入到" + sessionNO +"号桌"+ '\n');
			//玩家2
			Socket player2 = serverSocket.accept();
			jtaLog.append(new Date() + ":二号玩家"+ player2.getInetAddress().getHostAddress()+"加入到" + sessionNO +"号桌"+ '\n');
			//玩家3
			Socket player3 = serverSocket.accept();
			jtaLog.append(new Date() + ":三号玩家"+ player3.getInetAddress().getHostAddress()+"加入" + sessionNO +"号桌"+ '\n');
			//玩家4
			Socket player4 = serverSocket.accept();
			jtaLog.append(new Date() + ":四号玩家"+ player4.getInetAddress().getHostAddress()+"加入" + sessionNO +"号桌"+ '\n');

			jtaLog.append("------进入" + sessionNO +"号桌------" + '\n');

			HandleASession task = new HandleASession(player1,player2,player3,player4);
			new Thread(task).start();
		}
		catch(IOException ex){
			System.err.println(ex);
		}
	}
}
class HandleASession implements Runnable,Players{
	private Socket player1;
	private Socket player2;
	private Socket player3;
	private Socket player4;

	private ObjectInputStream fromPlayer1;
	private ObjectOutputStream toPlayer1;
	private ObjectInputStream fromPlayer2;
	private ObjectOutputStream toPlayer2;
	private ObjectInputStream fromPlayer3;
	private ObjectOutputStream toPlayer3;
	private ObjectInputStream fromPlayer4;
	private ObjectOutputStream toPlayer4;
	
	
	Server server = new Server();
	JTextArea jtaLog =server.jtaLog;
	JScrollPane scrollPane = server.scrollPane;
	private int[] f = new int[4];
	public HandleASession(Socket player1,Socket player2,Socket player3,Socket player4){
		this.player1 = player1;
		this.player2 = player2;
		this.player3 = player3;
		this.player4 = player4;
	}
	public void run() {

		try {
			ArrayList<Card> ac = new ArrayList<Card>();
			int result=(int)(Math.random()*4);
			//随机生成牌组
			for(int n = 1;n<=2;n++){
				//共循环两次
				Card c1 = new Card("",5,"大王  ",999,999);
				//大小王没有花色，花色值为5，卡面值为777（最大）
				if(n==1) c1.setValue(c1.getValue()*2);
				else if(n==2) c1.setValue(c1.getValue()*2+1);
				ac.add(c1);
				Card c2 = new Card("",5,"小王  ",998,998);
				if(n==1) c2.setValue(c2.getValue()*2);
				else if(n==2) c2.setValue(c2.getValue()*2+1);
				ac.add(c2);
				Card c = null;
				String s = null;
				for(int i=1;i<=4;i++){
                    if(result==0){
                    	if(i==1) s = "方块";
    					else if(i==2) s = "梅花";
    					else if(i==3) s = "红桃";
    					else if(i==4) s = "黑桃";
					}else if(result==1){
						if(i==1) s = "方块";
						else if(i==2) s = "梅花";
						else if(i==3) s = "黑桃";
						else if(i==4) s = "红桃";
					}else if(result==2) {
						if(i==1) s = "方块";
						else if(i==2) s =  "红桃";
						else if(i==3) s =  "黑桃";
						else if(i==4) s =  "梅花";
					}else {
						if(i==1) s = "黑桃";
						else if(i==2) s = "红桃";
						else if(i==3) s = "梅花";
						else if(i==4) s = "方块";
					}
					
					for(int j=1;j<=13;j++){
						if(j==1) c = new Card(s,i,"A",14,i*20+14);
						else if(j==2) c = new Card(s,i,"2",200,i*20+200);
						else if(j==3) c = new Card(s,i,"3",3,i*20+3);
						else if(j==4) c = new Card(s,i,"4",4,i*20+4);
						else if(j==5) c = new Card(s,i,"5",5,i*20+5);
						else if(j==6) c = new Card(s,i,"6",6,i*20+6);
						else if(j==7) c = new Card(s,i,"7",7,i*20+7);
						else if(j==8) c = new Card(s,i,"8",8,i*20+8);
						else if(j==9) c = new Card(s,i,"9",9,i*20+9);
						else if(j==10) c = new Card(s,i,"10",10,i*20+10);
						else if(j==11) c = new Card(s,i,"J",11,i*20+11);
						else if(j==12) c = new Card(s,i,"Q",12,i*20+12);
						else if(j==13) c = new Card(s,i,"K",13,i*20+13);
						if(c.getColorName()=="♥"&& c.getColorNumber()==5) c.setValue(1000);
						if(c.getColorName()=="♠"&& c.getColorNumber()==3) c.setValue(997);
						if(c.getColorName()=="♣"&& c.getColorNumber()==3) c.setValue(996);
						if(n==1) c.setValue(c.getValue()*2);
						else if(n==2) c.setValue(c.getValue()*2+1);
						ac.add(c);
						//红五 大王 小王 黑桃3 梅花3
					}
				}
			}
			Collections.shuffle(ac); //洗牌

			//发牌给玩家一
			TreeSet<Card> tc1 = new TreeSet<Card>(ac.subList(75, 100));
			Iterator<Card> iterator1 = tc1.iterator();
			toPlayer1 = new ObjectOutputStream(player1.getOutputStream());
			toPlayer1.writeObject(PLAYER1);
			while(iterator1.hasNext()){
				Card it = iterator1.next();
				toPlayer1.writeObject(it);
			}
			//发牌给玩家二
			TreeSet<Card> tc2 = new TreeSet<Card>(ac.subList(50, 75));
			Iterator<Card> iterator2 = tc2.iterator();
			toPlayer2 = new ObjectOutputStream(player2.getOutputStream());
			toPlayer2.writeObject(PLAYER2);
			while(iterator2.hasNext()){
				Card it = iterator2.next();
				toPlayer2.writeObject(it);
			}
			//发牌给玩家三
			TreeSet<Card> tc3 = new TreeSet<Card>(ac.subList(25, 50));
			Iterator<Card> iterator3 = tc3.iterator();
			toPlayer3 = new ObjectOutputStream(player3.getOutputStream());
			toPlayer3.writeObject(PLAYER3);
			while(iterator3.hasNext()){
				Card it = iterator3.next();
				toPlayer3.writeObject(it);
			}
			//发牌给玩家四
			TreeSet<Card> tc4 = new TreeSet<Card>(ac.subList(0, 25));
			Iterator<Card> iterator4 = tc4.iterator();
			toPlayer4 = new ObjectOutputStream(player4.getOutputStream());
			toPlayer4.writeObject(PLAYER4);
			while(iterator4.hasNext()){
				Card it = iterator4.next();
				toPlayer4.writeObject(it);
			}
			String flower="黑桃";
			if(result==0) {
				flower="黑桃";
			}else if(result==1){
				flower="红桃";
			}else if(result==2) {
				flower="梅花";
			}else {
				flower="方块";
			}
			

			jtaLog.append("---------洗牌---------"+ '\n');

			jtaLog.append("---------发牌---------"+ '\n');

			jtaLog.append("---------随机主牌："+flower+"--------"+ '\n');
			
			jtaLog.append("---------玩家开始出牌---------"+ '\n');
			try {
				fromPlayer1 = new ObjectInputStream(player1.getInputStream());
				fromPlayer2 = new ObjectInputStream(player2.getInputStream());
				fromPlayer3 = new ObjectInputStream(player3.getInputStream());
				fromPlayer4 = new ObjectInputStream(player4.getInputStream());
				while(true){
					int[] score = new int [4];
					//从客户端接收每个玩家发过来的牌

					Card c1 = (Card)fromPlayer1.readObject();
					jtaLog.append("玩家1出牌："+c1.getColorName()+c1.getCard()+ '\n');
					score[0] = c1.getValue();
					if(score[0] != 0)toPlayer2.writeObject(2);

					Card c2 = (Card)fromPlayer2.readObject();
					
					jtaLog.append("玩家2出牌："+c2.getColorName()+c2.getCard()+ '\n');
					score[1] = c2.getValue();
					if(score[1] != 0)toPlayer3.writeObject(3);

					Card c3 = (Card)fromPlayer3.readObject();
					jtaLog.append("玩家3出牌："+c3.getColorName()+c3.getCard()+ '\n');
					score[2] = c3.getValue();
					if(score[2] != 0)toPlayer4.writeObject(4);

					Card c4 = (Card)fromPlayer4.readObject();
					jtaLog.append("玩家4出牌："+c4.getColorName()+c4.getCard()+ '\n');
					score[3] = c4.getValue();
					if(score[3] != 0)toPlayer1.writeObject(1);
                    int increase=0;
                    if(c1.getCard().equals("5")) {
                    	increase+=5;
                    }else if(c1.getCard().equals("10")||c1.getCard().equals("K")) {
                    	increase+=10;
                    }
                    if(c2.getCard().equals("5")) {
                    	increase+=5;
                    }else if(c2.getCard().equals("10")||c2.getCard().equals("K")) {
                    	increase+=10;
                    }
                    if(c3.getCard().equals("5")) {
                    	increase+=5;
                    }else if(c3.getCard().equals("10")||c3.getCard().equals("K")) {
                    	increase+=10;
                    }
                    if(c4.getCard().equals("5")) {
                    	increase+=5;
                    }else if(c4.getCard().equals("10")||c4.getCard().equals("K")) {
                    	increase+=10;
                    }
					//比较大小，并统计分数结果
					int[] num = {score[0],score[1],score[2],score[3]};
					int temp = 0;
					for(int i = 0;i < 3;i++ )
						for(int j = 0;j < 3-i ;j++){
							if(num[j] < num[j+1]){
								temp = num[j];
								num[j] = num[j + 1];
								num[j + 1] = temp;
							}
						}
					int max = num[0];
					for(int i = 0; i <= 3; i++){
						if(score[i] == max){
							f[i] = f[i] + increase;
							jtaLog.append("--------------"+'\n');
							jtaLog.append("本轮得分最高：玩家"+(i+1)+" 分数+"+increase+'\n');
							jtaLog.append("积分情况："+'\n');
							
							
							for(int j = 0; j <= 3; j++){
								
								jtaLog.append("玩家"+(j+1)+"得分为:"+f[j]+ '\n');
							}
							break;
						}
					}

				}
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}


