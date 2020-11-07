package com.example.app.entity

import android.text.TextUtils
import com.example.app.BuildConfig
import com.example.app.utils.TimeUtils
import org.json.JSONArray
import org.json.JSONException
import java.util.*

/**
 * @author WhenYoung
 *  CreateAt Time 2020/10/13  16:23
 */
class DcLoginUser {
    interface RoleType {
        companion object {
            const val BUYER = "buyer"
            const val SELLER = "seller"
        }
    }

    var id:String?=null
    var mobilePhoneNumber :String? = null
    var nickName :String? = null
    var avatarUrl :String? = null
    var lcId :String? = null
    var vipValidity : Date? = null

    var role : String? = null
    get() {
        if (TextUtils.isEmpty(field)) {
            role = RoleType.BUYER
        }
        return role
    }
    var deviceId :String? = null
    var version :String? = null
    var rankImageUrl :String? = null
    var imToken :String? = null
    var credits :Int? = null
    var regions :List<String>? = null
    var channels :List<String>? = null
    var authData :Map<String,Map<String,String>>? = null
    var sessionToken:String?=null

    fun isVip(): Boolean {
        return null != vipValidity && vipValidity?.compareTo(Date())?:0 > 0
    }

    fun getVipStr(): String? {
        if (null == vipValidity) {
            return "非会员"
        }
        return if (!isVip()) {
            "会员已过期"
        } else String.format("会员有效期至%1$1s", TimeUtils.formatTimestamp(vipValidity?.time ?:0))
    }

    fun isSmsAuthenticated(): Boolean {
//        return isLinked(AccountConstant.AuthType.SMS)
        return false
    }

    private fun isLinked(authType:String):Boolean{
        return authData?.containsKey(authType) ?: false
    }

    fun getDescription(): String? {
        return String.format(
                "id: %s, 手机号: %s, 设备id: %s, 会员有效期: %s, 订阅频道: %s, 订阅地区: %s, app版本: %s",
                id,
                mobilePhoneNumber ?: "",
                deviceId ?: "",
                vipValidity ?: "",
                channels ?: "",
                regions ?: "",
                "android" + BuildConfig.VERSION_NAME
        )
    }


}