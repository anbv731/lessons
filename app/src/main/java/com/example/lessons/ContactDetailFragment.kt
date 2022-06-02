package com.example.lessons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lessons.databinding.ContactsBinding
import com.example.lessons.databinding.DetailBinding

class ContactDetailFragment : Fragment(){
    lateinit var binding: DetailBinding
    lateinit var textName:TextView
    lateinit var textSecondName:TextView
    lateinit var textNumber:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        textName=binding.textName
        textSecondName=binding.textSecondName
        textNumber=binding.textNumber
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}