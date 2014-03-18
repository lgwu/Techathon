/**
 * 
 */
package com.scout.lol.workflow.mining;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.log4j.Logger;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class MiningReducer extends TableReducer<ImmutableBytesWritable, Result, ImmutableBytesWritable> {
	Logger logger = Logger.getLogger(MiningReducer.class);

}
