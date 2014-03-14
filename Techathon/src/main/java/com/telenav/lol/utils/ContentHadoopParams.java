/**
 * 
 */
package com.telenav.lol.utils;

import java.io.IOException;
import java.io.Serializable;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;
import com.telenav.lol.workflow.ingestion.IngestionService;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class ContentHadoopParams {
	private static final Logger logger = Logger.getLogger(ContentHadoopParams.class);

	private static final String INGESTION_SERVICE = "ingest.service";

	public static IngestionService getIngestionService(Configuration conf) throws IOException {
		return readObjectFromConfiguration(conf, INGESTION_SERVICE, IngestionService.class);
	}

	public static void setIngestionService(Configuration conf, IngestionService service) throws IOException {
		logger.debug("Writing ingestion service to hadoop configuration");
		writeObjectToConfiguration(conf, INGESTION_SERVICE, service);
	}

	private static <T extends Serializable> void writeObjectToConfiguration(Configuration conf, String paramName, T obj) throws IOException {
		conf.set(paramName, SerializableUtils.serialize(obj));
	}

	private static <T> T readObjectFromConfiguration(Configuration conf, String paramName, Class<T> cls) throws IOException {
		if (conf.get(paramName) == null) {
			logger.debug("can't get "+paramName +" from configuration");
			return null;
		}
		return SerializableUtils.deserialize(conf.get(paramName), cls);
	}

}
