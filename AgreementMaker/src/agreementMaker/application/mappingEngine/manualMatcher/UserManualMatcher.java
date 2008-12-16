package agreementMaker.application.mappingEngine.manualMatcher;

import java.awt.Color;
import java.util.ArrayList;

import agreementMaker.application.Core;
import agreementMaker.application.mappingEngine.AbstractMatcher;
import agreementMaker.application.mappingEngine.Alignment;
import agreementMaker.application.mappingEngine.AlignmentMatrix;
import agreementMaker.application.mappingEngine.MatchersRegistry;
import agreementMaker.application.ontology.Node;
import agreementMaker.application.ontology.Ontology;
import agreementMaker.userInterface.Colors;

/**This class is used to represent the user manual matching
 * this matching will not be part of the matcher list in the combo box
 * so the user won't be able to select this matcher 
 * the matcher is generated by the system on startup with 0 alignments found
 * The user can manually add alignments to this one selecting this matcher from the table and adding the relation on canvas
 * the user can also add manually alignments to other matchers, but if this matcher is not selected matchings will not be added to the user one
 *
 */
public class UserManualMatcher extends AbstractMatcher {
	
	
	public UserManualMatcher() {
		//super(); VERY IMPORTANT NOT CALL SUPER FROM THIS MATCHER BECAUSE THIS MATCHER IS INVOKED BEFORE THE ONTOLOGY LOADING
		
		//maybe this first 3 lines are not needed anymore after the matcherregistry change
		index = 0;
		name = MatchersRegistry.UserManual;
		color = Colors.matchersColors[0];
		
		
		isAutomatic = false;
		needsParam = false;
		isShown = true;
		modifiedByUser = false;
		threshold = 0.01; //the minimum value != 0 in the threshold list;
		maxSourceAlign = ANY_INT;
		maxTargetAlign = ANY_INT;
		alignClass = true;
		alignProp = true;
		minInputMatchers = 0;
		maxInputMatchers = 0;
		
	}
	
	/**Set all alignment sim to 0*/
	public Alignment alignTwoNodes(Node source, Node target) {
		double sim = 0;
		String rel = Alignment.EQUIVALENCE;
		return new Alignment(source, target, sim, rel);
	}
	
    protected void afterSelectionOperations() {
    	super.afterAlignOperations();
    }
    
    //Time calculation, if you override this method remember to call super.afterSelectionOperations()
	protected void matchEnd() {
    	end = System.nanoTime();
    	executionTime = 0; //force it to 0
	}
	
	
	/**This method is only needed for usermatching because is the only one who gets initialized before the creation of ontologies*/
	public void setSourceOntology(Ontology o) {
		sourceOntology = o;
	}
	
	/**This method is only needed for usermatching because is the only one who gets initialized before the creation of ontologies*/
	public void setTargetOntology(Ontology o) {
		targetOntology = o;
	}

	/**These 3 methods are invoked any time the user select a matcher in the matcherscombobox. Usually developers don't have to override these methods unless their default values are different from these.*/
	public double getDefaultThreshold() {
		return 0.01;
	}
	
	/**These 3 methods are invoked any time the user select a matcher in the matcherscombobox. Usually developers don't have to override these methods unless their default values are different from these.*/
	public int getDefaultMaxSourceRelations() {
		return ANY_INT;
	}

	/**These 3 methods are invoked any time the user select a matcher in the matcherscombobox. Usually developers don't have to override these methods unless their default values are different from these.*/
	public int getDefaultMaxTargetRelations() {
		return ANY_INT;
	}

	public String getMatchReport() {
		String result =  "An empty matching has been created!\n";
		result += "Select the matching in the table before adding manual mappings to it.\n";
		return result;
	}
}

