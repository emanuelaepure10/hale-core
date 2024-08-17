
/*
 * Copyright (c) 2012 wetransform GmbH
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.esdihumboldt.hale.common.align.extension.engine;

import de.fhg.igd.eclipse.util.extension.ExtensionObjectFactory;
import eu.esdihumboldt.hale.common.align.transformation.engine.TransformationEngine;

/**
 * Factory for {@link TransformationEngine}s
 *
 * @author Simon Templer
 */
public interface EngineFactory extends ExtensionObjectFactory<TransformationEngine> {

	/**
	 * Get the engine description
	 *
	 * @return the description
	 */
	public String getDescription();

}
