package resource;

import java.io.File;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import panel.MainPanel;

public class Car implements Runnable {
	private String CarsURL[] = {
			"/UI/Cars/001.png",
			"/UI/Cars/002.png",
			"/UI/Cars/003.png",
			"/UI/Cars/004.png",
			"/UI/Cars/006.png",
			"/UI/Cars/009.png",
			"/UI/Cars/012.png",
			"/UI/Cars/014.png",
			"/UI/Cars/016.png",
			"/UI/Cars/017.png"
			};
	private ImageIcon Icon;
	private JLabel Label;
	private JPanel MPanel;
	private MainPanel MP;
	
	//car property
	private int maxspeed;
	private int speed;
	private int length;
	private int height;
	private int position;
	private Integer safedistance;
	private int state;//0-stop 1-speed up 2-constant speed 3-speed cut
	private boolean ponbridge;

	//mutex
	private static volatile int Maxavaliable=1;
	private static volatile int avaliable=Maxavaliable;
	private List<Car> cars;
	
	public Car(JPanel MPanel,List<Car> cars,Integer safedistance,MainPanel MP)
	{
		//choose a car image
		
		//File F=new File(Car.class.getResource("/UI/Cars").getFile());
		//System.out.println(F);
		//File[] C=F.listFiles();
		Random R=new Random();
		int index=Math.abs((R.nextInt()))%(CarsURL.length);
		Icon=new ImageIcon(Car.class.getResource(CarsURL[index]));
		Label=new JLabel(Icon);
		
		//property configuration
		maxspeed=Math.abs(R.nextInt())%5+1;
		System.out.println(maxspeed);
		speed=1;
		length=Icon.getIconWidth();
		height=Icon.getIconHeight();
		position=-length;
		state=0;
		this.safedistance=safedistance;
		ponbridge=false;
		
		//Panel
		this.MPanel=MPanel;
		this.MP=MP;
		
		//array list
		this.cars=cars;
		cars.add(Car.this);
	}
	
//P operation
	synchronized public void Wait() throws InterruptedException
	{
		System.out.println("wait "+avaliable);
		while(avaliable<=0)
		{
			Thread.sleep(30);
		}
		avaliable--;
		MP.setBridgeHint(Maxavaliable-avaliable);
	}

//V operation
	synchronized public void Signal()
	{
		System.out.println("signal "+avaliable+" car speed="+speed);
		avaliable++;
		if(avaliable>Maxavaliable)
		{
			avaliable=Maxavaliable;
		}
		MP.setBridgeHint(Maxavaliable-avaliable);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//set position
		int positiony=395-height;
		Label.setBounds(-length, positiony, length, height);
		MPanel.add(Label);
		//set state constant speed
		state=2;
		//move
		for(;position<=1500;position+=speed)
		{
			//stop
//			while(state!=2)
//			{
//				//resume detect
//				resumedetect();
//				
//				try {
//					Thread.sleep(30);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			
//			//collision detect
//			int pindex=cars.indexOf(Car.this)-1;//previous car
//			if(pindex>=0)
//			{
//				if(cars.get(pindex).getPosition()-(position+length)<safedistance.intValue())
//				{
//					//collision
//					state=0;
//					continue;
//				}
//			}
			collisiondetect();
			
			//go on bridge
			if(ponbridge==false&&position+length>=600&&position<600)
			{
				try {
					Wait();
					if(state==0)
					{
						continue;
					}
					ponbridge=true;//on bridge state
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//go off bridge
			if(ponbridge==true&&position>=900)
			{
				Signal();
				ponbridge=false;
			}
			
			//move
			Label.setLocation(position, positiony);
			
			speed=(speed+1>maxspeed)?maxspeed:speed+1;
			
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(position>1500)//destroy
		{
			cars.remove(Car.this);
		}
	}
	
	public void collisiondetect()
	{
		int pindex=cars.indexOf(Car.this)-1;//previous car
		if(pindex>=0)
		{
			if(cars.get(pindex).getPosition()-(position+length)<safedistance.intValue())
			{
				//collision
				//state=0;
				
				speed=0;
				
				stop();
			}
		}
	}
	
	public void resumedetect()
	{
		//resume detect
		int pindex=cars.indexOf(Car.this)-1;//previous car
		if(pindex>=0)
		{
			if(cars.get(pindex).getPosition()-(position+length)>safedistance.intValue())
			{
				//resume
				state=2;
			}
		}
		else
		{
			state=2;
		}
	}
	
	public void stop()
	{
		state=0;
		while(state!=2)
		{
			//resume detect
			resumedetect();
			
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

//getters and setters
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getSpeed() {
		return speed;
	}

	public int getLength() {
		return length;
	}

	public int getPosition() {
		return position;
	}

	public int getAvaliable() {
		return avaliable;
	}
	
	public void setSpeed(int speed)
	{
		this.speed=speed;
	}

	synchronized public static void setMaxavaliable(int maxavaliable) {
		avaliable+=maxavaliable-Maxavaliable;
		Maxavaliable = maxavaliable;
	}
	
}
