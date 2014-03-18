/**
 * 
 */
package com.scout.lol.workflow.main;

import org.apache.hadoop.util.ToolRunner;

import com.scout.lol.workflow.HadoopFlowStep;
import com.scout.lol.workflow.HadoopStepFactory;

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
