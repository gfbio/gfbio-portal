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

package org.gfbio.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link Project_User_WorkerLocalService}.
 *
 * @author froemm
 * @see Project_User_WorkerLocalService
 * @generated
 */
public class Project_User_WorkerLocalServiceWrapper
	implements Project_User_WorkerLocalService,
		ServiceWrapper<Project_User_WorkerLocalService> {
	public Project_User_WorkerLocalServiceWrapper(
		Project_User_WorkerLocalService project_User_WorkerLocalService) {
		_project_User_WorkerLocalService = project_User_WorkerLocalService;
	}

	/**
	* Adds the project_ user_ worker to the database. Also notifies the appropriate model listeners.
	*
	* @param project_User_Worker the project_ user_ worker
	* @return the project_ user_ worker that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public org.gfbio.model.Project_User_Worker addProject_User_Worker(
		org.gfbio.model.Project_User_Worker project_User_Worker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.addProject_User_Worker(project_User_Worker);
	}

	/**
	* Creates a new project_ user_ worker with the primary key. Does not add the project_ user_ worker to the database.
	*
	* @param project_User_WorkerPK the primary key for the new project_ user_ worker
	* @return the new project_ user_ worker
	*/
	@Override
	public org.gfbio.model.Project_User_Worker createProject_User_Worker(
		org.gfbio.service.persistence.Project_User_WorkerPK project_User_WorkerPK) {
		return _project_User_WorkerLocalService.createProject_User_Worker(project_User_WorkerPK);
	}

	/**
	* Deletes the project_ user_ worker with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param project_User_WorkerPK the primary key of the project_ user_ worker
	* @return the project_ user_ worker that was removed
	* @throws PortalException if a project_ user_ worker with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public org.gfbio.model.Project_User_Worker deleteProject_User_Worker(
		org.gfbio.service.persistence.Project_User_WorkerPK project_User_WorkerPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.deleteProject_User_Worker(project_User_WorkerPK);
	}

	/**
	* Deletes the project_ user_ worker from the database. Also notifies the appropriate model listeners.
	*
	* @param project_User_Worker the project_ user_ worker
	* @return the project_ user_ worker that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public org.gfbio.model.Project_User_Worker deleteProject_User_Worker(
		org.gfbio.model.Project_User_Worker project_User_Worker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.deleteProject_User_Worker(project_User_Worker);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _project_User_WorkerLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.gfbio.model.impl.Project_User_WorkerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.gfbio.model.impl.Project_User_WorkerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public org.gfbio.model.Project_User_Worker fetchProject_User_Worker(
		org.gfbio.service.persistence.Project_User_WorkerPK project_User_WorkerPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.fetchProject_User_Worker(project_User_WorkerPK);
	}

	/**
	* Returns the project_ user_ worker with the primary key.
	*
	* @param project_User_WorkerPK the primary key of the project_ user_ worker
	* @return the project_ user_ worker
	* @throws PortalException if a project_ user_ worker with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public org.gfbio.model.Project_User_Worker getProject_User_Worker(
		org.gfbio.service.persistence.Project_User_WorkerPK project_User_WorkerPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.getProject_User_Worker(project_User_WorkerPK);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the project_ user_ workers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.gfbio.model.impl.Project_User_WorkerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of project_ user_ workers
	* @param end the upper bound of the range of project_ user_ workers (not inclusive)
	* @return the range of project_ user_ workers
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<org.gfbio.model.Project_User_Worker> getProject_User_Workers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.getProject_User_Workers(start,
			end);
	}

	/**
	* Returns the number of project_ user_ workers.
	*
	* @return the number of project_ user_ workers
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getProject_User_WorkersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.getProject_User_WorkersCount();
	}

	/**
	* Updates the project_ user_ worker in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param project_User_Worker the project_ user_ worker
	* @return the project_ user_ worker that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public org.gfbio.model.Project_User_Worker updateProject_User_Worker(
		org.gfbio.model.Project_User_Worker project_User_Worker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _project_User_WorkerLocalService.updateProject_User_Worker(project_User_Worker);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _project_User_WorkerLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_project_User_WorkerLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _project_User_WorkerLocalService.invokeMethod(name,
			parameterTypes, arguments);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public Project_User_WorkerLocalService getWrappedProject_User_WorkerLocalService() {
		return _project_User_WorkerLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedProject_User_WorkerLocalService(
		Project_User_WorkerLocalService project_User_WorkerLocalService) {
		_project_User_WorkerLocalService = project_User_WorkerLocalService;
	}

	@Override
	public Project_User_WorkerLocalService getWrappedService() {
		return _project_User_WorkerLocalService;
	}

	@Override
	public void setWrappedService(
		Project_User_WorkerLocalService project_User_WorkerLocalService) {
		_project_User_WorkerLocalService = project_User_WorkerLocalService;
	}

	private Project_User_WorkerLocalService _project_User_WorkerLocalService;
}