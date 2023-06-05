package kau.sohothackathon.compileerror.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.sohothackathon.compileerror.data.model.Address
import kau.sohothackathon.compileerror.domain.AddressRepository
import kau.sohothackathon.compileerror.domain.FileRepository
import kau.sohothackathon.compileerror.domain.model.MediaFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val addressRepository: AddressRepository,
    private val fileRepository: FileRepository
) : ViewModel() {

    /** 주소록 화면 */
    val addresses = mutableStateListOf<Address>()
    private val _filteredAddresses = mutableStateOf(emptyList<Address>())
    val filteredAddresses: State<List<Address>> get() = _filteredAddresses
    private val _phonefilteredAddresses = mutableStateOf(emptyList<Address>())
    val phonefilteredAddresses: State<List<Address>> get() = _phonefilteredAddresses
    private val _search = mutableStateOf("")
    val search: State<String> get() = _search
    private val _phoneSearch = mutableStateOf("")
    val phoneSearch: State<String> get() = _phoneSearch

    /** 파일 화면 */
    private val _mediaFiles = mutableStateOf(emptyList<MediaFile>())
    val mediaFiles: State<List<MediaFile>> get() = _mediaFiles
    private val _filteredMediaFiles = mutableStateOf(emptyList<MediaFile>())
    val filteredMediaFiles: State<List<MediaFile>> get() = _filteredMediaFiles
    private val _mediaSearch = mutableStateOf("")
    val mediaSearch: State<String> get() = _mediaSearch

    fun getAllContacts(context: Context) = viewModelScope.launch(Dispatchers.Default) {
        if (addresses.isEmpty()) {
            addresses.clear()
            addresses.addAll(addressRepository.fetchAllContacts(context).sortedBy { it.name })
        }
    }

    fun getAllMediafiles() = viewModelScope.launch(Dispatchers.Default) {
        if (_mediaFiles.value.isEmpty()) {
            _mediaFiles.value = fileRepository.fetchAllMediaFiles()
        }
    }

    fun updatePhoneSearch(number: Char) {
        if (_phoneSearch.value.length < 11) {
            _phonefilteredAddresses.value = addresses.filter {
                it.phone.contains(_phoneSearch.value.plus(number), ignoreCase = true)
            }
            _phoneSearch.value = _phoneSearch.value.plus(number)
        }
    }

    fun updateMediaSearch(mediaSearch: String) {
        _mediaSearch.value = mediaSearch
        _filteredMediaFiles.value = _mediaFiles.value.filter {
            it.name.contains(mediaSearch, ignoreCase = true)
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

    fun playAudio(contrntUri: String) {

    }

    fun stopAudio() {

    }
}