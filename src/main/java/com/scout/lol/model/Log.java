/**
 * 
 */
package com.scout.lol.model;
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
