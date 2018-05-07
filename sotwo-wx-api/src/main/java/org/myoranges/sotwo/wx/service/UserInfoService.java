package org.myoranges.sotwo.wx.service;

import org.myoranges.sotwo.db.domain.sotwoUser;
import org.myoranges.sotwo.db.service.sotwoUserService;
import org.myoranges.sotwo.wx.dao.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserInfoService {
    @Autowired
    private sotwoUserService userService;


    public UserInfo getInfo(Integer userId) {
        sotwoUser user = userService.findById(userId);
        Assert.state(user != null, "用户不存在");
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatarUrl(user.getAvatar());
        return userInfo;
    }


}
