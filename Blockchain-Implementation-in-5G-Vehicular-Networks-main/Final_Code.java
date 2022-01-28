// Blockchain Implementation in Vehicular Networks

// Imported date class so as to get timeStamp
import java.util.Date;

// Imported to make use of random numbers
import java.util.*;

// This is to use SHA-256 algorithm
import java.security.MessageDigest;

// Making our Block 
class Block 
{

    // This is the data of our block
	private String data;
	// This is hash of previous block
	public String previousHash;
    // This is hash of current block
	public String hash;
	// This indicates when a block last updated
	private long timeStamp;
	
	private double trueness;

	// Initializing all our members of Block
	public Block(String data, String previousHash, double trueness) 
	{
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.trueness=trueness;
		// Hash of current block is calculated as the aggregate of all the members present in that block
		this.hash = current_block_hash_calculate();
	}
	
	public double getTruthValue()
    {
        return trueness;
    }
    
    public void setTruthValue(double val)
    {
        trueness=(trueness+val)/2;
    }
	
	// Calculatuing hash of current block
	public String current_block_hash_calculate() 
	{
	   // Hash of current block is calculated as the aggregate of all the members present in that block
	   String current_hash = Sha_256(previousHash + Long.toString(timeStamp) + data);
	   return current_hash;
    }

	//Applying Sha256 algorithm to input string and getting the hash of that block
	public static String Sha_256(String input)
	{	
	     // Wraping it inside try-catch as it is comes under checked exception
	     try
	     {
	        // Taking the Instance of SHA-256
			MessageDigest md = MessageDigest.getInstance("SHA-256");	        
			
	 	    // Get our input in bytes
			byte[] hash_in_bytes = md.digest(input.getBytes("UTF-8"));
			
			// Representing our hash in hexidecimal
			StringBuilder hash_in_hex = new StringBuilder(); 
		
			for (int i = 0; i < hash_in_bytes.length; i++) 
			{
			    // This make sure our input is in Hex form only
				String hex = Integer.toHexString(0xff & hash_in_bytes[i]);
				hash_in_hex.append(hex);
			}
			
			return hash_in_hex.toString();
	     }
	     
	     // catch block
	     catch(Exception e)
	     {
	         throw new RuntimeException(e);
	     }
	}	
}

// Making our class
class Car
{
    private int Xcoordinate;
    private int Ycoordinate;
    
    Car(int x, int y)
    {
        Xcoordinate=x;
        Ycoordinate=y;
    }
    
    public int getXcoordinate()
    {
        return Xcoordinate;
    }
    
    public int getYcoordinate()
    {
        return Ycoordinate;
    }
    
    public void setXcoordinate(int x)
    {
        Xcoordinate=x;
    }
    
    public void setYcoordinate(int y)
    {
        Ycoordinate=y;
    }
    
}

class RSU
{
    private int Xcoordinate;
    private int Ycoordinate;
    private String message;
    
    RSU(int x, int y, String msg)
    {
        Xcoordinate=x;
        Ycoordinate=y;
        message=msg;
    }
    
    public int getXcoordinate()
    {
        return Xcoordinate;
    }
    
    public int getYcoordinate()
    {
        return Ycoordinate;
    }
    
}

// Driver class	
public class Main {

    // Driver method
	public static void main(String[] args) {

        Random rdm = new Random();
		
		double val=Math.random();
		
		int xcord=rdm.nextInt(101)+200;
	    int ycord=rdm.nextInt(101)+200;
	    
	    double tv1=Math.random();
	    double tv2=Math.random();
	    double tv3=Math.random();
	    
	    String message;

        // Making our 1st block		
        // Since it is our first block so its previous hash should be 0
		Block GenesisBlock = new Block("This is our Genesis Block", "0",tv1);
		//System.out.println("Genesis Block Hash is : " + GenesisBlock.hash);
		//System.out.println("Hash of block previous to Genesis Block is : " +null);
        
        //System.out.println();
        
        // Making our 2nd block
        // Since it is our 2nd block so its previous hash should be hash of genesis block (Block 1)
		Block Block_2 = new Block("This is the 2nd Block",GenesisBlock.hash,tv2);
		//System.out.println("Block 2 Hash is : " + Block_2.hash);
		//System.out.println("Hash of block previous to Block 2 (i,e Block 1) is : " +GenesisBlock.hash);
		
		//System.out.println();
		
		// Making our 3rd block
        // Since it is our 3rd block so its previous hash should be hash of Block 2
		Block Block_3 = new Block("This is the 3rd Block",Block_2.hash,tv3);
		//System.out.println("Block 3 Hash is : " + Block_3.hash);
		//System.out.println("Hash of block previous to Block 3 (i,e Block 2) is : " +Block_2.hash);
	
		
	    Car c1=new Car(xcord,ycord);
	    Car c2=new Car(xcord-50,ycord);
	    Car c3=new Car(xcord-110,ycord);
	    
	    System.out.println();
	    System.out.println("-----------------------------Initial Coordinates of Cars-------------------------");
	    System.out.println();
	    
	    System.out.println("Initial Coordinates of Car 1 are ("+c1.getXcoordinate()+","+c2.getYcoordinate()+")");
	    System.out.println("Initial Coordinates of Car 2 are ("+c2.getXcoordinate()+","+c2.getYcoordinate()+")");
	    System.out.println("Initial Coordinates of Car 3 are ("+c3.getXcoordinate()+","+c3.getYcoordinate()+")");
		
		System.out.println();
		System.out.println("-----------------------------HAPPENING OF EVENT----------------------------------");
		System.out.println();
		System.out.print("EVENT HAPPENED IS: ");
		
	    if(GenesisBlock.getTruthValue()>=0.5 && val>=0.5)
	    {
	      message="Accident happened at coordinate ("+xcord+","+ycord+") of Car 1";
	      System.out.println(message);
	      GenesisBlock.setTruthValue(1);
	    }
	    
	    else if(GenesisBlock.getTruthValue()<0.5 && val>=0.5)
	    {
	        message="False Accident message send by Car 1";
	        System.out.println(message);
	        GenesisBlock.setTruthValue(-1);
	        return;
	    }
	    
	    else if(GenesisBlock.getTruthValue()>=0.5 && val<0.5)
	    {
	        message="Traffic Jam happened at coordinate ("+xcord+","+ycord+") of Car 1 ";
	        System.out.println(message);
	        GenesisBlock.setTruthValue(1);
	    }
	    
	    else
	    {
	        message="False Traffic Jam message send by Car 1";
	        System.out.println(message);
	        GenesisBlock.setTruthValue(-1); 
	        return;
	    }
	    
	    System.out.println();
	    
	    RSU rsu=new RSU(xcord,ycord+50, message);
	    
	    Car[]arrayofcar=new Car[4];
	    
	    arrayofcar[1]=c1;
	    arrayofcar[2]=c2;
	    arrayofcar[3]=c3;
	    
	    boolean visited[]=new boolean[4];
	    visited[1]=true;
	    
	    System.out.println();
	    System.out.println("-----------------------------HAPPENING OF V2V Communication----------------------");
	    System.out.println();
	    
	    V2Vcommunication(arrayofcar, visited, 1);
	    
	    System.out.println();
	    System.out.println("-----------------------------HAPPENING OF V2R Communication----------------------");
	    System.out.println();
	    
	    System.out.println("Coordinates of RSU are ("+rsu.getXcoordinate()+","+rsu.getYcoordinate()+")");
	    V2Rcommunication(arrayofcar,visited, rsu);
	    
	    System.out.println();
	    System.out.println("-----------------------------Final Coordinates of Cars---------------------------");
	    System.out.println();
	    
	    System.out.println("Final Coordinates of Car 1 are ("+c1.getXcoordinate()+","+c1.getYcoordinate()+") ###### UNCHANGED");
	    System.out.println("Final Coordinates of Car 2 are ("+c2.getXcoordinate()+","+c2.getYcoordinate()+") ###### CHANGED");
	    System.out.println("Final Coordinates of Car 3 are ("+c3.getXcoordinate()+","+c3.getYcoordinate()+") ###### CHANGED");
	    
	}
	
