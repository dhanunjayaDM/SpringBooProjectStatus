package com.lara;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Salting {
	 public static void main(String[] args)
	 {
		    //String password = "secret";
		    String filename="Address.java";
		    String[] nameParts= filename.split("\\.");
		    System.out.println(Arrays.toString(nameParts));
		    String file2=nameParts[0];
		    String fileextention=nameParts[1];
		    System.out.println(file2); 
		    System.out.println(fileextention);
		    

	        MessageDigest md;
	        try
	        {
	            // Select the message digest for the hash computation -> SHA-256
	            md = MessageDigest.getInstance("SHA-256");

	            // Generate the random salt
	            SecureRandom random = new SecureRandom();
	            byte[] salt = new byte[16];
	            random.nextBytes(salt);
	            
	            System.out.println(salt);

	            // Passing the salt to the digest for the computation
	            md.update(salt);

	            // Generate the salted hash
	            byte[] hashedPassword = md.digest(file2.getBytes(StandardCharsets.UTF_8));

	            StringBuilder sb = new StringBuilder();
	            for (byte b : hashedPassword)
	                sb.append(String.format("%02x", b));

	            System.out.println(sb.append("."+fileextention));
	        } catch (NoSuchAlgorithmException e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	    
	 }

}
