package me.chon.journey.dao;

import java.util.List;
import me.chon.journey.bean.Department;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(Department record);

    Department selectByPrimaryKey(Integer deptId);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);
}