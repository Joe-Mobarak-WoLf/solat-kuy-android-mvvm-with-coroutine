package com.programmergabut.solatkuy.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.programmergabut.solatkuy.data.Repository
import com.programmergabut.solatkuy.data.local.localentity.MsSetting

class MainActivityViewModel(application: Application, repository: Repository) : AndroidViewModel(application){

    val msSetting: LiveData<MsSetting> = repository.getMsSetting()
    private var repository: Repository? = null

    /* fun updateMsApi1(msApi1: MsApi1) = viewModelScope.launch {
        repository?.updateMsApi1(msApi1, this)
    } */

    /* fun updateSetting(msSetting: MsSetting) = viewModelScope.launch {
        repository?.updateMsSetting(msSetting.isHasOpenApp, this)
    } */

}