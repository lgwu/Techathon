/**
 * 
 */
package com.scout.lol.parser;
import java.util.HashMap;

import com.scout.lol.model.Vendor;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class ParserFactory {
    private static HashMap<Vendor, AbstractParser> factories = new HashMap<Vendor, AbstractParser>();
	static{
		//TODO
		factories.put(Vendor.MORE, new MoreLogParser());
	}
	public static AbstractParser getParser(Vendor vendor){
		
		return factories.get(vendor);
	}
	
}
