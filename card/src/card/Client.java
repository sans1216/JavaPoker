package card;

import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.ImageIcon;


public class Client extends JFrame implements Runnable,Players{
	public Client() {
		
		JLabel lblBy = new JLabel("\u6B22\u8FCE\u4E0B\u6B21\u518D\u6765\uFF01  by\uFF1A31701236 \u9A6C\u6613\u5B89");
		lblBy.setIcon(new ImageIcon(Client.class.getResource("/img/emoji.png")));
		getContentPane().add(lblBy, BorderLayout.SOUTH);
	}
	 JPanel contentPane=(JPanel)this.getContentPane();
	  JPanel jpanel1=new JPanel(new FlowLayout(FlowLayout.LEADING));
	  JPanel jpanel2=new JPanel(new FlowLayout(FlowLayout.LEADING));
	  JTextField input=new JTextField("请输入牌号",20);
	  JButton jbutton=new JButton("发送");
	  JLabel jl=new JLabel("");
	  JTextArea jta=new JTextArea("-----",3,20);
	  JScrollPane scrollPane = new JScrollPane(jta){
		   @Override
		   public Dimension getPreferredSize() {
		     return new Dimension(800, 700);
		   }
	 };	
	  
		
    public static ArrayList<Card> alc = new ArrayList<Card>();
    private String host = "localhost";
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    private boolean continueToPlay = true;
    private boolean myTurn = true;
    static boolean myTurn1 = false;
    static boolean myTurn2 = false;
    static boolean myTurn3 = false;
    static boolean myTurn4 = false;

    public static void main(String[] args){
    	Client client = new Client();
    	client.connectToServer();
    }
    
    	
    
    //连接服务器
    private void connectToServer(){
        try{
            Socket socket;
            socket = new Socket(host,8000);

            fromServer = new ObjectInputStream(socket.getInputStream());
            toServer = new ObjectOutputStream(socket.getOutputStream());
          //创建服务器界面
            
            
        }
        catch(Exception ex){
            System.err.println(ex);
        }
        Thread thread = new Thread(this);
        thread.start(); //启动线程
    }

    public void run() {
        // TODO Auto-generated method stub
        try{
            Object player = fromServer.readObject();
            
    		JLabel label = new JLabel("\u7EA2\u4E94\u68CB\u724C\u5BA4");
    		label.setFont(new Font("微软雅黑 Light", Font.PLAIN, 16));
    		label.setHorizontalAlignment(SwingConstants.CENTER);
    		scrollPane.setColumnHeaderView(label);
    		 
             contentPane.add(jpanel1,BorderLayout.NORTH);
             contentPane.add(scrollPane,BorderLayout.CENTER);
             jpanel1.add(input);
             jpanel1.add(jbutton);
             scrollPane.setBounds(200,200,400,300);
             jpanel2.add(jl);
             jpanel2.add(scrollPane);
             jbutton.setEnabled(false);
             jbutton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                 click_jbutton(e);
               }
             });
             contentPane.add(jpanel2,BorderLayout.CENTER);
             this.setVisible(true);
             this.setBounds(200,200,400,300);
    		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		this.setSize(900,800);
    		this.setTitle("client");

			int sessionNO = 1; 
		    
			jta.append("------欢迎来到"+sessionNO+"号桌------"+ '\n');
				
				//玩家1
			jta.append("------欢迎玩家加入到" + sessionNO +"号桌------"+ '\n');
	
            //玩家1接收牌
            if(player.equals(PLAYER1)){
                jta.append("玩家1："+ '\n');
                while(alc.size()<=24){
                    Card c = (Card)fromServer.readObject();
                    jta.append(""+alc.size()+":"+c.getColorName()+c.getCard()+"   ");
                    alc.add(c); 
                }

                jta.append('\n'+"");
            }

            //玩家2接收牌
            else if(player.equals(PLAYER2)){
                jta.append("玩家2："+ '\n');
                while(alc.size()<=24){
                    Card c = (Card)fromServer.readObject();
                    jta.append(""+alc.size()+":"+c.getColorName()+c.getCard()+"   ");
                    alc.add(c);
                }

                jta.append('\n'+"");
            }

            //玩家3接收牌
            else if(player.equals(PLAYER3)){

            	jta.append("玩家3："+ '\n');

                while(alc.size()<=24){
                    Card c = (Card)fromServer.readObject();
                    jta.append(""+alc.size()+":"+c.getColorName()+c.getCard()+"   ");
                    alc.add(c);
                }

                jta.append('\n'+"");
            }

            //玩家4接收牌
            else if(player.equals(PLAYER4)){
            	jta.append("玩家4："+ '\n');
                while(alc.size()<=24){
                    Card c = (Card)fromServer.readObject();
                    jta.append(""+alc.size()+":"+c.getColorName()+c.getCard()+"   ");
                    alc.add(c);
                }
                jta.append('\n'+"");
            }


            while(continueToPlay){
                if(player.equals(PLAYER1)){
                    if(myTurn) {
                        sendCard();
                        myTurn=false;
                    }
                    
                    if((Integer)fromServer.readObject()==1) {
                        myTurn1=true;
                        sendCard();
                        myTurn1 = false;
                    }
                }
                else if(player.equals(PLAYER2)){
                    if((Integer)fromServer.readObject()==2) {
                        myTurn2=true;
                        sendCard();
                        myTurn2 = false;
                    }
                }
                else if(player.equals(PLAYER3)){
                    if((Integer)fromServer.readObject()==3) {
                        myTurn3=true;
                        sendCard();
                        myTurn3 = false;
                    }
                }
                else if(player.equals(PLAYER4)){
                    if((Integer)fromServer.readObject()==4) {
                        myTurn4=true;
                        sendCard();
                        myTurn4 = false;
                    }

                }
            }
        }
        catch(Exception ex){
        }
    }
    public void click_jbutton(ActionEvent e) {
        String s;
        s=input.getText();
        int iNum = 0;
        try{
        	iNum=Integer.parseInt(s);
        	sendCard();
        }catch(Exception ex){
          JOptionPane.showMessageDialog(this,"请输入数字");
          return;
        }
        
       
      }
    private void sendCard() throws IOException{ 
        
    	jta.append("出牌序号:");
    	jbutton.setEnabled(true);
        
       int iNum = Integer.parseInt(input.getText());
       
        System.out.println(iNum);
        jta.append("  "+iNum+ '\n');
        toServer.writeObject(alc.get(iNum));
        System.out.println(alc.get(iNum).getColorName()+alc.get(iNum).getCard());
        jta.append(alc.get(iNum).getColorName()+alc.get(iNum).getCard()+'\n');
        alc.remove(iNum);

        jta.append("-------玩家的牌-------"+'\n');
        for(int j = 0; j < alc.size();j++){
            Card c = alc.get(j);
            jta.append(""+j+":"+c.getColorName()+c.getCard()+" "+'\n');
        }
        System.out.println();

        jbutton.setEnabled(false);
    }

}
