
package com.wwnt;

import com.wwnt.entity.GpsFile;
import com.wwnt.entity.User;
import com.wwnt.entity.postionPoint;
import com.wwnt.repository.GpsFileRepository;
import com.wwnt.repository.PointRepository;
import com.wwnt.repository.UserRepository;
import com.wwnt.utils.GetLocations;
import com.wwnt.web.DisplayTracksController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.metadata.DataSourcePoolMetadataProviders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest

@AutoConfigureMockMvc
public class DisplayTracksApplicationTests {

    @Autowired
    GpsFileRepository gpsFileRepository;
    @Autowired
    PointRepository pointRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DisplayTracksController displayTracksController;
    @Autowired
    private MockHttpSession session;
    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(displayTracksController).build();
        session = new MockHttpSession();
        session.setAttribute("username","81dc9bdb52d04dc20036dbd8313ed055");
    }

    private static final Logger logger = LoggerFactory.getLogger(DisplayTracksApplicationTests.class);

    @Test
    public void testGetFileList() {
        List<GpsFile> list1 = gpsFileRepository.getFileList("81dc9bdb52d04dc20036dbd8313ed055");
        for (GpsFile gpsFile : list1) {
            System.out.println(gpsFile);
        }
    }

    @Test
    public void testDeleteFileById() {
        int i = gpsFileRepository.deleteFileById(131, "81dc9bdb52d04dc20036dbd8313ed055");
        System.out.println(i);

    }

    @Test
    public void testDeleteUser() {
        int i = gpsFileRepository.deleteUser("cfcd208495d565ef66e7dff9f98764da");
        System.out.println(i);

    }

    @Test
    public void testSelectTracksNameByUsername() {
        List<String> list1 = gpsFileRepository.selectTracksNameByUsername("81dc9bdb52d04dc20036dbd8313ed055");
        for (String gpsFile : list1) {
            System.out.println(gpsFile);
        }

    }
    @Test
    public void testUpdateFileName() {
        gpsFileRepository.updateFileName("1232.txt",131);

    }
    @Test
    public void testPointList() {
        System.out.println(pointRepository.list().size());
    }
    @Test
    public void testSelectfileName() {
        System.out.println(pointRepository.selectfileName().size());
    }
    @Test
    public void testUpdateById() {
        pointRepository.updateById(100.0,81011);
    }
    @Test
    public void testSelsectMaxId() {
        System.out.println(pointRepository.selsectMaxId());
    }
    @Test
    public void testSelSectSpeedByFileName() {
        String[] str1 = pointRepository.selsectSpeedByFileName("2路U.txt");
        for (String str:str1
             ) {
            System.out.println(str);
        }
    }
    @Test
    public void testDeleteByFileName() {
        pointRepository.deleteByFileName("2路U.txt");

    }
    @Test
    public void testUpdateFileNameByFileName() {
        pointRepository.updateFileNameByFileName("3.txt","2.txt");

    }
    @Test
    public void testGetRowCount() {
        System.out.println(userRepository.getRowCount());

    }
    @Test
    public void testSelectByusername() {
        System.out.println(userRepository.selectByusername("81dc9bdb52d04dc20036dbd8313ed055"));

    }
    @Test
    public void testSelectByUsernameandPassword() {
        System.out.println(userRepository.selectByUsernameandPassword("81dc9bdb52d04dc20036dbd8313ed055","81dc9bdb52d04dc20036dbd8313ed055"));

    }
    @Test
    public void testUserList() {
        List<User> list1 = userRepository.list();
        for (User user1:list1
             ) {
            System.out.println(user1);
        }
    }
    @Test
    public void testDeleteUserById() {
        System.out.println(userRepository.deleteUserById(10));

    }
    @Test
    public void testControllerGetKaptchaImage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                .get("/kaptcha")
                // 设置返回类型
                .accept(MediaType.APPLICATION_JSON_VALUE)
                // 添加请求参数
