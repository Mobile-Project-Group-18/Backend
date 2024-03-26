<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	Map<String, Object> mapmsg = (Map<String, Object>)request.getAttribute("mapmsg");
%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>无标题文档</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="../js/select-ui.min.js"></script>
<script type="text/javascript" src="../editor/kindeditor.js"></script>


<script type="text/javascript">
	$(document).ready(function(e) {
		$(".select1").uedSelect({
			width : 345
		});
		$(".select2").uedSelect({
			width : 167
		});
		$(".select3").uedSelect({
			width : 100
		});
	});
</script>
<script type="text/javascript">
 function ok(){
   var th = document.form2;
   th.action="<%=path%>/servlet/StudentAction?action_flag=updateStudentState&studentState=3&studentId=<%=mapmsg.get("studentId")%>";
		th.submit();
	}
	
	
	 function no(){
   var th = document.form2;
   th.action="<%=path%>/servlet/StudentAction?action_flag=updateStudentState&studentState=4&studentId=<%=mapmsg.get("studentId")%>";
		th.submit();
	}
</script>
</head>

<body>
	<form name="form2" action="" method="post"  >
		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li><a href="#">首页</a></li>
				<li><a href="#">表单</a></li>
			</ul>
		</div>

		<div class="formbody" style="width: 400px">

			<div class="formtitle">
				<span>基本信息</span>
			</div>

			<ul class="forminfo">
				<li>学生学号：<%=mapmsg.get("studentSchoolCard") %></li>
				<li>学生名称：<%=mapmsg.get("studentName") %></li>
				<li>学生昵称：<%=mapmsg.get("studentNickname") %></li>
				<li>学生手机：<%=mapmsg.get("studentPhone") %></li>
				<li>学校名称：<%=mapmsg.get("studentSchoolName") %></li>
				<li>学校地址：<%=mapmsg.get("studentSchoolAddress") %></li>
				<li>身份证号：<%=mapmsg.get("studentIdCard") %></li>
				<%String imageUrl = (String)mapmsg.get("studentSchoolImage");%>
				<li>学生证图片：<img style="width: 100px;height: 100px" src="http://192.168.1.168:1010/SchoolService/upload/<%=imageUrl%>"/></li>
				<li></li>
			</ul>

		</div>
	</form>
</body>
</html>
