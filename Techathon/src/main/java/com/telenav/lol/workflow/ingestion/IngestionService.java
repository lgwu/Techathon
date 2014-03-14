/**
 * 
 */
package com.telenav.lol.workflow.ingestion;

import java.io.IOException;
import java.io.Serializable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import com.google.common.base.Function;
import com.telenav.lol.marshalers.ReflectMarshaller;
import com.telenav.lol.model.Log;
import com.telenav.lol.parser.AbstractParser;
import com.telenav.lol.utils.HbaseColumnFamillies;
import com.telenav.lol.utils.HbaseColumnNames;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public  class IngestionService implements Serializable, Function<String, Put> {

	private static final long serialVersionUID = "$Id$".hashCode();
	private AbstractParser<Log> parser;
	private ReflectMarshaller<Log> marshaller;

    public IngestionService(AbstractParser<Log> parser, ReflectMarshaller<Log> marshaller) {
	    super();
	    this.parser = parser;
	    this.marshaller = marshaller;
    }
    

	@Override
	public Put apply(String input) {
		Log log = parser.parse(input);
		return encodeObject(log);
	}

	//need to be override
	public Put encodeObject(Log log) {
		Put put = new Put(Bytes.toBytes(log.getId()));
		try {
			byte[] values = marshaller.marshal(log);
			put.add(Bytes.toBytes(HbaseColumnFamillies.LOG_INFORMATION), Bytes.toBytes(HbaseColumnNames.BASIC_LOG_INFORMATION), values);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return put;
	}

	/**
	 * @return the parser
	 */
	public AbstractParser<Log> getParser() {
		return parser;
	}

	/**
	 * @param parser the parser to set
	 */
	public void setParser(AbstractParser<Log> parser) {
		this.parser = parser;
	}

	/**
     * @param configuration
     */
    public void setup(Configuration configuration) {
	    // TODO Auto-generated method stub
	    
    }

}
