/**
 * 
 */
package com.scout.lol.workflow.ingestion;

import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.ToolRunner;

import com.scout.lol.marshalers.MarshalerFactory;
import com.scout.lol.model.Vendor;
import com.scout.lol.parser.ParserFactory;
import com.scout.lol.utils.ContentHadoopParams;
import com.scout.lol.utils.HbaseColumnFamillies;
import com.scout.lol.utils.SourceFileUtil;
import com.scout.lol.utils.TableNameUtil;
import com.scout.lol.workflow.HadoopFlowStep;


/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class IngestionStep extends HadoopFlowStep {
private String hbaseTableName;
private Vendor vendor;

	@Override
	public void init() {
		super.init();
		super.setConf(new Configuration());

		//hbaseTableName = TableNameUtil.getCoseLogTableName();
	}

	@Override
	public int run(String[] args) throws Exception {
		if (args.length < 1) {
			throw new RuntimeException("please input vendor name: more or cose");
		}
		init();
		String vendorName = StringUtils.trimToEmpty(args[1]);
		vendor = Vendor.asValue(vendorName);
		hbaseTableName = TableNameUtil.getCoseLogTableName(vendor);
		if (!hbaseAdmin.tableExists(hbaseTableName)) {
			String[] cfs = new String[] { HbaseColumnFamillies.LOG_INFORMATION };
			hbaseAdmin.createTable(hbaseTableName, cfs);
		}
		job = createHadoopJob(args);

		job.waitForCompletion(true);

		return 0;
	}

	@Override
	public Job createHadoopJob(String[] args) throws IOException {
		
		IngestionService ingestionService = new IngestionService(ParserFactory.getParser(vendor), MarshalerFactory.getMarshaller(vendor));
		ContentHadoopParams.setIngestionService(getConf(), ingestionService);

		Configuration conf = getConf();
		logger.info(">>>>>>>>>>>>> JobTracker: " + conf.get("mapred.job.tracker") + " while " + conf.get("mapred.job.tracker"));
		Job job = new Job(getConf(), generateJobName(IngestionStep.class));

		job.setJarByClass(IngestionStep.class);
		job.setMapperClass(IngestionMapper.class);
		job.setMapOutputKeyClass(ImmutableBytesWritable.class);
		job.setMapOutputValueClass(Put.class);
		job.setNumReduceTasks(0);
		
		
		String sourcePath = SourceFileUtil.getCoseLogFilePath(vendor);
		logger.info("the "+vendor +" source path :  "+sourcePath);
		FileInputFormat.setInputPaths(job, sourcePath);
		job.setInputFormatClass(TextInputFormat.class);
		TableMapReduceUtil.initTableReducerJob(hbaseTableName, null, job);

		return job;
	}
	
	public static void main(String[] args) throws Exception {
	
	
		HadoopFlowStep hadoopFlowStep = new IngestionStep();
		ToolRunner.run(hadoopFlowStep, args);
		return;

	}
	

}
