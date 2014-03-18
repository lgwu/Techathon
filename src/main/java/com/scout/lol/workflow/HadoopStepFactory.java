/**
 * 
 */
package com.scout.lol.workflow;

import java.util.HashMap;

import com.scout.lol.model.Vendor;
import com.scout.lol.workflow.ingestion.IngestionStep;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class HadoopStepFactory {
	private static HashMap<String, HadoopFlowStep> factories = new HashMap<String, HadoopFlowStep>();
	static{	
		factories.put("ingestionStep", new IngestionStep());
		}

	public static HadoopFlowStep getHadoopStep(String stepName ){
		return factories.get(stepName);
	}
}
