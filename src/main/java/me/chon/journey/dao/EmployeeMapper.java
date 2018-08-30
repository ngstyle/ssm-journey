package me.chon.journey.dao;

import java.util.List;
import me.chon.journey.bean.Employee;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer empId);

    int insert(Employee record);

    Employee selectByPrimaryKey(Integer empId);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);
}