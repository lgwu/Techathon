/**
 * 
 */
package com.telenav.lol.workflow;

import java.util.HashMap;
import com.telenav.lol.model.Vendor;
import com.telenav.lol.workflow.ingestion.IngestionStep;

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
