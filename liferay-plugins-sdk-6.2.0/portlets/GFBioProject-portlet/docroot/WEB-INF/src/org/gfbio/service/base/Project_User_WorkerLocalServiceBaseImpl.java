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

package org.gfbio.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.persistence.UserPersistence;

import org.gfbio.model.Project_User_Worker;

import org.gfbio.service.Project_User_WorkerLocalService;
import org.gfbio.service.persistence.ProjectPersistence;
import org.gfbio.service.persistence.Project_ResearchObjectPersistence;
import org.gfbio.service.persistence.Project_User_PIPersistence;
import org.gfbio.service.persistence.Project_User_WorkerPK;
import org.gfbio.service.persistence.Project_User_WorkerPersistence;
import org.gfbio.service.persistence.ResearchObjectPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the project_ user_ worker local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link org.gfbio.service.impl.Project_User_WorkerLocalServiceImpl}.
 * </p>
 *
 * @author froemm
 * @see org.gfbio.service.impl.Project_User_WorkerLocalServiceImpl
 * @see org.gfbio.service.Project_User_WorkerLocalServiceUtil
 * @generated
 */
public abstract class Project_User_WorkerLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements Project_User_WorkerLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link org.gfbio.service.Project_User_WorkerLocalServiceUtil} to access the project_ user_ worker local service.
	 */

	/**
	 * Adds the project_ user_ worker to the database. Also notifies the appropriate model listeners.
	 *
	 * @param project_User_Worker the project_ user_ worker
	 * @return the project_ user_ worker that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Project_User_Worker addProject_User_Worker(
		Project_User_Worker project_User_Worker) throws SystemException {
		project_User_Worker.setNew(true);

		return project_User_WorkerPersistence.update(project_User_Worker);
	}

	/**
	 * Creates a new project_ user_ worker with the primary key. Does not add the project_ user_ worker to the database.
	 *
	 * @param project_User_WorkerPK the primary key for the new project_ user_ worker
	 * @return the new project_ user_ worker
	 */
	@Override
	public Project_User_Worker createProject_User_Worker(
		Project_User_WorkerPK project_User_WorkerPK) {
		return project_User_WorkerPersistence.create(project_User_WorkerPK);
	}

	/**
	 * Deletes the project_ user_ worker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param project_User_WorkerPK the primary key of the project_ user_ worker
	 * @return the project_ user_ worker that was removed
	 * @throws PortalException if a project_ user_ worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Project_User_Worker deleteProject_User_Worker(
		Project_User_WorkerPK project_User_WorkerPK)
		throws PortalException, SystemException {
		return project_User_WorkerPersistence.remove(project_User_WorkerPK);
	}

	/**
	 * Deletes the project_ user_ worker from the database. Also notifies the appropriate model listeners.
	 *
	 * @param project_User_Worker the project_ user_ worker
	 * @return the project_ user_ worker that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Project_User_Worker deleteProject_User_Worker(
		Project_User_Worker project_User_Worker) throws SystemException {
		return project_User_WorkerPersistence.remove(project_User_Worker);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(Project_User_Worker.class,
			clazz.getClassLoader());
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
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return project_User_WorkerPersistence.findWithDynamicQuery(dynamicQuery);
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
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return project_User_WorkerPersistence.findWithDynamicQuery(dynamicQuery,
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
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return project_User_WorkerPersistence.findWithDynamicQuery(dynamicQuery,
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
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return project_User_WorkerPersistence.countWithDynamicQuery(dynamicQuery);
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
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) throws SystemException {
		return project_User_WorkerPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public Project_User_Worker fetchProject_User_Worker(
		Project_User_WorkerPK project_User_WorkerPK) throws SystemException {
		return project_User_WorkerPersistence.fetchByPrimaryKey(project_User_WorkerPK);
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
	public Project_User_Worker getProject_User_Worker(
		Project_User_WorkerPK project_User_WorkerPK)
		throws PortalException, SystemException {
		return project_User_WorkerPersistence.findByPrimaryKey(project_User_WorkerPK);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return project_User_WorkerPersistence.findByPrimaryKey(primaryKeyObj);
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
	public List<Project_User_Worker> getProject_User_Workers(int start, int end)
		throws SystemException {
		return project_User_WorkerPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of project_ user_ workers.
	 *
	 * @return the number of project_ user_ workers
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getProject_User_WorkersCount() throws SystemException {
		return project_User_WorkerPersistence.countAll();
	}

	/**
	 * Updates the project_ user_ worker in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param project_User_Worker the project_ user_ worker
	 * @return the project_ user_ worker that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Project_User_Worker updateProject_User_Worker(
		Project_User_Worker project_User_Worker) throws SystemException {
		return project_User_WorkerPersistence.update(project_User_Worker);
	}

	/**
	 * Returns the project local service.
	 *
	 * @return the project local service
	 */
	public org.gfbio.service.ProjectLocalService getProjectLocalService() {
		return projectLocalService;
	}

	/**
	 * Sets the project local service.
	 *
	 * @param projectLocalService the project local service
	 */
	public void setProjectLocalService(
		org.gfbio.service.ProjectLocalService projectLocalService) {
		this.projectLocalService = projectLocalService;
	}

	/**
	 * Returns the project remote service.
	 *
	 * @return the project remote service
	 */
	public org.gfbio.service.ProjectService getProjectService() {
		return projectService;
	}

	/**
	 * Sets the project remote service.
	 *
	 * @param projectService the project remote service
	 */
	public void setProjectService(
		org.gfbio.service.ProjectService projectService) {
		this.projectService = projectService;
	}

	/**
	 * Returns the project persistence.
	 *
	 * @return the project persistence
	 */
	public ProjectPersistence getProjectPersistence() {
		return projectPersistence;
	}

	/**
	 * Sets the project persistence.
	 *
	 * @param projectPersistence the project persistence
	 */
	public void setProjectPersistence(ProjectPersistence projectPersistence) {
		this.projectPersistence = projectPersistence;
	}

	/**
	 * Returns the project_ research object local service.
	 *
	 * @return the project_ research object local service
	 */
	public org.gfbio.service.Project_ResearchObjectLocalService getProject_ResearchObjectLocalService() {
		return project_ResearchObjectLocalService;
	}

	/**
	 * Sets the project_ research object local service.
	 *
	 * @param project_ResearchObjectLocalService the project_ research object local service
	 */
	public void setProject_ResearchObjectLocalService(
		org.gfbio.service.Project_ResearchObjectLocalService project_ResearchObjectLocalService) {
		this.project_ResearchObjectLocalService = project_ResearchObjectLocalService;
	}

	/**
	 * Returns the project_ research object remote service.
	 *
	 * @return the project_ research object remote service
	 */
	public org.gfbio.service.Project_ResearchObjectService getProject_ResearchObjectService() {
		return project_ResearchObjectService;
	}

	/**
	 * Sets the project_ research object remote service.
	 *
	 * @param project_ResearchObjectService the project_ research object remote service
	 */
	public void setProject_ResearchObjectService(
		org.gfbio.service.Project_ResearchObjectService project_ResearchObjectService) {
		this.project_ResearchObjectService = project_ResearchObjectService;
	}

	/**
	 * Returns the project_ research object persistence.
	 *
	 * @return the project_ research object persistence
	 */
	public Project_ResearchObjectPersistence getProject_ResearchObjectPersistence() {
		return project_ResearchObjectPersistence;
	}

	/**
	 * Sets the project_ research object persistence.
	 *
	 * @param project_ResearchObjectPersistence the project_ research object persistence
	 */
	public void setProject_ResearchObjectPersistence(
		Project_ResearchObjectPersistence project_ResearchObjectPersistence) {
		this.project_ResearchObjectPersistence = project_ResearchObjectPersistence;
	}

	/**
	 * Returns the project_ user_ p i local service.
	 *
	 * @return the project_ user_ p i local service
	 */
	public org.gfbio.service.Project_User_PILocalService getProject_User_PILocalService() {
		return project_User_PILocalService;
	}

	/**
	 * Sets the project_ user_ p i local service.
	 *
	 * @param project_User_PILocalService the project_ user_ p i local service
	 */
	public void setProject_User_PILocalService(
		org.gfbio.service.Project_User_PILocalService project_User_PILocalService) {
		this.project_User_PILocalService = project_User_PILocalService;
	}

	/**
	 * Returns the project_ user_ p i remote service.
	 *
	 * @return the project_ user_ p i remote service
	 */
	public org.gfbio.service.Project_User_PIService getProject_User_PIService() {
		return project_User_PIService;
	}

	/**
	 * Sets the project_ user_ p i remote service.
	 *
	 * @param project_User_PIService the project_ user_ p i remote service
	 */
	public void setProject_User_PIService(
		org.gfbio.service.Project_User_PIService project_User_PIService) {
		this.project_User_PIService = project_User_PIService;
	}

	/**
	 * Returns the project_ user_ p i persistence.
	 *
	 * @return the project_ user_ p i persistence
	 */
	public Project_User_PIPersistence getProject_User_PIPersistence() {
		return project_User_PIPersistence;
	}

	/**
	 * Sets the project_ user_ p i persistence.
	 *
	 * @param project_User_PIPersistence the project_ user_ p i persistence
	 */
	public void setProject_User_PIPersistence(
		Project_User_PIPersistence project_User_PIPersistence) {
		this.project_User_PIPersistence = project_User_PIPersistence;
	}

	/**
	 * Returns the project_ user_ worker local service.
	 *
	 * @return the project_ user_ worker local service
	 */
	public org.gfbio.service.Project_User_WorkerLocalService getProject_User_WorkerLocalService() {
		return project_User_WorkerLocalService;
	}

	/**
	 * Sets the project_ user_ worker local service.
	 *
	 * @param project_User_WorkerLocalService the project_ user_ worker local service
	 */
	public void setProject_User_WorkerLocalService(
		org.gfbio.service.Project_User_WorkerLocalService project_User_WorkerLocalService) {
		this.project_User_WorkerLocalService = project_User_WorkerLocalService;
	}

	/**
	 * Returns the project_ user_ worker remote service.
	 *
	 * @return the project_ user_ worker remote service
	 */
	public org.gfbio.service.Project_User_WorkerService getProject_User_WorkerService() {
		return project_User_WorkerService;
	}

	/**
	 * Sets the project_ user_ worker remote service.
	 *
	 * @param project_User_WorkerService the project_ user_ worker remote service
	 */
	public void setProject_User_WorkerService(
		org.gfbio.service.Project_User_WorkerService project_User_WorkerService) {
		this.project_User_WorkerService = project_User_WorkerService;
	}

	/**
	 * Returns the project_ user_ worker persistence.
	 *
	 * @return the project_ user_ worker persistence
	 */
	public Project_User_WorkerPersistence getProject_User_WorkerPersistence() {
		return project_User_WorkerPersistence;
	}

	/**
	 * Sets the project_ user_ worker persistence.
	 *
	 * @param project_User_WorkerPersistence the project_ user_ worker persistence
	 */
	public void setProject_User_WorkerPersistence(
		Project_User_WorkerPersistence project_User_WorkerPersistence) {
		this.project_User_WorkerPersistence = project_User_WorkerPersistence;
	}

	/**
	 * Returns the research object local service.
	 *
	 * @return the research object local service
	 */
	public org.gfbio.service.ResearchObjectLocalService getResearchObjectLocalService() {
		return researchObjectLocalService;
	}

	/**
	 * Sets the research object local service.
	 *
	 * @param researchObjectLocalService the research object local service
	 */
	public void setResearchObjectLocalService(
		org.gfbio.service.ResearchObjectLocalService researchObjectLocalService) {
		this.researchObjectLocalService = researchObjectLocalService;
	}

	/**
	 * Returns the research object remote service.
	 *
	 * @return the research object remote service
	 */
	public org.gfbio.service.ResearchObjectService getResearchObjectService() {
		return researchObjectService;
	}

	/**
	 * Sets the research object remote service.
	 *
	 * @param researchObjectService the research object remote service
	 */
	public void setResearchObjectService(
		org.gfbio.service.ResearchObjectService researchObjectService) {
		this.researchObjectService = researchObjectService;
	}

	/**
	 * Returns the research object persistence.
	 *
	 * @return the research object persistence
	 */
	public ResearchObjectPersistence getResearchObjectPersistence() {
		return researchObjectPersistence;
	}

	/**
	 * Sets the research object persistence.
	 *
	 * @param researchObjectPersistence the research object persistence
	 */
	public void setResearchObjectPersistence(
		ResearchObjectPersistence researchObjectPersistence) {
		this.researchObjectPersistence = researchObjectPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();

		PersistedModelLocalServiceRegistryUtil.register("org.gfbio.model.Project_User_Worker",
			project_User_WorkerLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"org.gfbio.model.Project_User_Worker");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	@Override
	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != _classLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected Class<?> getModelClass() {
		return Project_User_Worker.class;
	}

	protected String getModelClassName() {
		return Project_User_Worker.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = project_User_WorkerPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = org.gfbio.service.ProjectLocalService.class)
	protected org.gfbio.service.ProjectLocalService projectLocalService;
	@BeanReference(type = org.gfbio.service.ProjectService.class)
	protected org.gfbio.service.ProjectService projectService;
	@BeanReference(type = ProjectPersistence.class)
	protected ProjectPersistence projectPersistence;
	@BeanReference(type = org.gfbio.service.Project_ResearchObjectLocalService.class)
	protected org.gfbio.service.Project_ResearchObjectLocalService project_ResearchObjectLocalService;
	@BeanReference(type = org.gfbio.service.Project_ResearchObjectService.class)
	protected org.gfbio.service.Project_ResearchObjectService project_ResearchObjectService;
	@BeanReference(type = Project_ResearchObjectPersistence.class)
	protected Project_ResearchObjectPersistence project_ResearchObjectPersistence;
	@BeanReference(type = org.gfbio.service.Project_User_PILocalService.class)
	protected org.gfbio.service.Project_User_PILocalService project_User_PILocalService;
	@BeanReference(type = org.gfbio.service.Project_User_PIService.class)
	protected org.gfbio.service.Project_User_PIService project_User_PIService;
	@BeanReference(type = Project_User_PIPersistence.class)
	protected Project_User_PIPersistence project_User_PIPersistence;
	@BeanReference(type = org.gfbio.service.Project_User_WorkerLocalService.class)
	protected org.gfbio.service.Project_User_WorkerLocalService project_User_WorkerLocalService;
	@BeanReference(type = org.gfbio.service.Project_User_WorkerService.class)
	protected org.gfbio.service.Project_User_WorkerService project_User_WorkerService;
	@BeanReference(type = Project_User_WorkerPersistence.class)
	protected Project_User_WorkerPersistence project_User_WorkerPersistence;
	@BeanReference(type = org.gfbio.service.ResearchObjectLocalService.class)
	protected org.gfbio.service.ResearchObjectLocalService researchObjectLocalService;
	@BeanReference(type = org.gfbio.service.ResearchObjectService.class)
	protected org.gfbio.service.ResearchObjectService researchObjectService;
	@BeanReference(type = ResearchObjectPersistence.class)
	protected ResearchObjectPersistence researchObjectPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private String _beanIdentifier;
	private ClassLoader _classLoader;
	private Project_User_WorkerLocalServiceClpInvoker _clpInvoker = new Project_User_WorkerLocalServiceClpInvoker();
}