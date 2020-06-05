package com.example.fakeretrofitapp.ui

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.fakeretrofitapp.Repository

class ViewModel {
    private val repository: Repository = Repository()

    /*val data : LiveData<AsyncTask.Status> = liveData(Dispatchers.IO) {
        val retrievedData = repository.getData(token)
        emit(retrievedData)
    }*/
}