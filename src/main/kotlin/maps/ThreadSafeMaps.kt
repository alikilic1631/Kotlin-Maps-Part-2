package maps

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

open class LockedMap<K, V>(private val map: CustomMutableMap<K, V>) :
    CustomMutableMap<K, V> {
    private val lock: Lock = ReentrantLock()

    override val entries: Iterable<Entry<K, V>>
        get() = lock.withLock {
            map.entries.toList()
        }
    override val keys: Iterable<K>
        get() = lock.withLock {
            map.keys.toList()
        }
    override val values: Iterable<V>
        get() = lock.withLock {
            map.values.toList()
        }

    override fun contains(key: K): Boolean = map.contains(key)

    override fun get(key: K): V? = map[key]

    override fun put(entry: Entry<K, V>): V? {
        return this.set(entry.key, entry.value)
    }

    override fun remove(key: K): V? {
        lock.withLock {
            return map.remove(key)
        }
    }

    override fun put(key: K, value: V): V? {
        return this.set(key, value)
    }

    override fun set(key: K, value: V): V? {
        lock.withLock {
            return map.set(key, value)
        }
    }
}


class LockedListBasedMap<K, V> : LockedMap<K, V>(ListBasedMap())


class LockedHashMapBackedByLists<K, V> : LockedMap<K, V>(HashMapBackedByLists())
