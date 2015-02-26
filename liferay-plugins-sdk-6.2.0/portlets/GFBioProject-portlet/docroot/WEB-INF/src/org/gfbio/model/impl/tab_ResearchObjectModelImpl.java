/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package org.gfbio.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import org.gfbio.model.tab_ResearchObject;
import org.gfbio.model.tab_ResearchObjectModel;
import org.gfbio.model.tab_ResearchObjectSoap;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the tab_ResearchObject service. Represents a row in the &quot;gfbio_tab_ResearchObject&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link org.gfbio.model.tab_ResearchObjectModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link tab_ResearchObjectImpl}.
 * </p>
 *
 * @author Felicitas Loeffler
 * @see tab_ResearchObjectImpl
 * @see org.gfbio.model.tab_ResearchObject
 * @see org.gfbio.model.tab_ResearchObjectModel
 * @generated
 */
@JSON(strict = true)
public class tab_ResearchObjectModelImpl extends BaseModelImpl<tab_ResearchObject>
	implements tab_ResearchObjectModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a tab_ research object model instance should use the {@link org.gfbio.model.tab_ResearchObject} interface instead.
	 */
	public static final String TABLE_NAME = "gfbio_tab_ResearchObject";
	public static final Object[][] TABLE_COLUMNS = {
			{ "ro_ID", Types.BIGINT },
			{ "ro_name", Types.VARCHAR },
			{ "ro_meta", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table gfbio_tab_ResearchObject (ro_ID LONG not null primary key,ro_name VARCHAR(75) null,ro_meta VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table gfbio_tab_ResearchObject";
	public static final String ORDER_BY_JPQL = " ORDER BY tab_ResearchObject.ro_name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY gfbio_tab_ResearchObject.ro_name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.org.gfbio.model.tab_ResearchObject"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.org.gfbio.model.tab_ResearchObject"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.column.bitmask.enabled.org.gfbio.model.tab_ResearchObject"),
			true);
	public static long RO_NAME_COLUMN_BITMASK = 1L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static tab_ResearchObject toModel(tab_ResearchObjectSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		tab_ResearchObject model = new tab_ResearchObjectImpl();

		model.setRo_ID(soapModel.getRo_ID());
		model.setRo_name(soapModel.getRo_name());
		model.setRo_meta(soapModel.getRo_meta());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<tab_ResearchObject> toModels(
		tab_ResearchObjectSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<tab_ResearchObject> models = new ArrayList<tab_ResearchObject>(soapModels.length);

		for (tab_ResearchObjectSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.org.gfbio.model.tab_ResearchObject"));

	public tab_ResearchObjectModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _ro_ID;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setRo_ID(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ro_ID;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return tab_ResearchObject.class;
	}

	@Override
	public String getModelClassName() {
		return tab_ResearchObject.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("ro_ID", getRo_ID());
		attributes.put("ro_name", getRo_name());
		attributes.put("ro_meta", getRo_meta());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long ro_ID = (Long)attributes.get("ro_ID");

		if (ro_ID != null) {
			setRo_ID(ro_ID);
		}

		String ro_name = (String)attributes.get("ro_name");

		if (ro_name != null) {
			setRo_name(ro_name);
		}

		String ro_meta = (String)attributes.get("ro_meta");

		if (ro_meta != null) {
			setRo_meta(ro_meta);
		}
	}

	@JSON
	@Override
	public long getRo_ID() {
		return _ro_ID;
	}

	@Override
	public void setRo_ID(long ro_ID) {
		_ro_ID = ro_ID;
	}

	@JSON
	@Override
	public String getRo_name() {
		if (_ro_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _ro_name;
		}
	}

	@Override
	public void setRo_name(String ro_name) {
		_columnBitmask = -1L;

		if (_originalRo_name == null) {
			_originalRo_name = _ro_name;
		}

		_ro_name = ro_name;
	}

	public String getOriginalRo_name() {
		return GetterUtil.getString(_originalRo_name);
	}

	@JSON
	@Override
	public String getRo_meta() {
		if (_ro_meta == null) {
			return StringPool.BLANK;
		}
		else {
			return _ro_meta;
		}
	}

	@Override
	public void setRo_meta(String ro_meta) {
		_ro_meta = ro_meta;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
			tab_ResearchObject.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public tab_ResearchObject toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (tab_ResearchObject)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		tab_ResearchObjectImpl tab_ResearchObjectImpl = new tab_ResearchObjectImpl();

		tab_ResearchObjectImpl.setRo_ID(getRo_ID());
		tab_ResearchObjectImpl.setRo_name(getRo_name());
		tab_ResearchObjectImpl.setRo_meta(getRo_meta());

		tab_ResearchObjectImpl.resetOriginalValues();

		return tab_ResearchObjectImpl;
	}

	@Override
	public int compareTo(tab_ResearchObject tab_ResearchObject) {
		int value = 0;

		value = getRo_name().compareTo(tab_ResearchObject.getRo_name());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof tab_ResearchObject)) {
			return false;
		}

		tab_ResearchObject tab_ResearchObject = (tab_ResearchObject)obj;

		long primaryKey = tab_ResearchObject.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		tab_ResearchObjectModelImpl tab_ResearchObjectModelImpl = this;

		tab_ResearchObjectModelImpl._originalRo_name = tab_ResearchObjectModelImpl._ro_name;

		tab_ResearchObjectModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<tab_ResearchObject> toCacheModel() {
		tab_ResearchObjectCacheModel tab_ResearchObjectCacheModel = new tab_ResearchObjectCacheModel();

		tab_ResearchObjectCacheModel.ro_ID = getRo_ID();

		tab_ResearchObjectCacheModel.ro_name = getRo_name();

		String ro_name = tab_ResearchObjectCacheModel.ro_name;

		if ((ro_name != null) && (ro_name.length() == 0)) {
			tab_ResearchObjectCacheModel.ro_name = null;
		}

		tab_ResearchObjectCacheModel.ro_meta = getRo_meta();

		String ro_meta = tab_ResearchObjectCacheModel.ro_meta;

		if ((ro_meta != null) && (ro_meta.length() == 0)) {
			tab_ResearchObjectCacheModel.ro_meta = null;
		}

		return tab_ResearchObjectCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{ro_ID=");
		sb.append(getRo_ID());
		sb.append(", ro_name=");
		sb.append(getRo_name());
		sb.append(", ro_meta=");
		sb.append(getRo_meta());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("org.gfbio.model.tab_ResearchObject");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>ro_ID</column-name><column-value><![CDATA[");
		sb.append(getRo_ID());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>ro_name</column-name><column-value><![CDATA[");
		sb.append(getRo_name());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>ro_meta</column-name><column-value><![CDATA[");
		sb.append(getRo_meta());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = tab_ResearchObject.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			tab_ResearchObject.class
		};
	private long _ro_ID;
	private String _ro_name;
	private String _originalRo_name;
	private String _ro_meta;
	private long _columnBitmask;
	private tab_ResearchObject _escapedModel;
}