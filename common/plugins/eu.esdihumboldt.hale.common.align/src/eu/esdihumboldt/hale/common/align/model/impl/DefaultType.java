
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
package eu.esdihumboldt.hale.common.align.model.impl;

import eu.esdihumboldt.hale.common.align.model.Entity;
import eu.esdihumboldt.hale.common.align.model.Type;

/**
 * Represents a type in a mapping cell
 *
 * @author Simon Templer
 */
public class DefaultType implements Type {

	private final TypeEntityDefinition definition;

	/**
	 * Create a type entity
	 *
	 * @param definition the type entity definition
	 */
	public DefaultType(TypeEntityDefinition definition) {
		super();
		this.definition = definition;
	}

	/**
	 * @see Entity#getDefinition()
	 */
	@Override
	public TypeEntityDefinition getDefinition() {
		return definition;
	}

	// TODO type filter/restriction stuff - also update hashCode/equals?!

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((definition == null) ? 0 : definition.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultType other = (DefaultType) obj;
		if (definition == null) {
			if (other.definition != null)
				return false;
		}
		else if (!definition.equals(other.definition))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getDefinition().toString();
	}

}
