/**
 * 
 */
package com.telenav.lol.workflow.ingestion;

import java.io.IOException;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;
import com.google.common.base.Preconditions;
import com.telenav.lol.utils.ContentHadoopParams;



/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class IngestionMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put> {
	private static final Logger logger = Logger.getLogger(IngestionMapper.class);
	private IngestionService ingestionService =null;
	@Override
	protected void setup(final Context context) throws IOException, InterruptedException {
		super.setup(context);
		ingestionService = ContentHadoopParams.getIngestionService(context.getConfiguration());
		Preconditions.checkNotNull(ingestionService, "ingestion service is null");
		ingestionService.setup(context.getConfiguration());		
		
	}

	@Override
	protected void map(LongWritable key, Text text, Context context) throws IOException, InterruptedException {
		String segment = text.toString();
		if (segment.equals(""))
			throw new InterruptedException("Error happened since original text is blank");

		Put put = ingestionService.apply(segment);//transformService.parse(segment);
		if (put == null) {
			context.getCounter("LOG", "InvaildInput").increment(1);
			return;
		}
		context.getCounter("LOG", "VaildInput").increment(1);
		context.write(new ImmutableBytesWritable(put.getRow()), put);
	}
}
