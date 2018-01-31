/*
 * Copyright (c) 2017 interactive instruments GmbH
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     interactive instruments GmbH <http://www.interactive-instruments.de>
 */

package eu.esdihumboldt.hale.io.xtraserver.writer.handler;

import java.util.Collection;

import com.google.common.collect.ListMultimap;

import de.fhg.igd.slf4jplus.ALogger;
import de.fhg.igd.slf4jplus.ALoggerFactory;
import de.interactive_instruments.xtraserver.config.util.api.FeatureTypeMapping;
import de.interactive_instruments.xtraserver.config.util.api.MappingTable;
import eu.esdihumboldt.hale.common.align.model.Cell;
import eu.esdihumboldt.hale.common.align.model.Entity;
import eu.esdihumboldt.hale.common.align.model.EntityDefinition;
import eu.esdihumboldt.hale.common.schema.model.TypeDefinition;
import eu.esdihumboldt.hale.common.schema.model.constraint.type.PrimaryKey;
import eu.esdihumboldt.hale.io.jdbc.constraints.DatabaseTable;
import eu.esdihumboldt.hale.io.xsd.constraint.XmlElements;
import eu.esdihumboldt.hale.io.xtraserver.compatibility.XtraServerCompatibilityMode;

/**
 * Abstract Type Transformation Handler
 * 
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
abstract class AbstractTypeTransformationHandler implements TypeTransformationHandler {

	protected final MappingContext mappingContext;
	protected final static ALogger logger = ALoggerFactory
			.getLogger(TypeTransformationHandler.class);

	protected AbstractTypeTransformationHandler(final MappingContext mappingContext) {
		this.mappingContext = mappingContext;
	}

	protected FeatureTypeMapping createFeatureTypeMapping(final Cell cell) {
		final ListMultimap<String, ? extends Entity> targetEntities = cell.getTarget();
		if (targetEntities == null || targetEntities.size() == 0) {
			throw new IllegalStateException("No target type has been specified.");
		}
		final Entity targetType = targetEntities.values().iterator().next();
		final TypeDefinition targetTypeDefinition = targetType.getDefinition().getType();
		final XmlElements constraints = targetTypeDefinition.getConstraint(XmlElements.class);
		if (constraints == null || constraints.getElements().size() == 0) {
			throw new IllegalStateException("No constraint has been specified.");
		}
		else if (constraints.getElements().size() > 1) {
			throw new IllegalStateException("More than one constraint has been specified.");
		}
		final String name = mappingContext.getNamespaces()
				.getPrefixedName(constraints.getElements().iterator().next().getName());

		return FeatureTypeMapping.create(name, targetTypeDefinition.getName(),
				mappingContext.getNamespaces());
	}

	protected String getPrimaryKey(final TypeDefinition definition) {
		final PrimaryKey primaryKey = definition.getConstraint(PrimaryKey.class);
		if (primaryKey == null || primaryKey.getPrimaryKeyPath() == null
				|| primaryKey.getPrimaryKeyPath().isEmpty()) {
			return null;
		}
		return primaryKey.getPrimaryKeyPath().iterator().next().getLocalPart();
	}

	protected MappingTable createTableIfAbsent(final FeatureTypeMapping featureTypeMapping,
			final EntityDefinition sourceType) {
		return createTableIfAbsent(featureTypeMapping, sourceType, false);
	}

	protected MappingTable createTableIfAbsent(final FeatureTypeMapping featureTypeMapping,
			final EntityDefinition sourceType, boolean isJoined) {
		final TypeDefinition sourceTypeDefinition = sourceType.getType();
		final String tableName = sourceTypeDefinition.getDisplayName();

		return this.mappingContext.getTable(tableName).orElseGet(() -> {
			MappingTable table = MappingTable.create();
			final DatabaseTable dbTable = sourceTypeDefinition.getConstraint(DatabaseTable.class);
			if (dbTable != null && dbTable.getTableName() != null) {
				table.setName(dbTable.getTableName());
			}
			else {
				table.setName(tableName);
			}

			final String primaryKey = getPrimaryKey(sourceTypeDefinition);
			if (primaryKey != null) {
				table.setOidCol(primaryKey);
			}
			else {
				table.setOidCol("id");
				logger.warn(
						"No primary key for table '{}' found, assuming 'id'. (context: oid_col in FeatureType {})",
						table.getName(), featureTypeMapping.getName());
			}
			table.setJoined(isJoined);

			featureTypeMapping.addTable(table);
			return table;
		});
	}

	@Override
	public final FeatureTypeMapping handle(final Cell cell) {
		final FeatureTypeMapping mapping = mappingContext
				.addNextFeatureTypeMapping(createFeatureTypeMapping(cell));

		final ListMultimap<String, ? extends Entity> sourceEntities = cell.getSource();
		if (sourceEntities == null || sourceEntities.size() == 0) {
			throw new IllegalStateException("No source type has been specified.");
		}
		if (XtraServerCompatibilityMode.hasFilters(cell.getSource())) {
			logger.warn(
					"Filters are not supported and are ignored during type transformation of Feature Type {}",
					mappingContext.getFeatureTypeName());
		}

		final ListMultimap<String, ? extends Entity> targetEntities = cell.getTarget();
		if (targetEntities == null || targetEntities.size() == 0) {
			throw new IllegalStateException("No target type has been specified.");
		}
		final Entity targetType = targetEntities.values().iterator().next();
		final Collection<Entity> sourceTypes = (Collection<Entity>) sourceEntities.values();
		doHandle(sourceTypes, targetType, mapping, cell);
		return mapping;
	}

	public abstract void doHandle(final Collection<Entity> sourceTypes, final Entity targetType,
			final FeatureTypeMapping mapping, final Cell typeCell);
}
