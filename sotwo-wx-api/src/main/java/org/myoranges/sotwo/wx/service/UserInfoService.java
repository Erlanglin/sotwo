package org.myoranges.sotwo.wx.service;

import org.myoranges.sotwo.db.domain.SotwoUser;
import org.myoranges.sotwo.db.service.SotwoUserService;
import org.myoranges.sotwo.wx.dao.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserInfoService {
    @Autowired
    private SotwoUserService userService;


    public UserInfo getInfo(Integer userId) {
        SotwoUser user = userService.findById(userId);
        Assert.state(user != null, "用户不存在");
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatarUrl(user.getAvatar());
        return userInfo;
    }


}
