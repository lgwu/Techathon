/**
 * 
 */
package com.telenav.lol.workflow.mining;

import org.apache.hadoop.mapreduce.Job;

import com.telenav.lol.workflow.HadoopFlowStep;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class MiningStep extends HadoopFlowStep {

	/* (non-Javadoc)
     * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
     */
    @Override
    public int run(String[] args) throws Exception {
	    // TODO Auto-generated method stub
	    return 0;
    }

	/* (non-Javadoc)
     * @see com.telenav.lol.workflow.HadoopFlowStep#createHadoopJob(java.lang.String[])
     */
    @Override
    public Job createHadoopJob(String[] args) {
	    // TODO Auto-generated method stub
	    return null;
    }

}
