package com.now.three_days.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.now.three_days.MainActivity
import com.now.three_days.R
import com.now.three_days.adapter.CalendarAdapter
import com.now.three_days.adapter.ViewPagerMainAdapter
import com.now.three_days.data.model.CalendarVO
import com.now.three_days.data.viewmodel.CListViewModel
import com.now.three_days.databinding.FragmentCalendarBinding
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment : Fragment() {

    private lateinit var clistView: CListViewModel
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    lateinit var calendarAdapter: CalendarAdapter
    private var cList = ArrayList<CalendarVO>()

//    var week_textView: TextView = binding.weekTextview

    companion object {
        fun newInstance() = CalendarFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        clistView =
            ViewModelProvider(this).get(CListViewModel::class.java)

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        binding.viewPager.setBackgroundResource(R.color.white)

        viewPager.adapter = ViewPagerMainAdapter(this)
        val tabTitle = arrayListOf<String>("Challenge", "Relay")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        val mainAct = activity as MainActivity
        mainAct?.setBottomNav(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var week_day: Array<String> = resources.getStringArray(R.array.calendar_day)

        cList.removeAll(cList)
        calendarAdapter = CalendarAdapter(cList)
        val dateFormat =
            DateTimeFormatter.ofPattern("dd").withLocale(Locale.forLanguageTag("ko"))
        val monthFormat =
            DateTimeFormatter.ofPattern("yyyy년 MM월").withLocale(Locale.forLanguageTag("ko"))

        val localDate = LocalDateTime.now().format(monthFormat)
        binding.dayTextView.text = localDate

        val nowDate = LocalDateTime.now().format(dateFormat)

        var preSunday: LocalDateTime = LocalDateTime.now().with(
            TemporalAdjusters.previous(
                DayOfWeek.SUNDAY
            )
        )

        for (i in 0..6) {
            cList.apply {
                add(CalendarVO(preSunday.plusDays(i.toLong()).format(dateFormat), week_day[i]))
            }
        }
        binding.calendarRecyclerview.adapter = calendarAdapter
        binding.calendarRecyclerview.layoutManager = GridLayoutManager(context, 7)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}