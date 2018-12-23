package com.example.framgianguyenvanthanhd.music_professional.screens.more_setting

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioGroup
import com.example.framgianguyenvanthanhd.music_professional.App
import com.example.framgianguyenvanthanhd.music_professional.MainActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.data.model.Setting
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SettingRepository
import com.example.framgianguyenvanthanhd.music_professional.data.resources.local.SettingLocalDataSource
import com.example.framgianguyenvanthanhd.music_professional.screens.BaseFragment
import com.example.framgianguyenvanthanhd.music_professional.service.RepeatType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_more_setting.*
import kotlinx.android.synthetic.main.fragment_personal.*

/**
 * Created by admin on 12/23/2018.
 */
class MoreSettingFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_more_setting, container, false)
    }

    override fun onResume() {
        super.onResume()
        val settingRepo = SettingRepository.getInstance(SettingLocalDataSource.getInstance(context))
        val setting = settingRepo.setting

        sw_shuffle.isChecked = setting.isShuffleMode

        when (setting.repeatMode) {
            RepeatType.NO_REPEAT -> {
                rd_repeat_none.isChecked = true
            }
            RepeatType.REPEAT_ONE -> {
                rd_repeat_one.isChecked = true
            }
            RepeatType.REPEAT_ALL -> {
                rd_repeat_all.isChecked = true
            }
        }

        sw_shuffle.setOnCheckedChangeListener { _, state ->
            setting.isShuffleMode = state
            settingRepo.saveSetting(setting)
        }

        setting_repeat.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.rd_repeat_none -> {
                    setting.repeatMode = RepeatType.NO_REPEAT
                    settingRepo.saveSetting(setting)
                }
                R.id.rd_repeat_one -> {
                    setting.repeatMode = RepeatType.REPEAT_ONE
                    settingRepo.saveSetting(setting)
                }
                R.id.rd_repeat_all -> {
                    setting.repeatMode = RepeatType.REPEAT_ALL
                    settingRepo.saveSetting(setting)
                }
            }
        }
    }

    override fun initiateView() {
        val mainActivity = activity as MainActivity
        mainActivity.isDisplayBottomNavigation(true)
        mainActivity.isDisplayToolbar(true)
        mainActivity.updateToolbar(true)
        val fistName = SharedPrefs.getInstance().get(KeysPref.FIRST_NAME.name, String::class.java)
        val lastName = SharedPrefs.getInstance().get(KeysPref.LAST_NAME.name, String::class.java)
        val avatar = SharedPrefs.getInstance().get(KeysPref.AVATAR.name, String::class.java)
        if (fistName.isNotEmpty() && lastName.isNotEmpty()) {
            txt_account_name_setting.text =  fistName + lastName
        } else if (SharedPrefs.getInstance().get(KeysPref.USER_NAME.name, String::class.java).isNotEmpty()) {
            txt_account_name_setting.text = SharedPrefs.getInstance().get(KeysPref.USER_NAME.name, String::class.java)
        }
        if (avatar.isNotEmpty()) {
            Picasso.with(activity).load(avatar).into(account_avatar_setting)
        }

        txt_version.text = getAppVersion()
    }

    fun getAppVersion(): String? {
        val manager = App.getContext().packageManager
        val info: PackageInfo
        return try {
            info = manager.getPackageInfo(
                    App.getContext().packageName, 0)
            info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }
}