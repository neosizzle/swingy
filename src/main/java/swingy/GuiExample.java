// package swingy;

// import java.awt.event.ActionListener;
// import java.awt.event.ActionEvent;
// import java.util.Scanner;

// import javax.swing.JButton;
// import javax.swing.JFrame;
// import javax.swing.WindowConstants;

// /**
//  * Hello world!
//  *
//  */
// public class App 
// {
//     static boolean guimode;
//     static JFrame f=new JFrame();//creating instance of JFrame 
//     static int count;

//     private static void disableGUI()
//     {
//         guimode = false;
//         f.setVisible(false);
//     }

//     private static void changeState()
//     {
//         ++count;
//     }

//     public static void main( String[] args )
//     {
//         guimode = false;
//         count = 0;

//         JButton b=new JButton("terminal ui");//creating instance of JButton  
//         b.setBounds(130,100,100, 40);//x axis, y axis, width, height  
//         b.addActionListener(new ActionListener(){  
//             public void actionPerformed(ActionEvent e){  
//                         disableGUI();
//                     }  
//                 });  
                
//         JButton b2=new JButton(count + "count");//creating instance of JButton  
//         b2.setBounds(230,100,100, 40);//x axis, y axis, width, height  
//         b2.addActionListener(new ActionListener(){  
//             public void actionPerformed(ActionEvent e){  
//                         changeState();
//                     }  
//                 });  

//         f.add(b2);
//         f.add(b);//adding button in JFrame  
                
//         f.setSize(400,500);//400 width and 500 height  
//         f.setLayout(null);//using no layout managers
//         f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//         f.setVisible(guimode);//making the frame visible  

//         while (true) {
//             Scanner sc= new Scanner(System.in); //System.in is a standard input stream  
//             System.out.print(count + ">");  
//             String str= sc.nextLine();              //reads string  
//             if (guimode) System.out.println("cant read");
//             else if (str.equals("gui"))
//             {
//                 f.setVisible(true);
//                 guimode = true;
//             }
//             else{
//                 System.out.println("You have entered: "+str); 
//                 if (str.equals("changeState")) ++count;
//             }
//         }
        
//     }
// }

