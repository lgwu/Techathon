/**
 * 
 */
package com.scout.lol.workflow.main;

import org.apache.commons.lang.StringUtils;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class CommandUtil {
	public static String getVendorId(String[] args) {
        Object value = getValue(args, "-V");
        if (value != null)
            return (String) value;
        throw new RuntimeException("please use -V to specify VendorId, sample:-V5");
    }
    public static String getProjectId(String[] args) {
        Object value = getValue(args, "-P");
        if (value != null)
            return (String) value;
        throw new RuntimeException("please use -P to specify ProjectId, sample:-P1");
    }
      
    public static String[] getCopyTable(String[] args) {
    	Object value = getValue(args, "-C");
    	if(value==null){
    		throw new RuntimeException("please use -C to specify table to copy, sample:-CsourceTable:destTable");
    	}
    	String tableString = (String)value;
    	String[] tables = tableString.split(":");
    	if(tables.length!=2||StringUtils.isEmpty(tables[0])||StringUtils.isEmpty(tables[1])){
			throw new RuntimeException("both sourceTable and destTable should be specified, sample sourceTable:destTable");
		}
    	return tables;
    	
    }
    
    public static String getGeoCodeDataSet(String[] args) {
    	Object value = getValue(args, "-G");
    	if (value != null)
    		return (String) value;
    	throw new RuntimeException("please use -G to specify GeoCodeDatSet, sample:-GTA_US");
    }
    
    public static String getCountry(String[] args){
    	Object value = getValue(args, "-C");
    	if (value != null)
    		return (String) value;
    	throw new RuntimeException("please use -C to specify country, sample:-CUS");
    }
    
    public static String getValue(String[] args, String startWith) {
        for (String value : args) {
            if (value.startsWith(startWith)) {
                return value.substring(startWith.length());
            }
        }
        return null;
    }

    public static String getStepName(String[] args) {
        String value = getValue(args, "-S");
        if (value != null)
            return value;
        throw new RuntimeException("please use -S to specify stepName, sample:-SingestionStep");
    }

  

    public static void main(String[] args) {
        System.out.println(CommandUtil.getVendorId(new String[] { "-V1" }));
    }
}
