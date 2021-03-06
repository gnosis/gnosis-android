package io.gnosis.safe.ui.settings.owner.list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.gnosis.safe.utils.MnemonicAddressDerivator
import kotlinx.coroutines.flow.Flow
import pm.gnosis.model.Solidity

class OwnerPagingProvider(
    private val derivator: MnemonicAddressDerivator
) {

    fun getOwnersStream(): Flow<PagingData<Solidity.Address>> {
        return Pager(
            initialKey = 0,
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE,
                maxSize = PAGE_SIZE * MAX_PAGES
            ),
            pagingSourceFactory = {
                OwnerPagingSource(derivator, MAX_PAGES)
            }
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 20
        const val MAX_PAGES = 5
    }
}
