/**
 * 
 */
package com.telenav.lol.model;

import java.util.Date;
import java.util.List;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class CoseLog extends Log{
	private String logId;
	
	private List<String> value;

	public CoseLog(String logId, Date date, List<String> value) {
		this.logId = logId;
		super.setDate( date);
		this.value = value;
	}

	public CoseLog() {
		super();
	}

	/**
	 * @return the logId
	 */
	public String getLogId() {
		return logId;
	}

	/**
	 * @param logId the logId to set
	 */
	public void setLogId(String logId) {
		this.logId = logId;
	}

	/**
	 * @return the value
	 */
	public List<String> getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(List<String> value) {
		this.value = value;
	}
	/**
	 * @param logId
	 * @param date
	 * @param value
	 */

}
