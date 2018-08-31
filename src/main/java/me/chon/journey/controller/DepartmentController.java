package me.chon.journey.controller;

import me.chon.journey.bean.Department;
import me.chon.journey.bean.HttpResult;
import me.chon.journey.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @RequestMapping("/depts")
    @ResponseBody
    public HttpResult<List<Department>> getDepts() {
        List<Department> depts = departmentService.getDepts();

        HttpResult httpResult = HttpResult.success();
        httpResult.setData(depts);

        return httpResult;
    }

}
