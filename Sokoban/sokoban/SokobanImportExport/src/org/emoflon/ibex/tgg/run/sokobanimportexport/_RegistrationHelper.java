package org.emoflon.ibex.tgg.run.sokobanimportexport;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.handbook.sokobanExchangeFormat.SokobanExchangeFormatPackage;
import org.emoflon.ibex.handbook.sokobanExchangeFormat.impl.SokobanExchangeFormatPackageImpl;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.UserDefinedRuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import SokobanLanguage.impl.SokobanLanguagePackageImpl;

public class _RegistrationHelper {

	/** Load and register source and target metamodels */
	public static void registerMetamodels(ResourceSet rs, OperationalStrategy strategy)  throws IOException {
		SokobanLanguagePackageImpl.init();		
		SokobanExchangeFormatPackageImpl.init();
		rs.getPackageRegistry().put("platform:/resource/sokobanExchangeFormat/model/generated/SokobanExchangeFormat.ecore", SokobanExchangeFormatPackage.eINSTANCE);
	}
	
	/** Create default options **/
	public static IbexOptions createIbexOptions() {
		IbexOptions options = new IbexOptions();
		options.projectName("SokobanImportExport");
		options.projectPath("SokobanImportExport");
		options.debug(true);
		options.userDefinedConstraints(new UserDefinedRuntimeTGGAttrConstraintFactory());
		
		File root = new File(_RegistrationHelper.class.getResource(".").getFile());
		options.workspacePath(root.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile()
				.getParentFile().getParentFile().getParent() + File.separatorChar);
		
		return options;
	}
}
