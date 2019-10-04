package aam.dex.screen.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class InitialViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment =
        when(position) {
            0 -> LoginFragment.newInstance()
            1 -> CreateAccountFragment.newInstance()
            else -> throw IllegalStateException()
        }

    override fun getCount() = 2

/*
    override fun isViewFromObject(view: View, obj: Any) = view == obj
    override fun getCount() = 2

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        when(position) {
            0 -> R.layout.view_login
            1 -> R.layout.view_login
            else -> null
        }?.let {
            val view = LayoutInflater.from(container.context).inflate(it, container, false)
            (container as ViewPager).addView(view, position)
            return view
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            0 -> "Sign In"
            1 -> "Registration"
            else -> ""
        }
    }*/
}