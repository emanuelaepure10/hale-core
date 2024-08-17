
/*
 * Copyright (c) 2016 wetransform GmbH
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.esdihumboldt.hale.io.jdbc.extension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Select Schemas from database
 *
 * @author Arun
 */
public interface SchemaSelector {

	/**
	 * get all schemas of database using database connection.
	 *
	 * @param connection A {@link Connection}
	 * @return List of String of schema name
	 * @throws SQLException A SQL Exception
	 */
	public List<String> getSchemas(Connection connection) throws SQLException;

}
