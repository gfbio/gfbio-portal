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

package org.gfbio.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import org.gfbio.service.persistence.Project_ResearchObjectPK;

import java.io.Serializable;

/**
 * The base model interface for the Project_ResearchObject service. Represents a row in the &quot;gfbio_Project_ResearchObject&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link org.gfbio.model.impl.Project_ResearchObjectModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link org.gfbio.model.impl.Project_ResearchObjectImpl}.
 * </p>
 *
 * @author Felicitas Loeffler
 * @see Project_ResearchObject
 * @see org.gfbio.model.impl.Project_ResearchObjectImpl
 * @see org.gfbio.model.impl.Project_ResearchObjectModelImpl
 * @generated
 */
public interface Project_ResearchObjectModel extends BaseModel<Project_ResearchObject> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a project_ research object model instance should use the {@link Project_ResearchObject} interface instead.
	 */

	/**
	 * Returns the primary key of this project_ research object.
	 *
	 * @return the primary key of this project_ research object
	 */
	public Project_ResearchObjectPK getPrimaryKey();

	/**
	 * Sets the primary key of this project_ research object.
	 *
	 * @param primaryKey the primary key of this project_ research object
	 */
	public void setPrimaryKey(Project_ResearchObjectPK primaryKey);

	/**
	 * Returns the project i d of this project_ research object.
	 *
	 * @return the project i d of this project_ research object
	 */
	public long getProjectID();

	/**
	 * Sets the project i d of this project_ research object.
	 *
	 * @param projectID the project i d of this project_ research object
	 */
	public void setProjectID(long projectID);

	/**
	 * Returns the research object i d of this project_ research object.
	 *
	 * @return the research object i d of this project_ research object
	 */
	public long getResearchObjectID();

	/**
	 * Sets the research object i d of this project_ research object.
	 *
	 * @param researchObjectID the research object i d of this project_ research object
	 */
	public void setResearchObjectID(long researchObjectID);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(Project_ResearchObject project_ResearchObject);

	@Override
	public int hashCode();

	@Override
	public CacheModel<Project_ResearchObject> toCacheModel();

	@Override
	public Project_ResearchObject toEscapedModel();

	@Override
	public Project_ResearchObject toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}