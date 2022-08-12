package com.wwnt.web;


import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.wwnt.entity.*;
import com.wwnt.repository.GpsFileRepository;

import com.wwnt.repository.PointRepository;
import com.wwnt.repository.UserRepository;
import com.wwnt.service.DisplayTracksService;
import com.wwnt.utils.CodeUtil;
import com.wwnt.utils.DisplayTracksConfig;
import com.wwnt.utils.calcuPointDistance;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.wwnt.entity.User;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dev3 on 28/12/2017.
 */
@Controller
@CrossOrigin
public class DisplayTracksController {
    private static final Logger logger = LoggerFactory.getLogger(DisplayTracksController.class);
    /*@Autowired
    GcjTracksRepository gcjTracksRepository;*/

    @Autowired
    DisplayTracksConfig displayTracksConfig;
    @Autowired
    GpsFileRepository gpsFileRepository;
    @Autowired
    DisplayTracksService displayTracksService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private Producer captchaProducer = null;
    @Autowired
    PointRepository pointRepository;

    @Autowired
    UserFile userFile;


    @RequestMapping("/kaptcha")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成验证码
        String capText = captchaProducer.createText();
        System.out.println(capText);
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //向客户端写出
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @RequestMapping("/jst")
    public String toT1() {
        return "/justify";
    }

    /**
     * @param id
     * @param fileName
     * @param newFileName
     * @param session
     * @return success成功 error名字为空 fail名字存在
     */
    @GetMapping("/updateName")
    @ResponseBody
    public String updateName(@RequestParam("fileId") Integer id, @RequestParam("fileName") String fileName, @RequestParam("newFileName") String newFileName, HttpSession session) {
        String username = (String) session.getAttribute("username");
//        String username = "81dc9bdb52d04dc20036dbd8313ed055";
        System.out.println("=========================");
        System.out.println(username);
        System.out.println("=========================");
        if ("".equals(newFileName) || newFileName == null) return "error";//名字为空
        else {
            newFileName = newFileName + ".txt";
            System.out.println(newFileName);
            List fileList = gpsFileRepository.selectTracksNameByUsername(username);
            System.out.println("updatename");
            if (fileList.contains(newFileName)) return "fail";//已有文件名
            else {
                gpsFileRepository.updateFileName(newFileName, id);
                File file = new File(displayTracksConfig.getFilePathOfTrackData() +"\\"+ username + "\\" + fileName);
                file.renameTo(new File(displayTracksConfig.getFilePathOfTrackData() +"\\"+ username + "\\" + newFileName));

                int i = pointRepository.updateFileNameByFileName(fileName, newFileName);
                System.out.println(i);
                return "success";
            }
        }

    }

    /**
     * 测试删除用户
     * @param session
     * @return JSONObject（‘status’,'success'）
     */
    @Transactional
    @GetMapping("/delUser1")
    @ResponseBody
    public JSONObject testDelUser(@RequestParam("userId") Integer id,HttpSession session) {//完善前端相应
        String username = userRepository.selectUserNameById(id);
        System.out.println(username);
        JSONObject resultObject = new JSONObject();

        //int i = gpsFileRepository.deleteUser(username);
        System.out.println("=============");
        int j = userRepository.deleteUserById(id);
        if (j <= 0) {
            resultObject.put("status", "failure");
            return resultObject;
        }
        logger.info("--------清除了数据库中的记录");

        System.out.println("删除了用户名为" + username + "的用户");
        resultObject.put("status", "success");
        return resultObject;
    }

    /**
     * 删除用户
     *
     * @param id
     * @param session
     * @return JSONObject（‘status’,'success'）
     */
    @Transactional
    @GetMapping("/delUser")
    @ResponseBody
    public JSONObject delUser(@RequestParam("userId") Integer id, HttpSession session) {//完善前端相应
//        String username = (String) session.getAttribute("username");
        String username = userRepository.selectUserNameById(id);
        System.out.println(username);
        JSONObject resultObject = new JSONObject();

        int i = gpsFileRepository.deleteUser(username);
        System.out.println("=============");
        int j = userRepository.deleteUserById(id);
        if (i <= 0 || j <= 0) {
            resultObject.put("status", "failure");
            return resultObject;
        }
        logger.info("--------清除了数据库中的记录");
        List ListOfFile = gpsFileRepository.selectTracksNameByUsername(username);
        System.out.println(ListOfFile.size());
        for (int k = 0; i < ListOfFile.size(); k++) {
            //for (String s : ListOfFile) {
            String fileName = (String) ListOfFile.get(k);
            System.out.println(displayTracksConfig.getFilePathOfTrackData() + username + "//" + fileName);
            File file = new File(displayTracksConfig.getFilePathOfTrackData() + username + "//" + fileName);
            if (file.exists()) {
                file.delete();
                logger.info("--------删除了文件");
            }
            displayTracksService.deleteGcj02ListFromCache(fileName);

        }
        System.out.println("删除了用户名为" + username + "的用户");
        resultObject.put("status", "success");
        return resultObject;
    }