	public static void V2Vcommunication(Car[]arrayofcar, boolean[]visited, int carnumber)
	{
	    
	    if(carnumber==3)
	    return;
	    
	    int q=carnumber;
	    for(;q<arrayofcar.length-1;q++)
	    {
	        int val1=Math.abs(arrayofcar[q].getXcoordinate()-arrayofcar[q+1].getXcoordinate());
	        int val2=Math.abs(arrayofcar[q].getYcoordinate()-arrayofcar[q+1].getYcoordinate());
	        
	        double reach=Math.sqrt((val1*val1)+(val2*val2));
	        
	    if(visited[q+1]==false)
	       {
	        
	        if(reach<=50)
	        {
	          visited[q+1]=true;
	          System.out.println("Incident information passed from Car "+q+" to Car "+(q+1));
	          
	          int xcord=arrayofcar[q+1].getXcoordinate();
	          int ycord=arrayofcar[q+1].getYcoordinate();
	          
	          V2Vcommunication(arrayofcar, visited, carnumber+1);
	          
	          System.out.println("Car "+(q+1)+" moves to Right from coordinate ("+xcord+","+ycord+") to coordinate ("+(xcord+25)+","+(ycord-25)+")");
	          arrayofcar[q+1].setXcoordinate(xcord+25);
	          arrayofcar[q+1].setYcoordinate(ycord-25);
	          
	          break;
	        }
	        
	        else
	        {
	           System.out.println("Incident information cannot be passed from Car "+q+ " to Car "+(q+1)+" because Car "+(q+1)+" is not in a range of Car "+q);
	           System.out.println("No other Car is in reach of Car "+q);
	           return;
	        }
	       
	           
	       }
	    }
	    
	    System.out.println("No other Car is in reach of Car "+(q+2));
	    
	}
	
	public static void V2Rcommunication(Car[]arrayofcar, boolean[]visited, RSU rsu)
	{
	    System.out.println("Incident information passed from Car 1 to RSU ");
	    
	    for(int q=2;q<arrayofcar.length;q++)
	    {
	        int val1=Math.abs(rsu.getXcoordinate()-arrayofcar[q].getXcoordinate());
	        int val2=Math.abs(rsu.getYcoordinate()-arrayofcar[q].getYcoordinate());
	        
	        double reach=Math.sqrt((val1*val1)+(val2*val2));
	        
	        if(reach<=150)
	        {
	          if(visited[q]==true)
	              System.out.println("Car "+q+" is in reach of RSU but it already gets V2V communication from Car 1");
	          
	          else
	          {
	              System.out.println("Incident information passed from RSU to Car "+q);
	              int xcord=arrayofcar[q].getXcoordinate();
	              int ycord=arrayofcar[q].getYcoordinate();
	          
	          System.out.println("Car "+q+" moves to Left from coordinate ("+xcord+","+ycord+") to coordinate ("+(xcord+25)+","+(ycord+25)+")");
	          arrayofcar[q].setXcoordinate(xcord+25);
	          arrayofcar[q].setYcoordinate(ycord+25);
	          
	          }
	          
	        }
	        
	        else
	        System.out.println("Incident information cannot be passed from RSU to Car "+q+" because Car "+q+" is not in a range of RSU");

    	}
    	
    	System.out.println("No other Car is in reach of RSU");
	}

}
