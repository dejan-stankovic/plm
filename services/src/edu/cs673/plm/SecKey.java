/***************************************************************
Filename: SecKey.java
Author: Christian Heckendorf
Created Date: 9/28/13
Purpose: Generate a key for symmetric encryption
Features: All session protected features
***************************************************************/
package edu.cs673.plm;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SecKey {
	private static volatile boolean initialized = false;
	private static Boolean lock = new Boolean(true);
	private static KeyGenerator kg = null;
	private static SecretKey outer = null;

	/***************************************************************
	Function name: SecKey
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: Initializes the object
	***************************************************************/
	public SecKey() {
		initialize();
	}

	/***************************************************************
	Function name: getKey
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: Produces the generated key
	***************************************************************/
	public SecretKey getKey(){
		if(outer == null){
			throw new IllegalStateException("No active key!");
		}

		return outer;
	}

	/***************************************************************
	Function name: initialize
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: Creates the key in a thread safe way
	***************************************************************/
	private void initialize(){
		if(initialized){
			return;
		}

		synchronized(lock){
			if(initialized){
				return;
			}

			initialized = true;

			try{
				kg = KeyGenerator.getInstance("AES");
				kg.init(128);
				outer = kg.generateKey();
			} catch (Exception e){
				System.out.println(e.toString());
			}
		}
	}
}

