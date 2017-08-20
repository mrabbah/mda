

package com.acme.domain

/**
 * User Domain Object
 */
class User {
    	
    String username	
    	
    String password	
       		   
    
    static hasMany = [addresss : Address]
    
    static mapping = { 
}
    static constraints = {
    
        username(contraintes)
    
        password()
    
    }
}

