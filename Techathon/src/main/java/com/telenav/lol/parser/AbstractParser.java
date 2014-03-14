/**
 * 
 */
package com.telenav.lol.parser;
import java.io.Serializable;
import org.apache.log4j.Logger;
import com.telenav.lol.model.Log;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public abstract class AbstractParser<T extends Log> implements Serializable {
    private static final long serialVersionUID = "$Id$".hashCode();
	private static final Logger logger = Logger.getLogger(AbstractParser.class);
	public abstract T parse(String line);

}
