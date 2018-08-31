package me.chon;


import com.github.pagehelper.PageInfo;
import me.chon.journey.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:spring-mvc.xml"})
public class MvcTest {

    // 传入springmvc 的 ioc (@WebAppConfiguration)
    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {
        // 模拟请求 拿到返回值
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/emps")
                        .param("pageNum", "10"))
                .andReturn();

        // 请求成功后，请求域中会有pageInfo，取出pageInfo进行验证

        MockHttpServletRequest request = mvcResult.getRequest();

        PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");

        System.out.println("当前页码：" + pageInfo.getPageNum());
        System.out.println("总页码：" + pageInfo.getPages());
        System.out.println("总记录数：" + pageInfo.getTotal());
        System.out.println("在页面需要连续显示的页码：");

        int[] navigatepageNums = pageInfo.getNavigatepageNums();
        for (int num : navigatepageNums) {
            System.out.print(" " + num);
        }

        System.out.println("");

        List<Employee> emps = pageInfo.getList();
        for (Employee emp : emps) {
            System.out.println(emp);
        }


        MockHttpServletResponse response = mvcResult.getResponse();
        System.out.println("返回值：" + response);
    }

}
