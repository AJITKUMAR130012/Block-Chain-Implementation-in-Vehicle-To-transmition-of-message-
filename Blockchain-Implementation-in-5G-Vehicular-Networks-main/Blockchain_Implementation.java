// Basic Implementation of Blockchain in Java

// Imported date class so as to get timeStamp
import java.util.Date;
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

	// Initializing all our members of Block
	public Block(String data, String previousHash) 
	{
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		// Hash of current block is calculated as the aggregate of all the members present in that block
		this.hash = current_block_hash_calculate();
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

// Driver class	
public class Main {

    // Driver method
	public static void main(String[] args) {

        // Making our 1st block		
        // Since it is our first block so its previous hash should be 0
		Block GenesisBlock = new Block("This is our Genesis Block", "0");
		System.out.println("Genesis Block Hash is : " + GenesisBlock.hash);
		System.out.println("Hash of block previous to Genesis Block is : " +null);
        
        System.out.println();
        
        // Making our 2nd block
        // Since it is our 2nd block so its previous hash should be hash of genesis block (Block 1)
		Block Block_2 = new Block("This is the 2nd Block",GenesisBlock.hash);
		System.out.println("Block 2 Hash is : " + Block_2.hash);
		System.out.println("Hash of block previous to Block 2 (i,e Block 1) is : " +GenesisBlock.hash);
		
		System.out.println();
		
		// Making our 3rd block
        // Since it is our 3rd block so its previous hash should be hash of Block 2
		Block Block_3 = new Block("This is the 3rd Block",Block_2.hash);
		System.out.println("Block 3 Hash is : " + Block_3.hash);
		System.out.println("Hash of block previous to Block 3 (i,e Block 2) is : " +Block_2.hash);
		
	}

	
}
