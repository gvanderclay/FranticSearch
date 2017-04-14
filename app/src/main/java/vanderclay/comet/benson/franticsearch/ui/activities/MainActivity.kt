package vanderclay.comet.benson.franticsearch.ui.activities

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.toolbar.*
import vanderclay.comet.benson.franticsearch.ui.fragments.CardSearchFragment
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.commons.SetCache

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var mDrawer: DrawerLayout? = null
    var nvDrawer: NavigationView? = null
    var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        mDrawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        nvDrawer = findViewById(R.id.nvView) as NavigationView?
        drawerToggle = setUpDrawerToggle()
        mDrawer?.addDrawerListener(drawerToggle as DrawerLayout.DrawerListener)

        setupDrawerContent(nvDrawer)
        supportFragmentManager.beginTransaction().replace(R.id.flContent,
                CardSearchFragment.newInstance()).commit()

        title = getString(R.string.card_search)
    }

    private fun setUpDrawerToggle(): ActionBarDrawerToggle {
        return ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close)
    }

    private fun setupDrawerContent(navigationView: NavigationView?) {
        navigationView?.setNavigationItemSelectedListener {
            selectDrawerItem(it)
            true
        }
    }

    fun selectDrawerItem(menuItem: MenuItem) {
        val fragment = when(menuItem.itemId) {
            R.id.card_search -> {
                CardSearchFragment.newInstance()
            }
            else -> CardSearchFragment.newInstance()
        }

        supportFragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit()

        menuItem.isChecked = true

        title = menuItem.title
        mDrawer?.closeDrawers()
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle?.syncState()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Pass any configuration change to the drawer toggles
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(drawerToggle?.onOptionsItemSelected(item)!!) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.card_search) {
            // Handle the camera action
        } else if (id == R.id.decks) {

        } else if (id == R.id.card_scan) {

        } else if (id == R.id.settings_item) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
