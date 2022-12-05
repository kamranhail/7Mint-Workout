package com.example.excersiceapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "history-table")
class HistoryEntity(
                 @ColumnInfo( name = "data")   val data: String,
                  @PrimaryKey(autoGenerate = true) val id: Int
            )

{

}