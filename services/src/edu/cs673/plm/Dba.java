/***************************************************************
Filename: Dba.java
Author: Christian Heckendorf
Created Date: 9/21/13
Purpose: Helper class to get an EntityManager object
Features: Probably all features
***************************************************************/
package edu.cs673.plm;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Dba {

	private static volatile boolean initialized = false;
	private static Boolean lock = new Boolean(true);
	private static EntityManagerFactory emf = null;

	private EntityManager outer;

	/***************************************************************
	Function name: Dba
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Prepares the class
	***************************************************************/
	public Dba() {
		this(false);
	}

	/***************************************************************
	Function name: Dba
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Prepares the class
	***************************************************************/
	public Dba(boolean readOnly) {
		initialize();
		openEm(readOnly);
	}

	/***************************************************************
	Function name: openEm
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Opens an EntityManager
	***************************************************************/
	public void openEm(boolean readOnly) {
		if (outer != null) {
			return;
		}

		outer = emf.createEntityManager();

		if (readOnly == false) {
			outer.getTransaction().begin();
		}
	}

	/***************************************************************
	Function name: getActiveEm
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Returns the active EntityManager
	***************************************************************/
	public EntityManager getActiveEm(){
		if(outer == null){
			throw new IllegalStateException("No transaction was active!");
		}

		return outer;
	}


	/***************************************************************
	Function name: closeEm
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Closes the EntityManager
	***************************************************************/
	public void closeEm(){
		if(outer == null){
			return;
		}

		try{
			if(outer.getTransaction().isActive()){

				if(outer.getTransaction().getRollbackOnly()){
					outer.getTransaction().rollback();
				} else {
					outer.getTransaction().commit();
				}
			}

		} finally {
			outer.close();
			outer = null;
		}
	}


	/***************************************************************
	Function name: markRollback
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Marks the transaction as a rollback
	***************************************************************/
	public void markRollback(){
		if(outer != null){
			outer.getTransaction().setRollbackOnly();
		}
	}

	/***************************************************************
	Function name: isRollbackOnly
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Determines whether the transaction is a rollback
	***************************************************************/
	public boolean isRollbackOnly(){
		return outer != null && outer.getTransaction().getRollbackOnly();
	}


	/***************************************************************
	Function name: initialize
	Author: Christian Heckendorf
	Created Date: 9/21/13
	Purpose: Initializes the EntityManagerFactory
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
				emf = Persistence.createEntityManagerFactory("PLMPU");

			} catch(Throwable t){
				t.printStackTrace();
				return;
			}
		}
	}
}