    /**
     * 用户列表密码
     *
     * @return user's list
     */
    @GetMapping("/element")
    @ResponseBody
    public List<User> list1() {//完善前端相应
        return userRepository.list();
    }


    /**
     * 不允许注册
     *
     * @return success
     */
    @GetMapping("/stopReg")
    @ResponseBody
    public String stopReg() {
//        mv.addObject("userData",userRepository.list());
//        map.put("userData",userRepository.list());
        userFile.setFlag(0);
        System.out.println("阻止注册");
        return "success";
    }

    /**
     * 允许注册
     *
     * @return success
     */
    @GetMapping("/allowReg")
    @ResponseBody
    public String allowReg() {
//        mv.addObject("userData",userRepository.list());
//        map.put("userData",userRepository.list());
        userFile.setFlag(1);
        System.out.println("允许注册");
        return "success";
    }

    /**
     * @param username
     * @param passsword
     * @param httpSession
     * @return success 登陆成功 fail 登陆失败
     */
    @GetMapping("/log")
    @ResponseBody
    public String login1(@RequestParam("username") String username, @RequestParam("password") String passsword, HttpSession httpSession) {
        String newPassWord = DigestUtils.md5DigestAsHex(passsword.getBytes(StandardCharsets.UTF_8));
        String newUserName = DigestUtils.md5DigestAsHex(username.getBytes(StandardCharsets.UTF_8));

        User user = userRepository.selectByUsernameandPassword(newUserName, newPassWord);//找user存user
        if (user != null) {
            httpSession.setAttribute("username", newUserName);
            System.out.println("存user到session");
            httpSession.getAttribute("username");
            System.out.println(newUserName);
            return "success";
        }
        return "fail";
    }

    /**
     * @param username  user's name
     * @param passsword user's password, encrypted
     * @return int 0 注册成功 2 验证码错误 111不允许注册 else（1） 用户名已存在
     * <p>
     * <p>
     * This method verifies if user exists and registers if not
     */
    @GetMapping("/reg")
    @ResponseBody
    public int register1(@RequestParam("username") String username, @RequestParam("password") String passsword, @RequestParam("verifyCodeActual") String verifyCodeActual, HttpServletRequest request) {
        //调用工具类判断验证码的正确性
        if (!CodeUtil.checkVerifyCode(request, verifyCodeActual)) {
            return 2;
        }
        int flag = userFile.getFlag(); // flag is right to ....
        if (flag != 0) {
            String newPassWord = DigestUtils.md5DigestAsHex(passsword.getBytes(StandardCharsets.UTF_8));
            String newUserName = DigestUtils.md5DigestAsHex(username.getBytes(StandardCharsets.UTF_8));
            int i = userRepository.selectByusername(newUserName);//找相同用户名的用户
            if (i == 0) {
                userRepository.save(new User(newUserName, newPassWord, username));//存user
//                System.out.println("用户已存在");
            }
            return i;
        } else return 111;
    }

