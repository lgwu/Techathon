/**
 * 
 */
package com.scout.lol.utils;

import org.apache.commons.lang.StringUtils;

import com.scout.lol.model.Vendor;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class SourceFileUtil {
	public static String getCoseLogFilePath(Vendor vendor){
		switch(vendor){
			case MORE:
				return "/lol/sources/more/more";
			case COSE:
				return "/lol/sources/cose";
			 default :
				 throw new RuntimeException("Not create the path on HDFS for " +vendor );
		}
		
	}
}
