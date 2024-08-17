
/*
 * Copyright (c) 2014 wetransform GmbH
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.esdihumboldt.hale.common.schema.model.constraint.factory.type;

import eu.esdihumboldt.hale.common.schema.model.constraint.factory.AbstractFlagConstraintFactoryTest;
import eu.esdihumboldt.hale.common.schema.model.constraint.type.SkipValidation;
import eu.esdihumboldt.hale.common.schema.model.constraint.type.factory.SkipValidationFactory;

/**
 * Tests for {@link SkipValidationFactory}.
 *
 * @author Simon Templer
 */
public class SkipValidationFactoryTest extends AbstractFlagConstraintFactoryTest<SkipValidation> {

	/**
	 * Default constructor.
	 */
	public SkipValidationFactoryTest() {
		super(SkipValidation.class);
	}

}
