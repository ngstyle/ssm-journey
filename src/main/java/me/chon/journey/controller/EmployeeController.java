package me.chon.journey.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.chon.journey.bean.Employee;
import me.chon.journey.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 处理员工CRUD
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 查询员工数据（分页） 引入pageHelper
     * @return
     */
    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, Model model) {

        // 在查询之前 使用pageHelper
        PageHelper.startPage(pageNum, 5);

        // startPage 后面紧跟的这个查询就是分页查询
        List<Employee> emps = employeeService.getAll();

        // 使用pageInfo 包装后的结果，封装了详细的分页信息
        PageInfo pageInfo = new PageInfo(emps, 5);

        model.addAttribute("pageInfo", pageInfo);

        return "list";
    }

}
