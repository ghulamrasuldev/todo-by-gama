package com.example.todobygama

object DataObject {
    var listData = mutableListOf<CardInfo>()

    fun setData (title: String, description: String, date: String, time:String, category: String){
        listData.add(CardInfo(title,description,category,date,time))
    }

    fun getAllData(): List<CardInfo>{
        return listData
    }

    fun deleteAll() {
        listData.clear()
    }

    fun getData(pos:Int) : CardInfo{
        return listData[pos]
    }
}