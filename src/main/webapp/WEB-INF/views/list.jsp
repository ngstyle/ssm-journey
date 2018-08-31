<%--
  Created by IntelliJ IDEA.
  User: chon
  Date: 2018/8/31
  Time: 上午11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <title>员工列表</title>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>

    <%--web 路径--%>
    <%--不以"/"开始的相对路径，找资源，以当前资源的路径为基准，容易出问题--%>
    <%--以"/"开始的相对路径，找资源，以服务器的路径为标准(http://localhost:3306)
        需要加上项目名，http://localhost:3306/journey
    --%>
    <link href="${APP_PATH }/resources/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="${APP_PATH}/resources/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>

    <div class="container">
        <%--标题--%>
        <div class="row">
            <div class="col-md-12">
                <h1>SSM-CRUD</h1>
            </div>
        </div>

        <%--按钮--%>
        <div class="row">
            <div class="col-md-4 col-md-offset-8 pull-right">
                <button class="btn btn-primary">新增</button>
                <button class="btn btn-danger">删除</button>
            </div>
        </div>

        <%--显示表格数据--%>
        <div class="row" style="margin-top: 40px">

            <div class="col-md-12">
                <table class="table table-hover">
                    <tr>
                        <th>#</th>
                        <th>empName</th>
                        <th>gender</th>
                        <th>email</th>
                        <th>departName</th>
                        <th>操作</th>
                    </tr>

                    <c:forEach items="${pageInfo.list }" var="emp">
                        <tr>
                            <td>${emp.empId }</td>
                            <td>${emp.empName }</td>
                            <td>${emp.gender=="M" ? "男" : "女" }</td>
                            <td>${emp.email }</td>
                            <td>${emp.department.deptName }</td>
                            <td>
                                <button class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    编辑
                                </button>
                                <button class="btn btn-danger btn-sm">
                                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                    删除
                                </button>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </div>

        </div>

        <%--显示分页信息--%>
        <div class="row">
            <%--分页文字信息--%>
            <div class="col-md-6">
                当前${pageInfo.pageNum }页，总${pageInfo.pages}页，总${pageInfo.total} 条记录
            </div>

            <%--分页条--%>
            <div class="col-md-6 pull-right">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li><a href="${APP_PATH }/emps?pageNum=1">首页</a></li>

                        <c:if test="${pageInfo.hasPreviousPage }">
                            <li>
                                <a href="${APP_PATH }/emps?pageNum=${pageInfo.pageNum-1 }" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>


                        <c:forEach items="${pageInfo.navigatepageNums }" var="num">
                            <c:if test="${pageInfo.pageNum == num }">
                                <li class="active"><a href="#">${num }</a></li>
                            </c:if>

                            <c:if test="${pageInfo.pageNum != num }">
                                <li><a href="${APP_PATH }/emps?pageNum=${num }">${num }</a></li>
                            </c:if>
                        </c:forEach>

                        <c:if test="${pageInfo.hasNextPage }">
                            <li>
                                <a href="${APP_PATH }/emps?pageNum=${pageInfo.pageNum+1 }" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </c:if>

                        <li><a href="${APP_PATH }/emps?pageNum=${pageInfo.pages }">末页</a></li>
                    </ul>
                </nav>
            </div>

        </div>

    </div>
</body>
</html>
