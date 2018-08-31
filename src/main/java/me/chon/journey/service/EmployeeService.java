package me.chon.journey.service;

import me.chon.journey.bean.Employee;
import me.chon.journey.bean.EmployeeExample;
import me.chon.journey.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 查询所有员工
     * @return
     */
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    public int addEmp(Employee employee) {
        return employeeMapper.insertSelective(employee);
    }

    public boolean isUserNameExist(String empName) {
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpNameEqualTo(empName);

        long count = employeeMapper.countByExample(employeeExample);
        return count > 0;
    }
}
