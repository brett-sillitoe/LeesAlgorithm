import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Queue;
import java.util.ArrayDeque;

public class lees
{
  public lees() {}
  
  static int clicks = 0;
  
  static int[][] board = new int[20][20];
  static button[][] buttons = new button[20][20];
  
  static int sX;
  static int sY;
  static int eX;
  static int eY;
  
  static boolean isStart;
  static boolean isEnd;
  
  static int[] row = {-1, 0, 0, 1};
  static int[] col = {0, -1, 1, 0};
  
  public static void main(String[] paramArrayOfString)
  {
    JFrame localJFrame = new JFrame("get rekt");
    localJFrame.setDefaultCloseOperation(3);
    localJFrame.setSize(1366, 768);
    localJFrame.setLocation(30, 20);
    int i = 20;
    int j = 20;
	
	for(int a=0; a<20; a++){
		for(int b=0; b<20; b++){
			board[b][a] = 1;
		}
	}
	
    
    JPanel localJPanel1 = new JPanel();
    localJPanel1.setLayout(new GridLayout(j, i));
    for (int k = 0; k < i * j; k++) {
      button localObject = new button((k%20), (k/20));
	  buttons[k%20][k/20] = localObject;
	  localObject.addActionListener(new ActionListener(){
		  @Override
		  public void actionPerformed(ActionEvent e){
			  colorButton(localObject);
		  }
	  });
      localObject.setPreferredSize(new Dimension(30, 30));
	  localJPanel1.setBackground(Color.BLACK);
      localJPanel1.add(localObject);
    }
    
    JPanel localJPanel2 = new JPanel(new FlowLayout(1, 0, 0));
	localJPanel2.setBackground(Color.BLACK);
    localJPanel2.add(localJPanel1);
    Object localObject = new javax.swing.JScrollPane(localJPanel2);
    localJFrame.getContentPane().add((Component)localObject);
    
    localJFrame.pack();
    localJFrame.setLocationRelativeTo(null);
    JButton localJButton = new JButton("Run Algorithm");
	localJButton.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){
			runAlgorithm();
		}
	});
    localJFrame.getContentPane().add("South", localJButton);
   
    localJFrame.setVisible(true);
	
  }
  
  public static boolean isValid(boolean[][] v, int a, int x, int y){
	  //THIS COULD BE SUPER WRONG LUL YOLO
	  return (y+col[a]>=0)&&(y+col[a]<20)&&(x+row[a]>=0)&&(x+row[a]<20)&&(board[x+row[a]][y+col[a]]==1)&&(!v[x+row[a]][y+col[a]]);
  }
  
  public static void runAlgorithm(){
	  boolean[][] visited = new boolean[20][20];
	  Queue<button> q = new ArrayDeque<>();
	  int x;
	  int y;
	  int dist;
	  
	  visited[sX][sY] = true;
	  buttons[sX][sY].distance = 0;
	  q.add(buttons[sX][sY]);
	  
	  int minDist = Integer.MAX_VALUE;
	  
	  while(!q.isEmpty()){
		  button b = q.poll();
		  x = b.x;
		  y = b.y;
		  dist = b.distance;
		  
		  if(x==eX && y==eY){
			  minDist = dist;
			  break;
		  }
		  
		  for(int k=0; k<4; k++){
			  if(isValid(visited, k, x, y)){
				  visited[x+row[k]][y+col[k]] = true;
				  buttons[x+row[k]][y+col[k]].distance = dist + 1;
				  q.add(buttons[x+row[k]][y+col[k]]);
			  }
		  }
	  }
	  if(minDist!=Integer.MAX_VALUE){
		  System.out.println("The shortest path is " +minDist);
		  colorPath(buttons[eX][eY]);
	  }
	  else{
		  System.out.println("No possible path");
	  }
	  
  }
  
  public static void colorPath(button b){
	  int min = Integer.MAX_VALUE;
	  boolean[][] v = new boolean[20][20];
	  int x = 0;
	  int y = 0;
	  if(b.x!=sX || b.y!=sY){
		if(b.x!=eX || b.y!=eY){
			buttons[b.x][b.y].paintBlue();
		}
		for(int k=0; k<4; k++){
			if(isValid(v, k, b.x, b.y)){
				if(buttons[b.x+row[k]][b.y+col[k]].distance<min){
					min = buttons[b.x+row[k]][b.y+col[k]].distance;
					x = b.x+row[k];
					y = b.y+col[k];

				}
			}
		}
		colorPath(buttons[x][y]);
	  }
	  
  }
  
  public static void colorButton(button b){
	if(b.getBackground() == Color.WHITE){
		if(isStart == false){
			b.paintGreen();
			sX = b.x;
			sY = b.y;
			isStart = true;
		}
		
		else if(isEnd == false){
			b.paintRed();
			eX = b.x;
			eY = b.y;
			isEnd = true;
		}
		
		else{
			b.paintBlack();
			board[b.x][b.y] = 0;
		}
	}
	else if(b.getBackground() == Color.BLACK){
		if(isStart == false){
			b.paintGreen();
			sX = b.x;
			sY = b.y;
			isStart = true;
		}
		
		else if(isEnd == false){
			b.paintRed();
			eX = b.x;
			eY = b.y;
			isEnd = true;
		}
		
		else{
			b.paintWhite();
			board[b.x][b.y] = 1;
		}
	}
	else if(b.x == sX && b.y == sY){
		if(isEnd == false){
			b.paintRed();
			eX = b.x;
			eY = b.y;
			isEnd = true;
		}
		else{
			b.paintWhite();
			board[b.x][b.y] = 1;
		}
		sX = -1;
		sY = -1;
		isStart = false;
	}
	else if (b.x == eX && b.y == eY){
		b.paintWhite();
		board[b.x][b.y] = 1;
		eX = -1;
		eY = -1;
		isEnd = false;
		
	}
  }
}

class button extends JButton{
	int x;
	int y;
	int distance;
	
	button(int x, int y){
		this.distance = Integer.MAX_VALUE;
		this.x = x;
		this.y = y;
		this.setBackground(Color.WHITE);
		this.setBorderPainted(true);
	}
	
	public void paintGreen(){
		this.setBackground(new Color(9,148,65));
	}
	
	public void paintRed(){
		this.setBackground(new Color(237, 41, 57));
	}
	
	public void paintBlue(){
		this.setBackground(new Color(30, 144, 255));
	}
	
	public void paintBlack(){
		this.setBackground(Color.BLACK);
	}
	
	public void paintWhite(){
		this.setBackground(Color.WHITE);
	}
}