    @GetMapping("/testVueIdea")
    @ResponseBody
    public String testVueIdea() {
        return "连接到idea";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/bootstrap")
    public String bootstrap() {
        return "bootstrap";
    }

    @GetMapping("/dropdown")
    public String dropdown() {
        return "dropdown";
    }

    @GetMapping("/testInput")
    public String testInput() {
        return "testInput";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 获取filename的轨迹信息
     *
     * @param fileName
     * @param session
     * @return JSONArray： [{name:[filename]},{time:[]},{path:[经纬度]},{speed:[]}]
     * @throws ParseException
     */
    @PostMapping("/getTracks")
    @ResponseBody
    public JSONArray getTracks(@RequestParam("s") String fileName, HttpSession session) throws ParseException {
        String username = (String) session.getAttribute("username");
//        String username = "81dc9bdb52d04dc20036dbd8313ed055";
        System.out.println(username);
        System.out.println(fileName);
        JSONArray pointArray = new JSONArray();
//        for (String fileName : fileNames) {
        if (fileName == null || "".equals(fileName)) {
            return null;
        }
        if (!new File(displayTracksConfig.getFilePathOfTrackData()+"\\" + username + "\\" + fileName).exists()) {
//            System.out.println(displayTracksConfig.getFilePathOfTrackData()+"//" + username + "//" + fileName);
            return null;
        }
        TrackData trackData = displayTracksService.getTrackData(session, fileName);
        if (trackData == null) {
            return null;
        }
        Boolean saveToDB = true; //true 要存到数据库 false不需要
        String[] pathArray = new String[trackData.getFormatedLatitudeList().size()];
        String[] timeArray = new String[trackData.getFormatedLatitudeList().size()];
        JSONObject routeObject = new JSONObject();
        StringBuffer stringBuffer;
        StringBuffer originalPointsBuffer;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        int count = 0;//计数器每20个更新一次 20个的平均速度
        double totalDistance = 0.0; //20个点的总路程
        double totalTime = 0.0; //20个点的总时间

        if (pointRepository.selectByfileName(fileName).size() != 0) {//select 为空是拿出“[]”
            saveToDB = false;
        }

        for (int i = 0; i < trackData.getFormatedLatitudeList().size(); i++) {
            stringBuffer = new StringBuffer("[");
            stringBuffer.append(trackData.getFormatedLongitudeList().get(i));
            stringBuffer.append(",");
            stringBuffer.append(trackData.getFormatedLatitudeList().get(i));
            stringBuffer.append("]");
            pathArray[i] = stringBuffer.toString();
            originalPointsBuffer = new StringBuffer("[");
            originalPointsBuffer.append(trackData.getLongitudeList().get(i));
            originalPointsBuffer.append(",");
            originalPointsBuffer.append(trackData.getLatitudeList().get(i));
            originalPointsBuffer.append("]");

            //originalPointsArray[i] = originalPointsBuffer.toString();
            if (trackData.getTimeList().size() > 0) {
                if (i >= 1) {
                    Date time1 = sdf.parse(trackData.getTimeList().get(i));

                    double distance = calcuPointDistance.calculateLineDistance(
                            trackData.getFormatedLongitudeList().get(i - 1),
                            trackData.getFormatedLatitudeList().get(i - 1),
                            trackData.getFormatedLongitudeList().get(i),
                            trackData.getFormatedLatitudeList().get(i));
                    Date time2 = sdf.parse(trackData.getTimeList().get(i - 1));
                    double diffTime = (time1.getTime() - time2.getTime()) / 1000 % 60;
                    totalDistance += distance;
                    totalTime += diffTime;
                    double speed = 1.0;
                    if (diffTime != 0)
                        speed = Math.abs(distance / diffTime) * 3.6;
                    if (saveToDB) {
                        count++;
                    }
                    if (count % 20 == 0 && count >= 20) {
                        Double averageSpeed = Math.abs(totalDistance / totalTime) * 3.6;
                        int maxId = pointRepository.selsectMaxId();
                        for (int j = 20; j >= 0; j--) {
                            if (averageSpeed == 0.0)
                                averageSpeed = 1.0;
                            pointRepository.save(new postionPoint(
                                    trackData.getFormatedLongitudeList().get(i),
                                    trackData.getFormatedLatitudeList().get(i),
                                    fileName, username, time1, averageSpeed
                            ));
                        }
                        totalDistance = 0.0;
                        totalTime = 0.0;
                    }

                } else {
                    double speed = 1.0;
                    if (saveToDB) {
                        Date time1 = sdf.parse(trackData.getTimeList().get(i));
                        pointRepository.save(new postionPoint(
                                trackData.getFormatedLongitudeList().get(i),
                                trackData.getFormatedLatitudeList().get(i),
                                fileName, username, time1, speed
                        ));
                        count++;
                    }
                }
                timeArray[i] = trackData.getTimeList().get(i);
            } else {
                timeArray[i] = "";
                if (saveToDB) {
                    Date timeNow = new Date();
                    String time3 = sdf.format(timeNow);
                    Date time4 = sdf.parse(time3);
                    pointRepository.save(new postionPoint(
                            trackData.getFormatedLongitudeList().get(i),
                            trackData.getFormatedLatitudeList().get(i),
                            fileName, username, time4, 10.0
                    ));
                    count++;
                }
            }


        }
        int dataLength = trackData.getFormatedLatitudeList().size();
        if (totalTime != 0) {
            double averageSpeed = Math.abs(totalDistance / totalTime) * 3.6;//剩余不足20个的平均速度
            for (int j = 1; j <= 20; j++) {
                Date time5 = sdf.parse(trackData.getTimeList().get(dataLength - j));
                pointRepository.save(new postionPoint(
                        trackData.getFormatedLongitudeList().get(dataLength - j),
                        trackData.getFormatedLatitudeList().get(dataLength - j),
                        fileName, username, time5, averageSpeed
                ));
            }
        }
        if (trackData.getTimeList().size() <= 0 && saveToDB) {
            for (int j = 1; j <= 20; j++) {
                Date timeNow = new Date();
                String time3 = sdf.format(timeNow);
                Date time4 = sdf.parse(time3);
                pointRepository.save(new postionPoint(
                        trackData.getFormatedLongitudeList().get(dataLength - j),
                        trackData.getFormatedLatitudeList().get(dataLength - j),
                        fileName, username, time4, 10.0
                ));
            }
        }
        routeObject.put("name", fileName);
        routeObject.put("time", timeArray);
        routeObject.put("path", pathArray);
        //routeObject.put("originalPoint", originalPointsArray);
        routeObject.put("speed", pointRepository.selsectSpeedByFileName(fileName));
        pointArray.add(routeObject);
        System.out.println(fileName + "被调用===================");
//        }
        return pointArray;
    }

    /**
     * upload file
     *
     * @param file
     * @param session
     * @return int msg 0empty file  1 file exists  2 success
     * @throws ParseException
     */
    @PostMapping(value = {"/uploadFile"}, consumes = {"multipart/form-data"})
    @ResponseBody
    public String uploadFile(@RequestParam("file11") MultipartFile file, HttpSession session) throws ParseException {//这里接收的file是input type=file的 name属性
        String msg = "";
        if (null == file && "".equals(file)) {
            msg = "0";//上传文件失败
            return msg;
        }
        String username = (String) session.getAttribute("username");
//        String username = "81dc9bdb52d04dc20036dbd8313ed055";
        String path = displayTracksConfig.getFilePathOfTrackData();
        String fileName = file.getOriginalFilename();//文件名
        String filePathAndName = path +"\\" +username + "\\" + fileName;
        System.out.println(filePathAndName);

        File oldFile = new File(filePathAndName);
        if (oldFile.exists()) {
            msg = "1";//上传的文件已存在
            return msg;
        }
        File targetFile = new File(path +"\\"+ username);
        if (!targetFile.exists()) {//目标文件夹不存在，就创建
            targetFile.mkdirs();
        }
        try {
            file.transferTo(new File(path +"\\"+ username, fileName));
            msg = "2";//文件上传成功
            gpsFileRepository.save(new GpsFile(fileName, new Date(), username));
//            userFileRepository.save(new UserFile(userName,fileName));
            logger.info("--------文件上传成功");
        } catch (Exception e) {
            logger.info(e.toString());
        }
        //displayTracksConfig.setFileNameOfTrackData(fileName);
        return msg;

    }

//    @GetMapping("/index")
//    public ModelAndView getFileList(@RequestParam(value = "pageon", defaultValue = "1") int pageon,
//                                    @RequestParam(value = "refreshFlag", defaultValue = "0") int refreshFlag, ModelAndView mv,
//                                    HttpSession session) {
//        String username = (String) session.getAttribute("username");
//        Map<String, Object> map = new HashMap<>();
//        Page page = new Page(pageon);
//        page.setRowcountAndCompute(gpsFileRepository.getRowCount(username));
//        map.put("paramFlag", "no");
//        map.put("page", page);
//        map.put("fileList", gpsFileRepository.getPageFileList(page.getRow(), page.getStart(), username));
//        map.put("refreshFlag", refreshFlag);
//        /**
//         * 代表首次访问，不是点击页码
//         */
//        if (refreshFlag == 0) {
//            mv.setViewName("fileList");
//        } else {
//            mv.setViewName("tableOfFile");
//        }
//        mv.addAllObjects(map);//要用addAllObjects而不是addObject
//        return mv;
//    }

    /**
     * 获取某用户的文件列表
     *
     * @param session
     * @return RFileList 工具类 RFileList（boolean， List<Gpsfile>） 当boolean为空代表没登陆
     */
    @GetMapping("/getFileList")
    @ResponseBody
    public RFileList getfileList1(HttpSession session) {
        String username = (String) session.getAttribute("username");
//                String username = "81dc9bdb52d04dc20036dbd8313ed055";

        System.out.println("getfilelist"+username);
        if("".equals(username)||username == null)
            return new RFileList(false);
        return new RFileList(true,gpsFileRepository.getFileList(username)) ;
    }

//    @GetMapping("/queryFileListByParam")
//    public ModelAndView queryFileListByParam(@RequestParam(value = "pageon", defaultValue = "1") int pageon,
//                                             @RequestParam(value = "paramType") String paramType,
//                                             @RequestParam(value = "paramInputName") String paramInputName,
//                                             @RequestParam(value = "refreshFlag") int refreshFlag, ModelAndView mv,
//                                             HttpSession session) {
//        String username = (String) session.getAttribute("username");
//        Map<String, Object> map = new HashMap<>();
//        Page page;
//        /**
//         * 说明不是点击页码
//         */
//        if (refreshFlag == 0) {
//            page = new Page(1);
//        } else {
//            page = new Page(pageon);
//        }
//        List<GpsFile> fileList = null;
//        if ("fileName".equals(paramType)) {
//            page.setRowcountAndCompute(gpsFileRepository.getCountByFileName(paramInputName, username));
//            fileList = gpsFileRepository.getFileListByFileName(paramInputName, page.getRow(), page.getStart(), username);
//        } else if ("time".equals(paramType)) {
//            page.setRowcountAndCompute(gpsFileRepository.getCountByTime(paramInputName, username));
//            fileList = gpsFileRepository.getFileListByTime(paramInputName, page.getRow(), page.getStart(), username);
//        }
//        map.put("paramFlag", "yes");
//        map.put("paramType", paramType);
//        map.put("paramInputName", paramInputName);
//        map.put("refreshFlag", refreshFlag);
//        map.put("page", page);
//        map.put("fileList", fileList);
//        if (refreshFlag == 0) {
//            mv.setViewName("fileList");
//        } else {
//            mv.setViewName("tableOfFile");
//        }
//        mv.addAllObjects(map);
//        return mv;
//    }

    /**
     * delete file
     *
     * @param session
     * @param id
     * @param fileName
     * @return JSONObject status: success 成功 failure 失败
     */
    @GetMapping("/deleteFile")
    @ResponseBody
    public JSONObject deleteFile(HttpSession session, @RequestParam(value = "fileId") int id, @RequestParam(value = "fileName") String fileName) {
        String username = (String) session.getAttribute("username");
//                        String username = "81dc9bdb52d04dc20036dbd8313ed055";

        JSONObject resultObject = new JSONObject();
        int result = gpsFileRepository.deleteFileById(id, username);
        pointRepository.deleteByFileName(fileName);
        if (result <= 0) {
            resultObject.put("status", "failure");
            return resultObject;
        }
        logger.info("--------清除了数据库中的记录");
        System.out.println(displayTracksConfig.getFilePathOfTrackData()+"\\" + username +"\\" + fileName);
        File file = new File(displayTracksConfig.getFilePathOfTrackData() +"\\" + username +"\\" + fileName);
        if (file.exists()) {
            file.delete();
            logger.info("--------删除了文件");
        }
        // if(getTracks(fileName)!= null){
        displayTracksService.deleteGcj02ListFromCache(fileName);
        //  }
        resultObject.put("status", "success");
        return resultObject;
    }

    /**
     * 测试ab post
     * @param data
     */
    @PostMapping("/testab")
    @ResponseBody
    public void testAb(@RequestParam("data") String data) {
        System.out.println(data);
    }

//    @GetMapping("/getTrackTable")
//    @ResponseBody
//    public JSONObject getTrackTable(@RequestParam("fileName") String fileName, HttpSession session) {
//        String userName = (String) session.getAttribute("username");
//        if (fileName == null || "".equals(fileName)) {
//            return null;
//        }
//        if (!new File(displayTracksConfig.getFilePathOfTrackData() + userName + "//" + fileName).exists()) {
//            System.out.println(displayTracksConfig.getFilePathOfTrackData() + userName + "//" + fileName);
//            return null;
//        }
//        System.out.println(displayTracksConfig.getFilePathOfTrackData() + userName + "//" + fileName);
//        TrackData trackData = displayTracksService.getTrackData(session, fileName);
//        if (trackData == null) {
//            return null;
//        }
//        JSONObject routeObject = new JSONObject();
//        routeObject.put("cpu", trackData.getCpu());
//        routeObject.put("macAddress", trackData.getMACAddress());
//        routeObject.put("model", trackData.getModel());
//        routeObject.put("board", trackData.getBoard());
//        routeObject.put("hardware", trackData.getHardware());
//        return routeObject;
//    }
}
