package com.example.josuegramajo.kotlintest.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.josuegramajo.kotlintest.R
import com.example.josuegramajo.kotlintest.Fragments.CardViewFragment
import com.example.josuegramajo.kotlintest.Fragments.LandingFragment
import com.example.josuegramajo.kotlintest.Fragments.RecyclerViewFragment
import com.example.josuegramajo.kotlintest.Fragments.ScrollFragment
import com.example.josuegramajo.kotlintest.Utils.FirestoreUtils
import com.google.firebase.auth.*

import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout : DrawerLayout
    lateinit var mAuth:FirebaseAuth

    enum class fragments{
        FRAGMENT_RECYCLER,
        FRAGMENT_CARDVIEW,
        FRAGMENT_SCROLL
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()


        mAuth.currentUser?.let {
            FirestoreUtils().retrieveRols(this, it.uid)
        }

        val toolbar = findViewById(R.id.main_toolbar) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        if(savedInstanceState == null){
            val fragmentMain = LandingFragment()
            val transaction = this.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout,fragmentMain)
            transaction.commit()
        }

        var headerLayout = navigationView.getHeaderView(0)

        val user_email = headerLayout.findViewById(R.id.user_email) as TextView
        user_email.setText(intent.getStringExtra("email"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.options_menu -> toast("Ajsutes!!")
            R.id.options_logout -> {
                mAuth.signOut()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }else {
            val count  = supportFragmentManager.backStackEntryCount
            if(count != 0){
                supportFragmentManager.popBackStack()
            }
        }
    }

    fun isFragmentOpen(value:String) : Boolean{
        val currentFragment = supportFragmentManager.findFragmentByTag(value)
        if(currentFragment != null && currentFragment.isVisible){
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_item1 -> {
                if(!isFragmentOpen(fragments.FRAGMENT_RECYCLER.name)){
                    val recyclerFragment = RecyclerViewFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.frame_layout,recyclerFragment,fragments.FRAGMENT_RECYCLER.name)
                    transaction.addToBackStack("")
                    transaction.commit()
                }
            }
            R.id.nav_item2 -> {
                if(!isFragmentOpen(fragments.FRAGMENT_CARDVIEW.name)){
                    val cardviewFragment = CardViewFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.frame_layout,cardviewFragment,fragments.FRAGMENT_CARDVIEW.name)
                    transaction.addToBackStack("")
                    transaction.commit()
                }
            }
            R.id.nav_item3 -> {
                if(!isFragmentOpen(fragments.FRAGMENT_SCROLL.name)){
                    val bundle = Bundle()
                    val scrollFragment = ScrollFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    bundle.putString("name","DefaultName")
                    bundle.putString("description","")
                    scrollFragment.arguments = bundle
                    transaction.replace(R.id.frame_layout,scrollFragment,fragments.FRAGMENT_SCROLL.name)
                    transaction.addToBackStack("")
                    transaction.commit()
                }
            }
            R.id.nav_item4 -> toast("Item4!!")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}

/*
    //The ugly one
    fun makeAlert(){
        var alert = AlertDialog.Builder(this)

        var view = LinearLayout(this)
        view.orientation = LinearLayout.VERTICAL
        view.setPadding(15,15,15,15)

        var et_title = EditText(this)
        et_title.hint = "Title"
        et_title.setSingleLine(true)

        var et_description = EditText(this)
        et_description.hint = "Description"
        et_description.setSingleLine(true)

        view.addView(et_title)
        view.addView(et_description)

        alert.setTitle("Agregar Actividad")
        alert.setMessage("Escriba la actividad a agregar")
        alert.setView(view)
        alert.setPositiveButton("OK",{
            dialog, which -> addElement(et_title.text.toString(),et_description.text.toString())
        })
        alert.setNegativeButton("Cancel",{
            dialog, which ->  dialog.dismiss()
        })
        alert.show()
    }
*/
