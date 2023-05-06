package kau.sohothackathon.compileerror.domain

import android.content.Context
import kau.sohothackathon.compileerror.data.model.Address

interface AddressRepository {

    fun fetchAllContacts(context: Context): MutableList<Address>
}