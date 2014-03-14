/**
 * 
 */
package com.telenav.lol.parser;

import java.util.HashMap;
import java.util.HashSet;

import com.telenav.lol.model.Log;
import com.telenav.lol.model.Vendor;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class ParserFactory {
	private static HashMap<Vendor, AbstractParser<Log>> factories = new HashMap<Vendor, AbstractParser<Log>>();
	static{
		//TODO
//		factories.put(key, value)
	}
	public static AbstractParser<Log> getParser(Vendor vendor){
		return factories.get(vendor);
	}
	
}
