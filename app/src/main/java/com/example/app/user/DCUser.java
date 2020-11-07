package com.example.app.user;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.example.app.BuildConfig;
import com.example.app.utils.TimeUtils;
import com.parse.ParseClassName;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ParseClassName("_User")
public class DCUser extends ParseUser {

    public interface RoleType {
        String BUYER = "buyer";
        String SELLER = "seller";
    }

    @Nullable
    public static DCUser getCurrentUser() {
        return (DCUser) ParseUser.getCurrentUser();
    }

    public String getMobilePhoneNumber() {
        return getString("mobilePhoneNumber");
    }

    public String getNickName() {
        return getString("nickName");
    }

    public void setNickName(String nickName) {
        put("nickName", nickName);
    }

    public String getAvatarUrl() {
        return getString("avatarUrl");
    }

    public String getLcId() {
        return getString("lcId");
    }


    public Date getVipValidity() {
        return getDate("vipValidity");
    }

    public boolean isVip() {
        return null != getVipValidity() && getVipValidity().compareTo(new Date()) > 0;
    }

    public String getVipStr() {
        if (null == getVipValidity()) {
            return "非会员";
        }
        if (!isVip()) {
            return "会员已过期";
        }
        return String.format("会员有效期至%1$1s", TimeUtils.formatTimestamp(getVipValidity().getTime()));
    }

    public String getRole() {
        String role = getString("role");
        if (TextUtils.isEmpty(role)) {
            role = RoleType.BUYER;
        }
        return role;
    }

    public void setRole(String role) {
        put("role", role);
    }

    public String getDeviceId() {
        return getString("deviceId");
    }

    public void setDeviceId(String deviceId) {
        put("deviceId", deviceId);
    }

    public String getVersion() {
        return getString("version");
    }

    public void setVersion(String version) {
        put("version", version);
    }

    public String getRankImageUrl() {
        return getString("rankImageUrl");
    }

    public String getImToken() {
        return getString("imToken");
    }

    public int getCredits() {
        return getInt("credits");
    }

    public List<String> getRegions() {
        return jsonArrayToList(getJSONArray("regions"));
    }

    public void setRegions(List<String> regionList) {
        put("regions", regionList);
    }


    public List<String> getChannels() {
        return jsonArrayToList(getJSONArray("channels"));
    }

    public void setChannels(List<String> channelList) {
        put("channels", channelList);
    }


    public boolean isSmsAuthenticated() {
        return false;
    }

    public String getDescription() {
        return String.format(
                "id: %s, 手机号: %s, 设备id: %s, 会员有效期: %s, 订阅频道: %s, 订阅地区: %s, app版本: %s",
                getObjectId(),
                null == getMobilePhoneNumber() ? "" : getMobilePhoneNumber(),
                null == getDeviceId() ? "" : getDeviceId(),
                null == getVipValidity() ? "非会员" : getVipValidity().toString(),
                null == getChannels() ? "[]" : getChannels().toString(),
                null == getRegions() ? "[]" : getRegions().toString(),
                "android" + BuildConfig.VERSION_NAME
        );
    }

    private List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> listData = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    listData.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return listData;
    }
}
