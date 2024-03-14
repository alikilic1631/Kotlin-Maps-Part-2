@file:Suppress("ktlint:standard:filename")

package maps

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

abstract class StripedGenericHashMap<K, V>(bucketFactory: BucketFactory<K, V>) :
    GenericHashMap<K, V>(bucketFactory) {

    private val lockList: List<Lock> =
        (1..super.bucketArr.size).map { ReentrantLock() }

    private fun getLock(key: K): Lock = lockList[key.hashCode() % lockList.size]

    override val entries: Iterable<Entry<K, V>>
        get() {
            try {
                lockList.forEach { it.lock() }
                return super.entries
            } finally {
                lockList.forEach { it.unlock() }
            }
        }
    override val keys: Iterable<K>
        get() {
            try {
                lockList.forEach { it.lock() }
                return super.keys
            } finally {
                lockList.forEach { it.unlock() }
            }
        }
    override val values: Iterable<V>
        get() {
            try {
                lockList.forEach { it.lock() }
                return super.values
            } finally {
                lockList.forEach { it.unlock() }
            }
        }

    override fun contains(key: K): Boolean {
        getLock(key).withLock {
            return super.contains(key)
        }
    }

    override fun resize(newCapacity: Int) {
        try {
            lockList.map { it.lock() }
            super.resize(newCapacity)
        } finally {
            lockList.map { it.unlock() }
        }
    }

    override fun set(
        key: K,
        value: V,
    ): V? {
        getLock(key).withLock {
            return super.set(key, value)
        }
    }

    override fun put(entry: Entry<K, V>): V? {
        getLock(entry.key).withLock {
            return super.set(entry.key, entry.value)
        }
    }

    override fun put(
        key: K,
        value: V,
    ): V? {
        getLock(key).withLock {
            return super.put(key, value)
        }
    }

    override fun remove(key: K): V? {
        getLock(key).withLock {
            return super.remove(key)
        }
    }
}


class StripedHashMapBackedByLists<K, V> :
    StripedGenericHashMap<K, V>({ ListBasedMap() })

class StripedHashMapBackedByTrees<K, V>(keyComparator: Comparator<K>) :
    StripedGenericHashMap<K, V>({ TreeBasedMap(keyComparator) })


