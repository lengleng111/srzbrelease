package org.jeecg.modules.srzb.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.srzb.employee.dao.entity.SrEmployeeEntity;
import org.jeecg.modules.srzb.employee.mapper.SrEmployeeMapper;
import org.jeecg.modules.srzb.shopgoods.dao.entity.SrShopGoosImageEntity;
import org.jeecg.modules.srzb.user.dao.dto.RegisterDto;
import org.jeecg.modules.srzb.user.dao.entity.SrUserEntity;
import org.jeecg.modules.srzb.user.dao.entity.SrWxToPersonEntity;
import org.jeecg.modules.srzb.user.mapper.SrUserMapper;
import org.jeecg.modules.srzb.user.mapper.SrWxToPersonMapper;
import org.jeecg.modules.srzb.user.service.ISrWxToPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * 微信和人员信息关联表 服务实现类
 * </p>
 *
 * @author LZQ
 * @since 2022-03-26
 */
@Service
@Slf4j
public class SrWxToPersonServiceImpl extends ServiceImpl<SrWxToPersonMapper, SrWxToPersonEntity> implements ISrWxToPersonService {
    @Autowired
    private  SrUserMapper  srUserMapper;
    @Autowired
    private SrEmployeeMapper srEmployeeMapper;
    @Autowired
    private SrWxToPersonMapper srWxToPersonMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public Result<?> getUnionId(String code) {
        try {
//            String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
//            Map<String, String> requestUrlParam = new HashMap<String, String>();
//            requestUrlParam.put("appid", "");  //开发者设置中的appId
//            requestUrlParam.put("secret", ""); //开发者设置中的appSecret
//            requestUrlParam.put("js_code", code); //小程序调用wx.login返回的code
//            requestUrlParam.put("grant_type", "authorization_code");    //默认参数 authorization_code
//            //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
//            log.info("请求微信获取unionid入参："+requestUrlParam);
//            JSONObject result = JSONObject.parseObject(sendPost(requestUrl, requestUrlParam));
//            log.info("请求微信获取unionid出参："+result);
//            if (result.containsKey("errcode")) {
//                return Result.error("远程调用微信小程序API出错：" + result.getString("errcode") + result.getString("errmsg"));
//            }
            JSONObject result=new JSONObject();
            result.put("unionid","oKrhM1ffArQYosmiW_j75-b0iff8");
            //取出返参中的openId
            String unionId = result.getString("unionid");
            String sessionKey = result.getString("session_key");
            LambdaQueryWrapper<SrWxToPersonEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SrWxToPersonEntity::getUnionId,unionId);
            SrWxToPersonEntity srWxToPersonEntity=this.getOne(wrapper);
            if (srWxToPersonEntity == null){
                result.put("flag",3);
                return Result.OK(result);
            }
            String idcard = srWxToPersonEntity.getIdcard();
            LambdaQueryWrapper<SrUserEntity> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(SrUserEntity::getIdcard,idcard);
            SrUserEntity srUserEntity=srUserMapper.selectOne(wrapper2);
            if (srUserEntity != null){
                result.put("flag",2);
                result.put("srUserEntity",srUserEntity);
                return Result.OK(result);
            }else{
                LambdaQueryWrapper<SrEmployeeEntity> wrapper3 = new LambdaQueryWrapper<>();
                wrapper3.eq(SrEmployeeEntity::getIdcard,idcard);
                SrEmployeeEntity srEmployeeEntity=srEmployeeMapper.selectOne(wrapper3);
                if (srEmployeeEntity != null){
                    result.put("flag",1);
                    result.put("srEmployeeEntity",srEmployeeEntity);
                    return Result.OK(result);
                }
            }
            return null;
        }catch (Exception e){
            log.info("getUnionId：",e.getMessage());
            e.printStackTrace();
            return Result.error("系统错误"+e.getMessage());
        }
    }

    @Override
    public Result<?> getUserInfo(String idCardNo) {
        try {
            JSONObject result=new JSONObject();
            LambdaQueryWrapper<SrUserEntity> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(SrUserEntity::getIdcard,idCardNo);
            SrUserEntity srUserEntity=srUserMapper.selectOne(wrapper2);
            if (srUserEntity != null){
                result.put("flag",2);
                result.put("srUserEntity",srUserEntity);
                return Result.OK(result);
            }else {
                LambdaQueryWrapper<SrEmployeeEntity> wrapper3 = new LambdaQueryWrapper<>();
                wrapper3.eq(SrEmployeeEntity::getIdcard,idCardNo);
                SrEmployeeEntity srEmployeeEntity=srEmployeeMapper.selectOne(wrapper3);
                if (srEmployeeEntity != null){
                    result.put("flag",1);
                    result.put("srEmployeeEntity",srEmployeeEntity);
                    return Result.OK(result);
                }else {
                    result.put("flag",3);
                    return Result.OK(result);
                }
            }
        }catch (Exception e){
            log.info("getUserInfo：",e.getMessage());
            e.printStackTrace();
            return Result.error("系统错误"+e.getMessage());
        }
    }

    @Override
    public Result<?> saveRegister(RegisterDto registerDto) {
        try {
            LambdaQueryWrapper<SrWxToPersonEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SrWxToPersonEntity::getUnionId,registerDto.getUnionId());
            SrWxToPersonEntity srWxToPersonEntity=this.getOne(wrapper);
            if (srWxToPersonEntity == null){
                SrWxToPersonEntity srWxToPersonEntity1=new SrWxToPersonEntity();
                srWxToPersonEntity1.setIdcard(registerDto.getIdcard());
                srWxToPersonEntity1.setUnionId(registerDto.getUnionId());
                srWxToPersonEntity1.setCreateTime(new Date());
                srWxToPersonEntity1.setUpdateTime(new Date());
                srWxToPersonEntity1.setCreateBy("gfd");
                srWxToPersonEntity1.setUpdateBy("fdgs");
                srWxToPersonMapper.insert(srWxToPersonEntity1);
            }
            if (registerDto.getFlag().equals("1")){
                LambdaQueryWrapper<SrEmployeeEntity> wrapper3 = new LambdaQueryWrapper<>();
                wrapper3.eq(SrEmployeeEntity::getIdcard,registerDto.getIdcard());
                SrEmployeeEntity srEmployeeEntity=srEmployeeMapper.selectOne(wrapper3);
                if (srEmployeeEntity !=null){
                    srEmployeeEntity.setIdcard(registerDto.getIdcard());
                    srEmployeeEntity.setPhone(registerDto.getPhone());
                    srEmployeeMapper.updateById(srEmployeeEntity);
                }else {
                    return Result.error(599,"未查询到该内部员工信息。");
                }
            }else{

                SrUserEntity srUserEntity=new SrUserEntity();
                srUserEntity.setIdcard(registerDto.getIdcard());
                srUserEntity.setPhone(registerDto.getPhone());
                srUserEntity.setUserName(registerDto.getUserName());
                srUserEntity.setAge((long) 22);
                srUserEntity.setBirthday(new Date());
                srUserEntity.setCardtype("1");
                srUserEntity.setLevel("1");
                srUserEntity.setCreateBy("fd");
                srUserEntity.setUpdateBy("gfd");
                srUserEntity.setCreateTime(new Date());
                srUserEntity.setUpdateTime(new Date());
                srUserEntity.setSex("女");
                srUserMapper.insert(srUserEntity);
            }
            return Result.OK();
        }catch (Exception e){
            log.info("saveRegister：",e.getMessage());
            e.printStackTrace();
            return Result.error("系统错误"+e.getMessage());
        }
    }

    @Override
    public Result<?> updateInfo(RegisterDto registerDto) {
        try {
            if (registerDto.getFlag().equals("1")){
                LambdaQueryWrapper<SrEmployeeEntity> wrapper3 = new LambdaQueryWrapper<>();
                wrapper3.eq(SrEmployeeEntity::getIdcard,registerDto.getIdcard());
                SrEmployeeEntity srEmployeeEntity=srEmployeeMapper.selectOne(wrapper3);
                srEmployeeEntity.setPhone(registerDto.getPhone());
                srEmployeeMapper.updateById(srEmployeeEntity);
            }else{
                LambdaQueryWrapper<SrUserEntity> wrapper2 = new LambdaQueryWrapper<>();
                wrapper2.eq(SrUserEntity::getIdcard,registerDto.getIdcard());
                SrUserEntity srUserEntity=srUserMapper.selectOne(wrapper2);
                srUserEntity.setPhone(registerDto.getPhone());
                srUserMapper.updateById(srUserEntity);
            }
            return Result.OK();
        }catch (Exception e){
            log.info("updateInfo：",e.getMessage());
            e.printStackTrace();
            return Result.error("系统错误"+e.getMessage());
        }
    }

    @Override
    public Result<JSONObject> wxLogin(String unionId) {
        Result<JSONObject> result = new Result<JSONObject>();
        try {

            LambdaQueryWrapper<SrWxToPersonEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SrWxToPersonEntity::getUnionId,unionId);
            SrWxToPersonEntity srWxToPersonEntity=this.getOne(wrapper);
            if (srWxToPersonEntity == null){
                return Result.error(599,"未查询到该unionid相关信息");
            }
            String idcard = srWxToPersonEntity.getIdcard();
            LambdaQueryWrapper<SrUserEntity> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(SrUserEntity::getIdcard,idcard);
            SrUserEntity srUserEntity=srUserMapper.selectOne(wrapper2);
            if (srUserEntity == null){
                LambdaQueryWrapper<SrEmployeeEntity> wrapper3 = new LambdaQueryWrapper<>();
                wrapper3.eq(SrEmployeeEntity::getIdcard,idcard);
                SrEmployeeEntity srEmployeeEntity=srEmployeeMapper.selectOne(wrapper3);
                result=srygInfo(srEmployeeEntity,result);
            }else{
                result=srgkInfo(srUserEntity,result);
            }
            return result;
        }catch (Exception e){
            log.info("wxLogin：",e.getMessage());
            e.printStackTrace();
            return Result.error("系统错误"+e.getMessage());
        }
    }
    /**
     * @Author fuwang
     * @Description 利用内部人员的id和前缀生成token
     * @param
     * @Return
     * @Date 2021/12/7 16:29
     */
    private Result<JSONObject> srgkInfo(SrUserEntity srUserEntity, Result<JSONObject> result) {
        String userId = srUserEntity.getUserId().toString();
        String userType ="SRGK";
        JSONObject obj = new JSONObject();
        // 生成token
        String token = JwtUtil.newsign(userId, userType);
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
        obj.put("token", token);
        obj.put("userInfo", srUserEntity);
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }
    /**
     * @Author fuwang
     * @Description 利用外来人员的id和前缀生成token
     * @param
     * @Return
     * @Date 2021/12/7 16:28
     */
    private Result<JSONObject> srygInfo(SrEmployeeEntity srEmployeeEntity, Result<JSONObject> result) {
        String userId = srEmployeeEntity.getId().toString();
        String userType ="SRYG";
        JSONObject obj = new JSONObject();
        // 生成token
        String token = JwtUtil.newsign(userId, userType);
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
        obj.put("token", token);
        obj.put("userInfo", srEmployeeEntity);
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }
    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public String sendPost(String url, Map<String, ?> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        String param = "";
        Iterator<String> it = paramMap.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            param += key + "=" + paramMap.get(key) + "&";
        }

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            // logger.error(e.getMessage(), e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
