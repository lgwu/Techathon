/**
 * 
 */
package com.telenav.lol.workflow.mining;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.log4j.Logger;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class MiningMapper  extends TableMapper<ImmutableBytesWritable, Result> {
	private static final Logger logger = Logger.getLogger(MiningMapper.class);

}
