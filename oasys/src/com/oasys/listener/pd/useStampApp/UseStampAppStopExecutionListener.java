package com.oasys.listener.pd.useStampApp;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.oasys.model.BadgeApp;
import com.oasys.model.StatusNameRef;
import com.oasys.model.UseStampApp;
import com.oasys.service.BadgeAppService;
import com.oasys.service.UseStampAppService;
import com.oasys.util.Constants;

/**
 * @Creater wangxincheng
 * @File_Name PPEScrapAppProcessStartExecutionListener.java
 * @Version v1.0
 * @Creation_Date 2015年9月18日 
 * @Modifier
 * @Modified_Date
 * @Description 申请提交的监听器
 */
@SuppressWarnings("serial")
public class UseStampAppStopExecutionListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// 得到上下文环境
		WebApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();
		// 使用上下文环境中的getBean方法得到bean实例
		UseStampAppService useStampAppService =  (UseStampAppService) ctx
				.getBean("useStampAppService"); 
		// 根据流程实例获取流程的BusinessKey,并获取当前的业务订单的id
		Integer id = null;
		if (StringUtils.isNotBlank(execution.getProcessBusinessKey())) {
			// 截取字符串，取BusinessKey小数点的第2个值,第二个值为业务订单的id
			id = Integer.valueOf(execution.getProcessBusinessKey().split("\\.")[1]);
		}
		UseStampApp useStampApp = useStampAppService.finduseStampAppByUsaId(id);
		String statusKey= Constants.USESTAMPAPP_RE_KEY;
		//撤销时的流程和通过时的流程申请状态
		if(useStampApp.getAppStatus().equals(statusKey)){
			useStampAppService.upBadgeProcStatus(id, "5");
		}else{
			useStampAppService.upBadgeProcStatus(id, "3");
		}
	}

}
