package kau.sohothackathon.compileerror.ui.address

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.sohothackathon.compileerror.data.model.Address
import kau.sohothackathon.compileerror.domain.AddressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository
) : ViewModel() {

    val addresses = mutableStateListOf<Address>()
    private val _filteredAddresses = mutableStateOf(emptyList<Address>())
    val filteredAddresses: State<List<Address>> get() = _filteredAddresses
    private val _phonefilteredAddresses = mutableStateOf(emptyList<Address>())
    val phonefilteredAddresses: State<List<Address>> get() = _phonefilteredAddresses
    private val _search = mutableStateOf("")
    val search: State<String> get() = _search
    private val _phoneSearch = mutableStateOf("")
    val phoneSearch: State<String> get() = _phoneSearch
    fun getAllContacts(context: Context) = viewModelScope.launch(Dispatchers.Default) {
        addresses.addAll(addressRepository.fetchAllContacts(context).sortedBy { it.name })
    }

    fun updatePhoneSearch(number: Char) {
        if (_phoneSearch.value.length < 11) {
            _phonefilteredAddresses.value = addresses.filter {
                it.phone.contains(_phoneSearch.value.plus(number), ignoreCase = true)
            }
            _phoneSearch.value = _phoneSearch.value.plus(number)
        }
    }

    fun deletePhoneSearch() {
        if (_phoneSearch.value.isNotEmpty()) {
            _phonefilteredAddresses.value = addresses.filter {
                it.phone.contains(_phoneSearch.value, ignoreCase = true)
            }
            _phoneSearch.value = _phoneSearch.value.dropLast(1)
        }
    }

    fun updateSearch(query: String) {
        _filteredAddresses.value = addresses.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.phone.contains(query, ignoreCase = true)
        }
        _search.value = query
    }
}