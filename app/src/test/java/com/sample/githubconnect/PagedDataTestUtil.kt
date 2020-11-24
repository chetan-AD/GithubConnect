package com.sample.githubconnect

import androidx.paging.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

private val dcb = object : DifferCallback {
    override fun onChanged(position: Int, count: Int) {}
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T : Any> PagingData<T>.collectData(): List<T> {
    val items = mutableListOf<T>()
    val dif = object : PagingDataDiffer<T>(dcb, TestCoroutineDispatcher()) {
        override suspend fun presentNewList(previousList: NullPaddedList<T>, newList: NullPaddedList<T>, newCombinedLoadStates: CombinedLoadStates, lastAccessedIndex: Int): Int? {
            for (idx in 0 until newList.size)
                items.add(newList.getFromStorage(idx))
            return null
        }
    }
    dif.collectFrom(this)
    return items
}