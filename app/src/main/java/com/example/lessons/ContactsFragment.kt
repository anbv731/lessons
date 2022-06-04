package com.example.lessons

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lessons.databinding.ContactsBinding
import com.google.android.material.appbar.MaterialToolbar


const val MY_PERMISSION_REQUEST_CONTACTS = 0

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
        appbar=binding.topAppBarDetail
        appbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactModelList = mutableListOf()
            val permission =
                ActivityCompat.checkSelfPermission(view.context, Manifest.permission.READ_CONTACTS)
            if (permission == PackageManager.PERMISSION_GRANTED) {
                getContacts()
            } else {
                getContactsPermission()
            }
    }

   fun getContacts() {
        val resolver = requireActivity().contentResolver
        val phones =
            resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        while (phones!!.moveToNext()) {
            var number:String?=""
            val id= phones.getStringOrNull(
                phones.getColumnIndex(
                    ContactsContract.Contacts._ID
                )
            ).toString()
            val name =
                phones.getStringOrNull(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

            if(phones.getStringOrNull(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER))!!.toInt()>0){
                val cursor = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    arrayOf<String>(id),
                    null
                )
                if (cursor!=null)
                while (cursor.moveToNext()) {
                    val phoneType = cursor.getIntOrNull(
                        cursor.getColumnIndex(
                            Phone.TYPE
                        )
                    )
                    val phoneNumber: String = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                            Phone.NUMBER
                        )
                    )

                    when (phoneType) {
                        Phone.TYPE_MOBILE -> number=phoneNumber
                        Phone.TYPE_HOME -> println(phoneNumber)
                        Phone.TYPE_WORK -> println(phoneNumber)
                        Phone.TYPE_OTHER -> println(phoneNumber)
                        else -> {}
                    }
                }
                cursor?.close()
            }

            val contactModel = ContactModel(name,null,number)

            contactModelList.add(contactModel)
            Log.d("name>>", name + "  ")
        }
        phones.close()
        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = RecyclerAdapter(requireContext(),contactModelList) { position-> toItem(position) }
        binding.recyclerViewContacts.layoutManager = layoutManager
        binding.recyclerViewContacts.adapter = adapter
    }

    fun getContactsPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Ahtung!")
            alertDialogBuilder.setMessage("Ну очень нужно")
            alertDialogBuilder.setPositiveButton(android.R.string.yes) { dialog, which ->
                requestPermissions(
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    MY_PERMISSION_REQUEST_CONTACTS
                )
            }
            alertDialogBuilder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(
                    context,
                    "Как хочешь!", Toast.LENGTH_SHORT
                ).show()
            }
            alertDialogBuilder.show()

        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                MY_PERMISSION_REQUEST_CONTACTS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSION_REQUEST_CONTACTS) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts()
            }
        }
    }
    private fun toItem(position:Int){
        val bundle=Bundle()
        bundle.putString("name",contactModelList[position].name)
        bundle.putString("number",contactModelList[position].number)
        val fragment=ContactDetailFragment()
        fragment.arguments=bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.placeholder, fragment, null)
            ?.addToBackStack("detail")
            ?.commit()
    }
}