package kau.sohothackathon.compileerror.data

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import kau.sohothackathon.compileerror.data.model.Address
import kau.sohothackathon.compileerror.domain.AddressRepository
import javax.inject.Inject


class AddressRepositoryImpl @Inject constructor() : AddressRepository {

    override fun fetchAllContacts(context: Context): MutableList<Address> {
        val cr: ContentResolver = context.contentResolver
        val cur: Cursor? = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        val results = mutableListOf<Address>()
        if (cur != null && cur.count > 0) {
            while (cur.moveToNext()) {
                val columnIdIndex = cur.getColumnIndex(ContactsContract.Contacts._ID)
                val id: String = cur.getString(if (columnIdIndex == -1) 0 else columnIdIndex)
                val columnNameIndex = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val name: String =
                    cur.getString(if (columnNameIndex == -1) 0 else columnNameIndex)
                val phone = getPhoneNumber(cr, id)

                results.add(Address(name, phone))
            }
        }
        cur?.close()
        return results
    }

    private fun getPhoneNumber(cr: ContentResolver, id: String): String {
        var phone = ""
        val pCur: Cursor? = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
        )
        if (pCur != null && pCur.moveToFirst()) {
            val columnPhoneIndex =
                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            phone =
                pCur.getString(if (columnPhoneIndex == -1) 0 else columnPhoneIndex)
            pCur.close()
        }
        return phone
    }

}