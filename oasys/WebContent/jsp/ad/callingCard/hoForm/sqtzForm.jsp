<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
.easyui-textbox {
	height: 18px;
	width: 170px;
	line-height: 16px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
	transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
}

textarea:focus, input[type="text"]:focus {
	border-color: rgba(82, 168, 236, 0.8);
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px
		rgba(82, 168, 236, 0.6);
	outline: 0 none;
}
.table {
	background-color: transparent;
	border-collapse: collapse;
	border-spacing: 0;
	max-width: 100%;
}
fieldset {
	    border-width: 1px;
	    margin-top: 5px;
	    border-color:#F5F5F5;
	    -moz-border-radius:8px;
}
input, textarea {
	font-weight: normal;
}

.table td {
	padding: 6px;
}
.table th{
    text-align: right;
	padding: 6px;
}
.textStyle{
  text-align: right;
}
</style>
<script type="text/javascript">
$(function(){
	 $("#acceptTaskForm").form('load',$row);
})
/**
 * 营业部经理驳回/通过
 */
function submitTask(result,object){
	if($("#comment").val()==""){
		$.messager.alert("提示","请填写备注信息后再进行提交!","warning")
		return false;
	}
	//0驳回 1是通过
	$.messager.confirm('提示', '点击按钮之后将进入下一个任务活动环节,此任务将对您不可见!', function(r){
		if (r){
			var data = {
				"remark":$("#comment").val(),
				"result":result,
				"paId":$row.caID,
				"appNo":$row.appNo,
				"taskId":$row.taskId,
				"handleResult":result=='shenqingchongti'?1:0
			}
			$.ajax({
				type:"POST",
				url:"callingCard/handleTask.do",
				data:data,
				dataType:"JSON",
				success : function(msg) {
					$("#waitTaskGrid").datagrid('reload');
					$("#addWindow").dialog('close');
				}
			});
		}
	});
}
//申请调整
function tiaoZhengDialog(){
	$("<div></div>").dialog({
		title: '申请调整',    
	    width: 920,    
	    height: 400,    
	    closed: false,    
	    cache: false,    
	    href: 'purchaseAppController/toAddWindow.do',    
	    modal: true,
	    onClose : function(){
	    	$(this).remove();
	    },
	    buttons : [ {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$(this).remove(); 
			}
		}]
	});
}
</script>
     
<div data-options="region:'north',title:'North Title',split:true">
	<div style="overflow: auto;margin-left: 3px;">
	<form id="acceptTaskForm" method="post">
			 <input name="caID" id="caID"  type="hidden"/>
<!-- 		 <input name="auditId" type="hidden" value="noauditId"/>  -->
		 <table class="table" cellpadding="5px;">
			<tr>
			    <td class="textStyle">申请编号:</td>
				<td><input name="appNo" class="easyui-textbox" readonly="readonly" type="text"/></td>
				<td class="textStyle">申请人:</td>
				<td><input name="userName" class="easyui-textbox" readonly="readonly" type="text"/></td>
				<td class="textStyle">申请部门:</td>
				<td><input name="deptName" class="easyui-textbox" readonly="readonly"/></td>
				<td class="textStyle">职务:</td>
				<td><input name="positionName" class="easyui-textbox" readonly="readonly"/></td>
			</tr>
			<tr>
				<td class="textStyle">数量:</td>
				<td><input name="sumAppQty" class="easyui-textbox" readonly="readonly"/></td>
				<td class="textStyle">申请日期:</td>
				<td><input name="appDate" class="easyui-textbox" readonly="readonly"/></td>
			</tr>
			<tr>
			 	<td class="textStyle">备注:</td>
				<td colspan="5">
					<textarea id="comment" name="comment" class="easyui-validatebox easyui-textbox" style="width:688px;height:70px;"></textarea>
				</td>
			</tr>
		 </table>
		<div id="attachmentList" style="display:block;float:left;">
		</div>
		<div id="upload_form_div_add" style="margin-left: 10px;">
			<div id="upload_form_father_idDiv">
				<div id="upload_form_div">
				　	<font size="2">上传附件:&nbsp;</font>
					<input class="easyui-textbox easyui-combobox" type="text"/>
					<input name="fileName" type="text" placeholder="请输入附件名">
					<input id="file" name="file" type="file"  onchange="fileChange(this);"> 
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addACredential(this);">添加</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeACredential(this);">删除</a> 
				</div>
			</div>
		</div>
		<div id="upload_form" style="height: 30px; margin-left: 700px;">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="fileUploads(this)">上传附件</a>
		</div> 
	</form>
	</div>
	
	<div style="margin-left: 25px;margin-bottom: 5px;">
	    <a href="javascript:void(0);" class="easyui-linkbutton" onclick="tiaoZhengDialog();">申请调整</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitTask('shenqingchongti',this);">申请重提</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitTask('shenqingchexiao',this);">申请撤销</a>
	</div>
	
	<div id="lookInfo" class="easyui-accordion" style="height: 300px;width: 980px;overflow: hidden;">
	    <div title="备注信息" data-options="iconCls:'icon-cstbase',selected:true" >   
			<table id="lookLoanOrderdg" title="申请备注的信息"></table>
		</div>
	    <div title="附件信息" data-options="iconCls:'icon-cstbase'" >   
			<table id="lookAttachmentList" title="申请附件的信息"></table>
		</div>
	</div>
</div>   
<!-- 新增弹框 -->
<div id="tiaozhengDialog"></div>