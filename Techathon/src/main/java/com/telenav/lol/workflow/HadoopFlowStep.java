package com.telenav.lol.workflow;
import java.io.IOException;

import javax.annotation.PostConstruct;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.log4j.Logger;
import com.telenav.lol.dao.HbaseAdmin;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */


public abstract class HadoopFlowStep  implements Tool {
	public static Logger logger = Logger.getLogger(HadoopFlowStep.class);
	public static final int defaultHbaseCachingNum = 1000;

	public static final String OOZIE_ACTION_OUTPUT_PROPERTIES = "oozie.action.output.properties";

	public static final String[] taskCounterNames = { "MAP_INPUT_RECORDS", "MAP_OUTPUT_RECORDS", "REDUCE_INPUT_RECORDS", "REDUCE_OUTPUT_RECORDS" };

	protected HbaseAdmin hbaseAdmin ;

	protected Job job;
	
	public abstract Job createHadoopJob(String[] args) throws IOException;

	protected Configuration conf;

	private Configuration hbaseConfiguration =HBaseConfiguration.create();
	
	
	public void init() {
		setConf(hbaseConfiguration);
		hbaseAdmin= new HbaseAdmin(getConf());
	}
	
	@Override
	public void setConf(Configuration conf) {
		if (this.getConf() == null) {
			this.conf = conf;
		}
		else {
			HBaseConfiguration.merge(getConf(), conf);
		}
	}
	
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Override
	public Configuration getConf() {
		return conf;
	}



	
	public void getJobReport(Job job){
		setJob(job);	
	}

	public String generateJobName(Class cls) {
		String className = cls.getSimpleName();
		return className;
	}
	
}
