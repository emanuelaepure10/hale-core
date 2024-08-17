
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
package eu.esdihumboldt.hale.io.gml.internal.simpletype.converters;

import java.util.Date;

import org.apache.xmlbeans.XmlTime;
import org.springframework.core.convert.converter.Converter;

/**
 * Convert xs:date to {@link Date}
 */
public class XmlTimeToDate implements Converter<XmlTime, Date> {

	/**
	 * @see Converter#convert(Object)
	 */
	@Override
	public Date convert(XmlTime value) {
		if (value == null) {
			return null;
		}
		return value.getGDateValue().getDate();
	}

}