//                .param("name", "zxd")
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testControllerJst() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                .get("/jst")
                // 设置返回类型
                .accept(MediaType.APPLICATION_JSON_VALUE)
                // 添加请求参数
//                .param("name", "zxd")
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testControllerUpdateName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                .get("/updateName")
                // 设置返回类型
                .accept(MediaType.APPLICATION_JSON_VALUE)
                // 添加请求参数
                .param("fileId", "129")
                .param("fileName","11-09.txt")
                .param("newFileName","1.txt")
                .session(session)
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testControllerDelUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                .get("/delUser")
                // 设置返回类型
                .accept(MediaType.APPLICATION_JSON_VALUE)
                // 添加请求参数
                .param("userId", "213")
//                .param("fileName","11-09.txt")
//                .param("newFileName","1.txt")
                .session(session)
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testControllerElement() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                .get("/element")
                // 设置返回类型
                .accept(MediaType.APPLICATION_JSON_VALUE)
                // 添加请求参数
//                .param("userId", "213")
//                .param("fileName","11-09.txt")
//                .param("newFileName","1.txt")
                .session(session)
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testControllerStopReg() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                        .get("/stopReg")
                        // 设置返回类型
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        // 添加请求参数
//                .param("userId", "213")
//                .param("fileName","11-09.txt")
//                .param("newFileName","1.txt")
                        .session(session)
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testControllerAllowReg() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                        .get("/allowReg")
                        // 设置返回类型
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        // 添加请求参数
//                .param("userId", "213")
//                .param("fileName","11-09.txt")
//                .param("newFileName","1.txt")
                        .session(session)
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testControllerLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                        .get("/log")
                        // 设置返回类型
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        // 添加请求参数
                .param("username", "f899139df5e1059396431415e770c6dd")
                .param("password","f899139df5e1059396431415e770c6dd")
//                .param("newFileName","1.txt")
                        .session(session)
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testControllerTestVueIdea() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                        .get("/testVueIdea")
                        // 设置返回类型
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        // 添加请求参数
//                .param("username", "101")
//                .param("password","101")
//                .param("newFileName","1.txt")
                        .session(session)
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testControllerGetTracks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                        .post("/getTracks")
                        // 设置返回类型
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        // 添加请求参数
                .param("s", "3.txt")
//                .param("password","101")
//                .param("newFileName","1.txt")
                        .session(session)
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testControllerUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file11", "filename.txt", "text/plain", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders
                        // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                        .fileUpload("/uploadFile")
                        .file(file )
                        // 设置返回类型
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        // 添加请求参数
//                        .param("s", "3.txt")
//                .param("password","101")
//                .param("newFileName","1.txt")
                        .session(session)
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testControllerGetfileList1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                        .get("/getFileList")
                        // 设置返回类型
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        // 添加请求参数
//                        .param("s", "3.txt")
//                .param("password","101")
//                .param("newFileName","1.txt")
                        .session(session)
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testControllerDeleteFile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        // MockMvcRequestBuilders.get("XXX")代表get请求指定路径xxx
                        .get("/deleteFile")
                        // 设置返回类型
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        // 添加请求参数
                        .param("fileId", "132")
//                .param("password","101")
                .param("fileName","2路U.txt")
                        .session(session)
        )		//ResultActions.andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello zxd, I'm SpringBoot!"))
                //添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());
    }






















    @Test
    public void testInsert() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date parse = sdf.parse("2022/05/12 08:25:34");

    }

    @Test
    public void testPointFormat() {
        System.out.println("---------------------高德API坐标转化");
        System.out.println(GetLocations.gpsToGCJ_02(122.01912064, 36.97526));
    }

    @Test
    public void testFinalRe() {
        final int TEMP = 0;
        System.out.println(TEMP);
        Map<String, String> map = new LinkedHashMap<String, String>();
    }
}

