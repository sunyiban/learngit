package com.qqms.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.qqms.model.Log;
import com.qqms.service.LogsService;
import com.qqms.util.Constants;
import com.qqms.util.PageUtil;
import com.qqms.viewModel.GridModel;
@Namespace("/logs")
@Action(value = "logsAction")
public class LogsAction extends BaseAction implements ModelDriven<Log>
{
	private static final long serialVersionUID = 3658084064057123814L;
	private Log log;
	private LogsService logsService;

	public Log getLog()
	{
		return log;
	}

	public void setLog(Log log )
	{
		this.log = log;
	}

	@Autowired
	public void setLogsService(LogsService logsService )
	{
		this.logsService = logsService;
	}
	
	/**  
	* 函数功能说明 TODO:查询所有日志
	* Administrator修改者名字
	* 2013-6-18修改日期
	* 修改内容
	* @Title: findLogsAllList 
	* @Description: 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String findLogsAllList() throws Exception
	{
		Map<String, Object> map=new HashMap<String, Object>();
		if (null!=searchValue&&!"".equals(searchValue))
		{
			map.put(searchName, Constants.GET_SQL_LIKE+searchValue+Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil=new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
		GridModel gridModel=new GridModel();
		gridModel.setRows(logsService.findLogsAllList(map, pageUtil));
		gridModel.setTotal(logsService.getCount(map,pageUtil));
		OutputJson(gridModel);
		return null;
	}
	
	/**  
	* 函数功能说明 TODO:持久化日志弹窗
	* Administrator修改者名字
	* 2013-6-18修改日期
	* 修改内容
	* @Title: persistenceLogs 
	* @Description: 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String persistenceLogs() throws Exception
	{
		OutputJson(getMessage(logsService.persistenceLogs(getModel())),Constants.TEXT_TYPE_PLAIN);
		return null;
	}
	
	/**  
	* 函数功能说明 TODO:删除日志
	* Administrator修改者名字
	* 2013-6-18修改日期
	* 修改内容
	* @Title: delLogs 
	* @Description: 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String delLogs() throws Exception
	{
		OutputJson(getMessage(logsService.delLogs(getModel().getLogId())));
		return null;
	}
	
	public Log getModel()
	{
		if (null==log)
		{
			log=new Log();
		}
		return log;
	}
}
