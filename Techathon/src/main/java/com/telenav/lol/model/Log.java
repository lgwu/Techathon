/**
 * 
 */
package com.telenav.lol.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public abstract class Log {
	private String id = StringUtils.EMPTY;
	private String name =StringUtils.EMPTY;
	private Date date; 

	/**
     * @return the name
     */
    public String getName() {
    	return name;
    }

	/**
     * @param name the name to set
     */
    public void setName(String name) {
    	this.name = name;
    }

	/**
     * @return the date
     */
    public Date getDate() {
	    return date;
    }

	/**
     * @param date the date to set
     */
    public void setDate(Date date) {
	    this.date = date;
    }

	/**
     * @return the id
     */
    public String getId() {
	    return id;
    }

	/**
     * @param id the id to set
     */
    public void setId(String id) {
	    this.id = id;
    }

}
