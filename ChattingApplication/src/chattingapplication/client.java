
package chattingapplication;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.*;
public class client  implements ActionListener{
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static JFrame f1=new JFrame();
    static Box vertical=Box.createVerticalBox();
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    Boolean typing;
    client(){
        p1=new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,500,50);
        f1.add(p1);
        
        ImageIcon i=new ImageIcon(ClassLoader.getSystemResource("chattingapplication/icons/3.png"));
        Image i2=i.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel l1=new JLabel(i3);
        l1.setBounds(4,15,20,20);
        p1.add(l1);
        
        l1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("chattingapplication/icons/misba.jpg"));
        Image i5=i4.getImage().getScaledInstance(40, 40,Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel l2=new JLabel(i6);
        l2.setBounds(30,3,40,40);
        p1.add(l2);
        
        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("chattingapplication/icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel l5=new JLabel(i9);
        l5.setBounds(360,17,20,20);
        p1.add(l5);
        
        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("chattingapplication/icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel l6=new JLabel(i12);
        l6.setBounds(410,17,20,20);
        p1.add(l6);
        
        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("chattingapplication/icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(8, 23,Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel l7=new JLabel(i15);
        l7.setBounds(460,14,8,23);
        p1.add(l7);
        
        JLabel l3=new JLabel("Misba");
        l3.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        l3.setBounds(80,6,90,20);
        l3.setForeground(Color.WHITE);
        p1.add(l3);
        
        JLabel l4=new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF",Font.PLAIN,8));
        l4.setBounds(80,28,90,12);
        l4.setForeground(Color.WHITE);
        p1.add(l4);
         
         Timer t = new Timer(1, new ActionListener(){
           public void actionPerformed(ActionEvent ae){
               if(!typing){
                   l4.setText("Active Now");
               }
           }
       });
       
       t.setInitialDelay(2000);
        a1=new JPanel();
        //a1.setBackground(Color.PINK);
        //a1.setBounds(5,55,510,515);
        a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        
        //f1.add(a1);
        JScrollPane sp=new JScrollPane(a1);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.setBounds(5,55,495,515);
        f1.add(sp);
        
        
        t1=new JTextField();
        t1.setBounds(5,570,410,30);
        t1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f1.add(t1);
        t1.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                l4.setText("Typing...");
                t.stop();
                typing=true;
            }
            public void keyReleased(KeyEvent ae){
                typing=false;
                if(!t.isRunning()){
                    t.start();
                } 
            }
        });
        b1=new JButton("Send");
        b1.setBounds(415,570,80,30);
        b1.setBackground(new Color(7,94,84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
        b1.addActionListener(this);
        f1.add(b1);
        
        f1.setLayout(null);
        f1.setSize(500,600);
        f1.setLocation(700,20);
        f1.setUndecorated(true);
        f1.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        
       try{
        String out=t1.getText();
        sendTextToFile(out);
        JPanel p2=formatLabel(out);
        a1.setLayout(new BorderLayout());
        JPanel right=new JPanel(new BorderLayout()) ;
        right.add(p2,BorderLayout.LINE_END );
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical,BorderLayout.PAGE_START); 
        //a1.add(p2);
        dout.writeUTF(out);
        t1.setText("");
    }catch(Exception e){
        System.out.println(e);
    }
    }
    public void sendTextToFile(String msg) throws FileNotFoundException{
        try(FileWriter f=new FileWriter("chat.txt");
            PrintWriter p=new PrintWriter(new BufferedWriter(f));){
            p.println("Misba: "+msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static JPanel formatLabel(String out){
        JPanel p3=new JPanel();
        p3.setLayout(new BoxLayout(p3,BoxLayout.Y_AXIS));
        JLabel l1=new JLabel("<html><p style = \"width : 160px\">"+out+"</p></html>");
        l1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        l1.setBackground(new Color(37,211,102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));
        
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
        JLabel l2=new JLabel();
        l2.setText(sdf.format(cal.getTime())) ;
        p3.add(l1);
        p3.add(l2);
        return p3;
    }
    public static void main(String[] args){
        new client().f1.setVisible(true);
        try{
            s=new Socket("127.0.0.1",6001);
            din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            String msginput="";
            while(true){
            a1.setLayout(new BorderLayout());
	        msginput = din.readUTF();
            	JPanel p2 = formatLabel(msginput);
                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);
                
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f1.validate();
            
            }
        }catch(Exception e){
            
        }
    }
}