package com.example.slimdown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.slimdown.Class.Users
import com.example.slimdown.Class.Weights
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_progress.*

class ProgressActivity : AppCompatActivity() {

    private lateinit var adapter : RowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        getWeight()

        addImageView.setOnClickListener {
            showAddProgress()
        }

        adapter = RowsAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        FirebaseServices().getWeightById(FirebaseAuth.getInstance().currentUser!!.uid, object:
            FirebaseServices.IResult<List<Weights>> {

            override fun onSuccess(items: List<Weights>?) {
                if(items != null) {
                    adapter.getData(items)
                }
            }

            override fun onError(exception: java.lang.Exception) {

            }
        })
    }


    private fun getWeight() {

        FirebaseServices().getUserById(FirebaseAuth.getInstance().currentUser!!.uid, object :
            FirebaseServices.IResult<Users> {
            override fun onSuccess(items: Users?) {
                originalWeightTextView.text = items?.actualWeight.toString()
                targetWeightTextView.text = items?.lostWeight.toString()
                remainingWeightTextView.text = (items?.actualWeight?.minus(items?.lostWeight!!)).toString()

            }

            override fun onError(exception: Exception) {

            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.log_out_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logOutButton -> logOut()
        }

        return super.onOptionsItemSelected(item)
    }

    fun logOut() {
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(this, LoginActivity::class.java).apply {  }
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.moveTaskToBack(true)
    }

    private fun showAddProgress()
    {
        val intent = Intent(this, AddProgressActivity::class.java).apply { }
        startActivity(intent)
    }
}
