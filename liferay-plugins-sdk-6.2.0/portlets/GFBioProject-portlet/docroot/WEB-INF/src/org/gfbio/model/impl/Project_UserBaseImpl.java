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

import com.liferay.portal.kernel.exception.SystemException;

import org.gfbio.model.Project_User;

import org.gfbio.service.Project_UserLocalServiceUtil;

/**
 * The extended model base implementation for the Project_User service. Represents a row in the &quot;gfbio_Project_User&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link Project_UserImpl}.
 * </p>
 *
 * @author Felicitas Loeffler
 * @see Project_UserImpl
 * @see org.gfbio.model.Project_User
 * @generated
 */
public abstract class Project_UserBaseImpl extends Project_UserModelImpl
	implements Project_User {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a project_ user model instance should use the {@link Project_User} interface instead.
	 */
	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			Project_UserLocalServiceUtil.addProject_User(this);
		}
		else {
			Project_UserLocalServiceUtil.updateProject_User(this);
		}
	}
}