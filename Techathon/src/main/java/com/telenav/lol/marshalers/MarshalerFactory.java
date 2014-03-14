/**
 * 
 */
package com.telenav.lol.marshalers;

import java.util.HashMap;
import com.telenav.lol.model.Log;
import com.telenav.lol.model.Vendor;


/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class MarshalerFactory {
	private static HashMap<Vendor, ReflectMarshaller<Log>> factories = new HashMap<Vendor, ReflectMarshaller<Log>>();
	static{
		//TODO
//		factories.put(key, value)
		}
	public static ReflectMarshaller<Log> getMarshaller(Vendor vendor){
		return factories.get(vendor);
	}
}
