package com.notacompany.myaffairs.data

import android.app.Application
import com.notacompany.myaffairs.data.db.AppDatabase
import com.notacompany.myaffairs.data.repository.DataRepository

class AppApplication: Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { DataRepository(database.projectDao()) }
}