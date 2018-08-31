<%--
  Created by IntelliJ IDEA.
  User: chon
  Date: 2018/8/31
  Time: 上午11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
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
    <script src="${APP_PATH }/resources/jquery-1.12.4/jquery.min.js"></script>
    <script src="${APP_PATH}/resources/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <%--标题--%>
    <div class="row">
        <div class="col-lg-12">
            <h1>SSM-CRUD</h1>
        </div>
    </div>

    <%--按钮--%>
    <div class="row col-md-12">
        <div class="pull-right">
            <button class="btn btn-primary">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>

    <%--显示表格数据--%>
    <div class="row" style="margin-top: 40px">

        <div class="col-lg-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>departName</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>

                </tbody>

            </table>
        </div>

    </div>

    <%--显示分页信息--%>
    <div class="row col-md-12">
        <%--分页文字信息--%>
        <div class="pull-left" id="page_info_area">

        </div>

        <%--分页条--%>
        <div class="pull-right" id="page_nav_area">

        </div>

    </div>

</div>

<script type="text/javascript">
    // 使用Ajax 发送请求，获取json 数据
    $(function () {
        to_page(1);
    });


    function to_page(pageNum) {
        $.ajax({
            url: "${APP_PATH}/emps",
            data: "pageNum=" + pageNum,
            type: "GET",
            success: function (result) {
                //console.log(result);
                //1、解析并显示员工数据
                build_emps_table(result);
                //2、解析并显示分页信息
                build_page_info(result);
                //3、解析显示分页条数据
                build_page_nav(result);
            }
        });
    }

    function build_emps_table(result) {
        //清空table表格
        $("#emps_table tbody").empty();

        var emps = result.data.list;
        $.each(emps, function (index, emp) {
            let tdEmpId = $("<td></td>").append(emp.empId);
            let tdEmpName = $("<td></td>").append(emp.empName);
            let tdEmpGender = $("<td></td>").append(emp.gender == 'M' ? "男" : "女");
            let tdEmpEmail = $("<td></td>").append(emp.email);
            let tdDeptName = $("<td></td>").append(emp.department.deptName);

            let btnEdit = $("<button></button>")
                .addClass("btn btn-primary btn-sm")
                .append($("<span></span>").addClass("glyphicon glyphicon-pencil"))
                .append("编辑");

            let btnDelete = $("<button></button>")
                .addClass("btn btn-danger btn-sm")
                .append($("<span></span>").addClass("glyphicon glyphicon-trash"))
                .append("删除");

            let tdBtn = $("<td></td>").append(btnEdit).append(" ").append(btnDelete);

            $("<tr></tr>").append(tdEmpId)
                .append(tdEmpName)
                .append(tdEmpGender)
                .append(tdEmpEmail)
                .append(tdDeptName)
                .append(tdDeptName)
                .append(tdBtn)
                .appendTo("#emps_table tbody");

        })
    }

    function build_page_info(result) {
        $("#page_info_area").empty();

        $("#page_info_area").append("当前 " + result.data.pageNum + " 页，总 " + result.data.pages + " 页，" +
            "总 " + result.data.total + " 条记录");
    }

    function build_page_nav(result) {
        //page_nav_area
        $("#page_nav_area").empty();

        var ul = $("<ul></ul>").addClass("pagination");

        //构建元素
        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
        if (!result.data.hasPreviousPage) {
            firstPageLi.addClass("disabled");
            prePageLi.addClass("disabled");
        } else {
            //为元素添加点击翻页的事件
            firstPageLi.click(function () {
                to_page(1);
            });
            prePageLi.click(function () {
                to_page(result.data.pageNum - 1);
            });
        }


        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
        var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));
        if (!result.data.hasNextPage) {
            nextPageLi.addClass("disabled");
            lastPageLi.addClass("disabled");
        } else {
            nextPageLi.click(function () {
                to_page(result.data.pageNum + 1);
            });
            lastPageLi.click(function () {
                to_page(result.data.pages);
            });
        }


        //添加首页和前一页 的提示
        ul.append(firstPageLi).append(prePageLi);
        //1,2，3遍历给ul中添加页码提示
        $.each(result.data.navigatepageNums, function (index, item) {

            var numLi = $("<li></li>").append($("<a></a>").append(item));
            if (result.data.pageNum == item) {
                numLi.addClass("active");
            }
            numLi.click(function () {
                to_page(item);
            });
            ul.append(numLi);
        });
        //添加下一页和末页 的提示
        ul.append(nextPageLi).append(lastPageLi);

        //把ul加入到nav
        var navEle = $("<nav></nav>").append(ul);
        navEle.appendTo("#page_nav_area");
    }
</script>
</body>
</html>
