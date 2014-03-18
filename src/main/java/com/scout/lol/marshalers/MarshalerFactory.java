/**
 * 
 */
package com.scout.lol.marshalers;

import java.util.HashMap;

import com.scout.lol.model.MoreLog;
import com.scout.lol.model.Vendor;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class MarshalerFactory {
	private static HashMap<Vendor, ReflectMarshaller> factories = new HashMap<Vendor, ReflectMarshaller>();
	static {
		factories.put(Vendor.MORE, new ReflectMarshaller<MoreLog>(MoreLog.class));

	}

	public static ReflectMarshaller<?> getMarshaller(Vendor vendor) {
		return factories.get(vendor);
	}
}
