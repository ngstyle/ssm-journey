package me.chon.journey.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.chon.journey.bean.Employee;
import me.chon.journey.bean.HttpResult;
import me.chon.journey.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 处理员工CRUD
 */
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 查询员工数据（分页） 引入pageHelper
     * @return
     */
//    @RequestMapping("/emps")
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


    @RequestMapping("/emps")
    public HttpResult<PageInfo> getEmpsWithJson(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum) {

        // 在查询之前 使用pageHelper
        PageHelper.startPage(pageNum, 5);

        // startPage 后面紧跟的这个查询就是分页查询
        List<Employee> emps = employeeService.getAll();

        // 使用pageInfo 包装后的结果，封装了详细的分页信息
        PageInfo pageInfo = new PageInfo(emps, 5);

        HttpResult httpResult = HttpResult.success();
        httpResult.setData(pageInfo);

        return httpResult;
    }

    @PostMapping(value = "/emp")
    public HttpResult<List<FieldError>> addEmp(@Valid Employee employee, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            HttpResult failResult = HttpResult.fail();
            failResult.setData(bindingResult.getFieldErrors());
            return failResult;
        }

        employeeService.addEmp(employee);
        return HttpResult.success();
    }

    @GetMapping(value = "/emp/{id}")
    public HttpResult<Employee> getEmp(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmp(id);

        HttpResult httpResult = HttpResult.success();
        httpResult.setData(employee);
        return httpResult;
    }

    @PutMapping(value = "/emp/{empId}")
    public HttpResult<Integer> updateEmp(Employee employee) {
        HttpResult httpResult = HttpResult.success();
        httpResult.setData(employeeService.updateEmp(employee));

        return httpResult;
    }

    @PostMapping(value = "/checkuser")
    public HttpResult<Boolean> checkUser(@RequestParam(value = "empName") String empName) {
        boolean isUserNameExist = employeeService.isUserNameExist(empName);

        String regex = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        HttpResult httpResult;
        if (!empName.matches(regex)) {
            httpResult = HttpResult.fail();
            httpResult.setMessage("用户名可以是2-5位中文或者6-16位英文和数字的组合<后端>");
            httpResult.setData(false);
            return httpResult;
        }

        if (isUserNameExist) {
            httpResult = HttpResult.fail();
            httpResult.setMessage("用户名已存在");
        } else {
            httpResult = HttpResult.success();
            httpResult.setMessage("");
        }
        httpResult.setData(!isUserNameExist);

        return httpResult;
    }
}
