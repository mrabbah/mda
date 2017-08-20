

package demo

/**
 * Livre Domain Object
 */
class Livre{
    	
    String titre	
       		   
    
	static hasMany = [auteurs : Auteur]
	
	static mapping = { 
auteurs lazy:false
}
	static constraints = {
		
		titre()	
       	
	}
}

