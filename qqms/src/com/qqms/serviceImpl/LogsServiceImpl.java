package com.qqms.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qqms.dao.PublicDao;
import com.qqms.model.Log;
import com.qqms.service.LogsService;
import com.qqms.util.Constants;
import com.qqms.util.HqlUtil;
import com.qqms.util.PageUtil;

@Service("logsService")
public class LogsServiceImpl implements LogsService {
	private PublicDao<Log> publicDao;

	@Autowired
	public void setPublicDao(PublicDao<Log> publicDao) {
		this.publicDao = publicDao;
	}

	/*
	 * (非 Javadoc) <p>Title: findLogsAllList</p> <p>Description: </p>
	 * 
	 * @param map
	 * 
	 * @param pageUtil
	 * 
	 * @return
	 */
	public List<Log> findLogsAllList(Map<String, Object> map, PageUtil pageUtil) {
		String hql = "from Log t where 1=1";
		hql += HqlUtil.getSearchConditionsHQL("t", map);
		hql += HqlUtil.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
	}

	public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
		String hql = "select count(*) from Log t where 1=1";
		hql += HqlUtil.getSearchConditionsHQL("t", map);
		hql += HqlUtil.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.count(hql, map);
	}

	/*
	 * (非 Javadoc) <p>Title: persistenceLogs</p> <p>Description: </p>
	 * 
	 * @param l
	 * 
	 * @return
	 */
	public boolean persistenceLogs(Log l) {
		if (null == l.getLogId() || "".equals(l.getLogId())) {
			l.setLogDate(new Date());
			l.setUserId(Constants.getCurrendUser().getUserId());
			publicDao.save(l);
		} else {
			l.setUserId(Constants.getCurrendUser().getUserId());
			publicDao.update(l);
		}
		return true;
	}

	/*
	 * (非 Javadoc) <p>Title: delLogs</p> <p>Description: </p>
	 * 
	 * @param logId
	 * 
	 * @return
	 */
	public boolean delLogs(Integer logId) {
		publicDao.delete(publicDao.get(Log.class, logId));
		return true;
	}
}
