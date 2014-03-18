/**
 * 
 */
package com.scout.lol.workflow.ingestion;

import java.io.IOException;
import java.io.Serializable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.scout.lol.marshalers.ReflectMarshaller;
import com.scout.lol.model.Log;
import com.scout.lol.model.Vendor;
import com.scout.lol.parser.AbstractParser;
import com.scout.lol.utils.HbaseColumnFamillies;
import com.scout.lol.utils.HbaseColumnNames;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public  class IngestionService implements Serializable, Function<String, Put> {
	public static Logger logger = Logger.getLogger(IngestionService.class);
	private static final long serialVersionUID = "$Id$".hashCode();
	private AbstractParser parser;
	private ReflectMarshaller marshaller;

    public IngestionService(AbstractParser parser, ReflectMarshaller marshaller) {
	    super();
	   Preconditions.checkNotNull(parser,"The parser is null");
	   Preconditions.checkNotNull(marshaller,"The ReflectMarshaller is null");
	    this.parser = parser;
	    this.marshaller = marshaller;
    }

	@Override
	public Put apply(String input) {
		logger.info(" input : " + input);
		Log log = parser.parse(input);
		if(null != log){
		return encodeObject(log);
		}
		return null;
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
