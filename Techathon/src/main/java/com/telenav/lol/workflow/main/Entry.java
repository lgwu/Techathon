/**
 * 
 */
package com.telenav.lol.workflow.main;

import org.apache.hadoop.util.ToolRunner;
import com.telenav.lol.workflow.HadoopFlowStep;
import com.telenav.lol.workflow.HadoopStepFactory;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class Entry {
	public static void main(String[] args) throws Exception {

		String stepName = CommandUtil.getStepName(args);
		HadoopFlowStep hadoopFlowStep = HadoopStepFactory.getHadoopStep(stepName);
		ToolRunner.run(hadoopFlowStep, args);
		return;
	}

}
