package com.jormun.mysearchapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    val TAG: String = "SearchViewModel"

    var stringOrginData by mutableStateOf(listOf<String>())
    var stringFilterData by mutableStateOf(listOf<String>())
    var searchKeyWord = ""
    var lastIndex: Int = 0

    init {
    }

    fun addStringData(count: Int) {
        Log.e(TAG, "addStringData: 这是需要添加的数据: $count")
        val newStringData = arrayListOf<String>()
        for (index in lastIndex + 1..count) {
            newStringData.add("数据: $index")
        }
        stringOrginData = stringOrginData + newStringData
        lastIndex = count
        Log.e(TAG, "添加后的列表长度为: ${stringOrginData.size}")
    }

    fun doSearch(keyWord: String): String {
        if (stringOrginData.size == 0) return "无数据可搜索,请先添加数据"
        if (searchKeyWord.isEmpty()) return "请输入关键字"
        val filterList = arrayListOf<String>()
        for (stringDatum in stringOrginData) {
            if (stringDatum.contains(keyWord))
                filterList.add(stringDatum)
        }
        stringFilterData = stringFilterData
            .toMutableList()
            .also {
                it.clear()
            }
        stringFilterData = stringFilterData + filterList
        return "搜索成功!"
    }

    fun saveKeyWord(keyWord: String) {
        searchKeyWord = keyWord
    }


}