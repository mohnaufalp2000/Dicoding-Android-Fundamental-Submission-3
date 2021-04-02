package com.naufal.githubuser.activity

import android.icu.text.CompactDecimalFormat
import android.icu.util.ULocale
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.naufal.githubuser.R
import com.naufal.githubuser.adapter.TabDetailAdapter
import com.naufal.githubuser.databinding.ActivityDetailBinding
import com.naufal.githubuser.viewmodel.DetailViewModel
import com.naufal.githubuser.viewmodel.FavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {

    private val binding by lazy {ActivityDetailBinding.inflate(layoutInflater)}
    private val mDetailViewModel by lazy { ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java) }
    private val mFavoriteViewModel by lazy { ViewModelProvider(this).get(FavoriteViewModel::class.java) }

    companion object{
        const val USER = "user"
        const val ID = "id"
        const val AVATAR = "avatar"
    }

    private var followers : String? = ""
    private var followings : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val username = intent.getStringExtra(USER)
        val id = intent.getIntExtra(ID, 0)
        val avatar = intent.getStringExtra(AVATAR)

        showDetail(username)
        setupToolbar()
        setupFavoriteList(username, id, avatar)
    }

    private fun setupFavoriteList(username: String?, id: Int, avatar: String?) {
        var state = false

        CoroutineScope(Dispatchers.IO).launch {
            val data = mFavoriteViewModel.selectFavorite(id)
            withContext(Dispatchers.Main){
                if (data!=null){
                    state = if (data>0){
                        binding.btnFavorite.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        true
                    } else {
                        binding.btnFavorite.setBackgroundResource(R.drawable.ic_baseline_star_border_24)
                        false
                    }
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            state = !state

            if (state){
                binding.btnFavorite.setBackgroundResource(R.drawable.ic_baseline_star_24)
                mFavoriteViewModel.addFavorite(username, id, avatar)
            } else {
                binding.btnFavorite.setBackgroundResource(R.drawable.ic_baseline_star_border_24)
                mFavoriteViewModel.deleteFavorite(id)
            }
        }
    }

    private fun showDetail(username: String?) {
        mDetailViewModel.getDetailViewModel(username).observe(this@DetailActivity, {

            val numberFormat : CompactDecimalFormat = CompactDecimalFormat.getInstance(ULocale.UK, CompactDecimalFormat.CompactStyle.SHORT)

            Glide.with(this)
                    .load(it?.avatarUrl)
                    .into(binding.imgProfileDetail)

            binding.txtDetailName.text = it?.name ?: getString(R.string.user)
            binding.txtDetailUsername.text = it?.login
            binding.txtDetailLocation.text = it?.location
            binding.txtDetailRepositoryCount.text = numberFormat.format(it?.publicRepos)

            followers = numberFormat.format(it?.followers)
            followings = numberFormat.format(it?.following)

            val tabTitle : ArrayList<String> = arrayListOf("Followers ($followers)", "Followings ($followings)")

            binding.vpDetail.adapter = TabDetailAdapter(this)
            TabLayoutMediator(binding.tabDetail, binding.vpDetail){
                    tab, position -> tab.text = tabTitle[position]
            }.attach()

            val bio = it?.bio
            val company = it?.company
            val twitter = it?.twitterUsername
            val blog = it?.blog

            if (bio == null) {
                binding.apply {
                    cvBio.visibility = View.GONE
                    textViewBio.visibility = View.GONE
                }
            } else {
                binding.txtDetailBio.text = bio.toString()
            }

            if (company == null && blog == null && twitter == null) {
                binding.apply {
                    cvDetailDetail.visibility = View.GONE
                    textViewDetail.visibility = View.GONE
                }
            } else if (company == null) {
                binding.apply {
                    iconCompany.visibility = View.GONE
                    txtDetailCompany.visibility = View.GONE
                    txtDetailWebsite.text = blog
                    txtDetailTwitter.text = twitter.toString()
                }
            } else if (blog == null) {
                binding.apply {
                    iconWebsite.visibility = View.GONE
                    txtDetailWebsite.visibility = View.GONE
                    txtDetailCompany.text = company
                    txtDetailTwitter.text = twitter.toString()
                }
            } else if (twitter == null) {
                binding.apply {
                    iconTwitter.visibility = View.GONE
                    txtDetailTwitter.visibility = View.GONE
                    txtDetailWebsite.text = blog
                    txtDetailCompany.text = company
                }
            } else {
                binding.apply {
                    txtDetailCompany.text = company
                    txtDetailWebsite.text = blog
                    txtDetailTwitter.text = twitter.toString()
                }
            }
        })
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tbDetail.setNavigationOnClickListener {
            finish()
        }
    }

}