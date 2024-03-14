package maps

class ListBasedMap<K, V> : CustomMutableMap<K, V> {
    override var entries: CustomLinkedList<Entry<K, V>> = CustomLinkedList()

    override val keys: Iterable<K>
        get() = entries.map { it.key }
    override val values: Iterable<V>
        get() = entries.map { it.value }

    override fun contains(key: K): Boolean = key in keys

    override fun get(key: K): V? {
        if (!contains(key)) {
            return null
        }
        return entries.first { it.key == key }.value
    }

    override fun remove(key: K): V? {
        if (!contains(key)) {
            return null
        }
        val removedVal = this[key]

        var newEntries: CustomLinkedList<Entry<K, V>> = CustomLinkedList()
        entries.filter { it.key != key }.forEach { newEntries.add(it) }
        entries = newEntries

        return removedVal
    }

    override fun put(
        key: K,
        value: V,
    ): V? = this.set(key, value)

    override fun put(entry: Entry<K, V>): V? = this.set(entry.key, entry.value)

    override fun set(
        key: K,
        value: V,
    ): V? {
        var oldVal: V? = null
        if (contains(key)) {
            oldVal = this.remove(key)
        }
        (entries as CustomLinkedList).add(Entry(key, value))
        return oldVal
    }
}
