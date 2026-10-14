package com.example.a24012011002_mad_practical_7

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {

        private const val DB_NAME = "ContactDatabase.db"
        private const val DB_VERSION = 1

        private const val TABLE_CONTACT = "contacts"

        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_PHONE = "phoneNo"
        private const val KEY_EMAIL = "emailId"
        private const val KEY_ADDRESS = "address"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTableSQLQuery =
            "CREATE TABLE $TABLE_CONTACT (" +
                    "$KEY_ID TEXT PRIMARY KEY, " +
                    "$KEY_NAME TEXT, " +
                    "$KEY_PHONE TEXT, " +
                    "$KEY_EMAIL TEXT, " +
                    "$KEY_ADDRESS TEXT, " +
                    "$KEY_LATITUDE REAL, " +
                    "$KEY_LONGITUDE REAL" +
                    ")"

        db.execSQL(createTableSQLQuery)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACT")
        onCreate(db)
    }

    fun insertContact(contact: Person) {

        val db = writableDatabase

        val values = ContentValues()

        values.put(KEY_ID, contact.id)
        values.put(KEY_NAME, contact.name)
        values.put(KEY_PHONE, contact.phoneNo)
        values.put(KEY_EMAIL, contact.emailId)
        values.put(KEY_ADDRESS, contact.address)
        values.put(KEY_LATITUDE, contact.latitude)
        values.put(KEY_LONGITUDE, contact.longitude)

        db.insert(TABLE_CONTACT, null, values)

        db.close()
    }

    fun updatePerson(contact: Person) {
        val db = this.writableDatabase

        val values = ContentValues()

        values.put(KEY_NAME, contact.name)
        values.put(KEY_PHONE, contact.phoneNo)
        values.put(KEY_EMAIL, contact.emailId)
        values.put(KEY_ADDRESS, contact.address)
        values.put(KEY_LATITUDE, contact.latitude)
        values.put(KEY_LONGITUDE, contact.longitude)

        db.update(
            TABLE_CONTACT,
            values,
            "$KEY_ID=?",
            arrayOf(contact.id)
        )

        db.close()
    }


    fun getPerson(id: String): Person? {

        val db = readableDatabase

        val cursor = db.query(
            TABLE_CONTACT,
            arrayOf(
                KEY_ID,
                KEY_NAME,
                KEY_PHONE,
                KEY_EMAIL,
                KEY_ADDRESS,
                KEY_LATITUDE,
                KEY_LONGITUDE
            ),
            "$KEY_ID=?",
            arrayOf(id),
            null,
            null,
            null
        )

        if (!cursor.moveToFirst()) {
            cursor.close()
            return null
        }

        val person = Person(
            cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
            cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),
            cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)),
            cursor.getString(cursor.getColumnIndexOrThrow(KEY_ADDRESS)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_LATITUDE)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_LONGITUDE))
        )

        cursor.close()

        return person
    }

    fun getAllPersons(): ArrayList<Person> {

        val personList = ArrayList<Person>()

        val db = readableDatabase

        val cursor = db.query(
            TABLE_CONTACT,
            arrayOf(
                KEY_ID,
                KEY_NAME,
                KEY_PHONE,
                KEY_EMAIL,
                KEY_ADDRESS,
                KEY_LATITUDE,
                KEY_LONGITUDE
            ),
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {

            val person = Person(
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_ADDRESS)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_LATITUDE)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_LONGITUDE))
            )

            personList.add(person)
        }

        cursor.close()

        return personList
    }

    fun deleteContact(id: String) {

        val db = writableDatabase

        db.delete(
            TABLE_CONTACT,
            "$KEY_ID=?",
            arrayOf(id)
        )

        db.close()
    }
}