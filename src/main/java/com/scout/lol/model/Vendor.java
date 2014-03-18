/**
 * 
 */
package com.scout.lol.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public enum Vendor {
	
	    MORE("more"),
	    COSE("cose");
	    
	    private String value;
	    private Vendor(String value){
	        this.value = value;
	    }
	    
	    private final static Map<String, Vendor> map = new HashMap<String, Vendor>();
	    
	    static{
	        for (Vendor elem : Vendor.values()) {
	            map.put(elem.value, elem);
	        }
	    }
	    
	public static Vendor asValue(String str) {
		return map.get(str);
	}
	


	
}
