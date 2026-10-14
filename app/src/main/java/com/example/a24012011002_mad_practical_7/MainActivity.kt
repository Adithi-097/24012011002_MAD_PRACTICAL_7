package com.example.a24012011002_mad_practical_7

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val personList = ArrayList<Person>()

    lateinit var db: DatabaseHelper
    lateinit var personRecyleAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        val rv = findViewById<RecyclerView>(R.id.rv_contact)

        db = DatabaseHelper(this)

        personRecyleAdapter = PersonAdapter(personList, db)

        rv.adapter = personRecyleAdapter

        personList.addAll(db.getAllPersons())

        personRecyleAdapter.notifyDataSetChanged()

        findViewById<FloatingActionButton>(R.id.refreshbtn)
            .setOnClickListener {
                networkDb()
            }
    }

    private fun getPersonData(data: String) {

        try {

            val jsonArray = JSONArray(data)

            for (i in 0 until jsonArray.length()) {

                val jsonObject = jsonArray[i] as JSONObject

                val person = Person(jsonObject)

                if (db.getPerson(person.id) == null) {

                    // New person → Insert
                    db.insertContact(person)

                } else {

                    // Existing person → Update
                    db.updatePerson(person)
                }
            }

            // Refresh RecyclerView
            personList.clear()
            personList.addAll(db.getAllPersons())
            personRecyleAdapter.notifyDataSetChanged()

        } catch (e: JSONException) {

            e.printStackTrace()
        }
    }

    override fun onResume() {

        super.onResume()

        if (::db.isInitialized &&
            ::personRecyleAdapter.isInitialized
        ) {

            personList.clear()

            personList.addAll(db.getAllPersons())

            personRecyleAdapter.notifyDataSetChanged()
        }
    }

    fun networkDb() {

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val data = HttpRequest().makeServiceCall(
                    "https://api.json-generator.com/templates/5rDXHcbgpo93/data",
                    "d7wrtfqywyhu7y2bcbsz3cgjpbfisuhnmbibvgvf"
                )

                withContext(Dispatchers.Main) {

                    if (data != null) {
                        getPersonData(data)
                    }
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun clear(
        activity: Activity,
        key: String,
        value: String
    ) {

        val sharedPref =
            activity.getSharedPreferences(
                "app_setting_data",
                Context.MODE_PRIVATE
            )

        val editor = sharedPref.edit()

        editor.clear()

        editor.commit()
    }

    fun getString(
        activity: Activity,
        key: String,
        value: String
    ): String? {

        val sharedPref =
            activity.getSharedPreferences(
                "app_setting_data",
                Context.MODE_PRIVATE
            )

        return sharedPref.getString(key, "")
    }

    fun storeString(
        activity: Activity,
        key: String,
        value: String
    ) {

        val sharedPref =
            activity.getSharedPreferences(
                "app_setting_data",
                Context.MODE_PRIVATE
            )

        val editor = sharedPref.edit()

        editor.putString(key, value)

        editor.commit()
    }
}