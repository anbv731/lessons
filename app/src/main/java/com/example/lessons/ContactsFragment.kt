package com.example.lessons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lessons.databinding.ContactsBinding
import com.google.android.material.appbar.MaterialToolbar


class ContactsFragment : Fragment() {
    lateinit var binding: ContactsBinding
    lateinit var contactModelList: MutableList<ContactModel>
    lateinit var appbar: MaterialToolbar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContactsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        appbar = binding.topAppBarDetail
        appbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactModelList = mutableListOf()
        getFakeContacts(100)
    }

    private fun getFakeContacts(number: Int) {
        for (i in 0..number) {
            val name = "00" + i + "00"
            val secondName = "///" + i + "///"
            val phone = "+" + i + i + i + i + i
            contactModelList.add(i, ContactModel(name, secondName, phone))
        }

        val adapter =
            RecyclerAdapter(requireContext(), contactModelList) { position -> toItem(position) }
        binding.recyclerViewContacts.adapter = adapter


    }

    private fun toItem(position: Int) {
        val bundle = Bundle()
        bundle.putString("name", contactModelList[position].name)
        bundle.putString("secondName", contactModelList[position].secondName)
        bundle.putString("number", contactModelList[position].number)
        bundle.putString("position", position.toString())
        val fragment = ContactDetailFragment()
        fragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.placeholder, fragment, null)
            ?.addToBackStack("detail")
            ?.commit()
    }
}