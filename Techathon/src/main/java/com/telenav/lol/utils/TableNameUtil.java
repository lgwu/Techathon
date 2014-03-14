/**
 * 
 */
package com.telenav.lol.utils;

import com.telenav.lol.model.Vendor;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class TableNameUtil {
	public static String getCoseLogTableName(Vendor vendor) {
		switch(vendor){
			case MORE:
				return "MORE_LOG";
			case COSE:
				return "COSE_LOG";
			 default :
				 throw new RuntimeException("Not create the Htable on Hbase for " +vendor );
		}
	}

}
