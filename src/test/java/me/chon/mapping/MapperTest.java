package me.chon.mapping;


import me.chon.journey.bean.Department;
import me.chon.journey.bean.Employee;
import me.chon.journey.dao.DepartmentMapper;
import me.chon.journey.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 *  Spring的项目推荐使用Spring的单元测试，可以自动注入我们需要的组件
 *  1. 导入spring-test 模块（<scope>test</scope> src/main 下面不会引用到）
 *  2. @ContextConfiguration(locations指定spring 配置文件位置
 *  3. autowired 引入需要的组件
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;

    @Test
    public void testCRUD() {
        // 1. 创建ioc 容器
//        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 2. 从容器中获取mapper
//        DepartmentMapper departmentMapper = ioc.getBean(DepartmentMapper.class);


        // 测试部门的添加
//        departmentMapper.insertSelective(new Department(null,"开发部"));
//        departmentMapper.insertSelective(new Department(null,"测试部"));
//        departmentMapper.insertSelective(new Department(null,"产品部"));

        // 测试员工的添加
//        employeeMapper.insertSelective(new Employee(null,"nohc","F","chon_den@sina.com", 1));

        // 员工的批量insert
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

        for (int i = 0; i < 1000; i++) {
            String uuid = UUID.randomUUID().toString().substring(0, 5) + i;
            mapper.insertSelective(new Employee(null,uuid,"M",uuid + "@nohc.me",1));
        }

        System.out.println("批量插入员工完成");

    }

}
