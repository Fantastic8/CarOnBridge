package panel;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resource.Car;

public class MainPanel extends JFrame{
	
	//frame component
	private JPanel MPanel;
	private int xframeold;
	private int yframeold;
	private ImageIcon ImageIconMainBG;
	private JLabel LabelMainBG;
	private JLabel Labelsafedistance;
	private JLabel Labelavaliable;
	private JLabel LabelBridgeHint;
	private JLabel LabelBridgeHintNumber;
	private JLabel LabelCreateCar;
	private JSpinner Spinneravaliable;
	private JSpinner Spinnersafedistance;
	
	private Integer safedistance;
	private Integer maxavaliable;
	private boolean Iscreatecar;
	
	public void ConfigFrame()
	{
		setUndecorated(true);//remove border
		setResizable(false);//disable resize
		setBounds(200, 400, 1500, 400);
		this.setSize(1500, 400);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MPanel=(JPanel)getContentPane();
		MPanel.setOpaque(false);
		MPanel.setLayout(null);
		
		//Background
		ImageIconMainBG=new ImageIcon(MainPanel.class.getResource("/UI/CarOnBridgeBG.jpg"));
		LabelMainBG=new JLabel(ImageIconMainBG);
		LabelMainBG.setBounds(0, 0, ImageIconMainBG.getIconWidth(),ImageIconMainBG.getIconHeight());
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(LabelMainBG, new Integer(Integer.MIN_VALUE));
		
		//settings 
		//safedistance
		Labelsafedistance=new JLabel("Safe Distance:");
		Labelsafedistance.setBounds(70, 50, 150, 30);
		Labelsafedistance.setFont(new Font("Lao UI", Font.PLAIN, 18));
		
		safedistance=100;
		Spinnersafedistance=new JSpinner();
		Spinnersafedistance.setBounds(200, 50, 100, 30);
		Spinnersafedistance.setFont(new Font("Lao UI", Font.PLAIN, 18));
		Spinnersafedistance.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if((int)Spinnersafedistance.getValue()>200)
				{
					Spinnersafedistance.setValue(200);
				}
				if((int)Spinnersafedistance.getValue()<10)
				{
					Spinnersafedistance.setValue(10);
				}
				safedistance=(int)Spinnersafedistance.getValue();
			}
		});
		Spinnersafedistance.setValue(safedistance.intValue());
		
		MPanel.add(Labelsafedistance);
		MPanel.add(Spinnersafedistance);
		
		//avaliable
		Labelavaliable=new JLabel("Avaliable cars:");
		Labelavaliable.setBounds(350, 50, 150, 30);
		Labelavaliable.setFont(new Font("Lao UI", Font.PLAIN, 18));
		
		maxavaliable=1;
		Spinneravaliable=new JSpinner();
		Spinneravaliable.setBounds(480, 50, 100, 30);
		Spinneravaliable.setFont(new Font("Lao UI", Font.PLAIN, 18));
		Spinneravaliable.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if((int)Spinneravaliable.getValue()>10)
				{
					Spinneravaliable.setValue(10);
				}
				if((int)Spinneravaliable.getValue()<1)
				{
					Spinneravaliable.setValue(1);
				}
				maxavaliable=(int)Spinneravaliable.getValue();
				Car.setMaxavaliable(maxavaliable);
			}
		});
		Spinneravaliable.setValue(maxavaliable.intValue());
		
		MPanel.add(Labelavaliable);
		MPanel.add(Spinneravaliable);
		
		//bridge hint
		LabelBridgeHint=new JLabel("Number of Cars on Bridge");
		LabelBridgeHint.setFont(new Font("Lao UI", Font.PLAIN, 25));
		LabelBridgeHint.setBounds(600, 200, 300, 50);
		MPanel.add(LabelBridgeHint);
		
		LabelBridgeHintNumber=new JLabel("0");
		LabelBridgeHintNumber.setFont(new Font("Lao UI", Font.PLAIN, 70));
		LabelBridgeHintNumber.setBounds(725, 250, 200, 70);
		MPanel.add(LabelBridgeHintNumber);
		
		//is create cars
		Iscreatecar=true;
		LabelCreateCar=new JLabel("Stop Creating Cars");
		LabelCreateCar.setFont(new Font("Lao UI", Font.PLAIN, 30));
		LabelCreateCar.setBounds(1100, 50, 400, 30);
		MPanel.add(LabelCreateCar);
		LabelCreateCar.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e)
			{
				if(Iscreatecar)
				{
					LabelCreateCar.setText("Continue Creating Cars");
				}
				else
				{
					LabelCreateCar.setText("Stop Creating Cars");
				}
				Iscreatecar=!Iscreatecar;
			}
		});
		//border button
		//Close Button
		ImageIcon ImageIconExit=new ImageIcon(MainPanel.class.getResource("/UI/Exit.png"));
		JLabel LabelExit = new JLabel(ImageIconExit);
		LabelExit.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e)
			{
				System.exit(0);
			}
		});
		LabelExit.setBounds(MainPanel.this.getWidth()-32, 0, 30, 33);
		MPanel.add(LabelExit);
		//Minimize Button
		ImageIcon ImageIconMinimize=new ImageIcon(MainPanel.class.getResource("/UI/Minimize.png"));
		JLabel LabelMinimize=new JLabel(ImageIconMinimize);
		LabelMinimize.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e)
			{
				MainPanel.this.setExtendedState(MainPanel.this.ICONIFIED);//minimize
			}
		});
		LabelMinimize.setBounds(MainPanel.this.getWidth()-62, 0, 30, 33);
		MPanel.add(LabelMinimize);
		
		//Window Movable
		this.addMouseListener(new MouseAdapter() 
		{
		  @Override
		  public void mousePressed(MouseEvent e) {
		  xframeold = e.getX();
		  yframeold = e.getY();
		  }
		 });
		this.addMouseMotionListener(new MouseMotionAdapter() {
			  public void mouseDragged(MouseEvent e) {
			  int xOnScreen = e.getXOnScreen();
			  int yOnScreen = e.getYOnScreen();
			  int xframenew = xOnScreen - xframeold;
			  int yframenew = yOnScreen - yframeold;
			  MainPanel.this.setLocation(xframenew, yframenew);
			  }
			 });
	}
	
	public MainPanel(boolean visible) throws InterruptedException
	{
		//config
		ConfigFrame();
		MainPanel.this.setVisible(visible);

		//collision 
		List<Car> cars=new ArrayList();		
		
		while(true)
		{
			while(!Iscreatecar)
			{
				Thread.sleep(30);
			}
			Car car=new Car(MPanel,cars,safedistance,MainPanel.this);
			new Thread(car).start();
			Thread.sleep(Math.abs(new Random().nextInt())%2000+1000);
			// wait car leave first dangerous area
			while(car.getPosition()-(safedistance.intValue()+10)+car.getLength()<0)
			{
				Thread.sleep(30);
			}
		}
	}
	
	public void setBridgeHint(int avaliable)
	{
		LabelBridgeHintNumber.setText(String.valueOf(avaliable));
	}
}
