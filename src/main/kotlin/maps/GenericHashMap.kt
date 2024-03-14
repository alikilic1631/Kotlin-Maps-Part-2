package maps

typealias BucketFactory<K, V> = () -> CustomMutableMap<K, V>

const val NORMAL_ARRAY_SIZE = 16
const val LOAD_FACTOR = 0.75

abstract class GenericHashMap<K, V>(private val bucketFactory: BucketFactory<K, V>) :
    CustomMutableMap<K, V> {
    protected var bucketArr: Array<CustomMutableMap<K, V>> =
        Array(NORMAL_ARRAY_SIZE) { bucketFactory() }

    override val entries: Iterable<Entry<K, V>>
        get() = bucketArr.asIterable().flatMap { it.entries }
    override val keys: Iterable<K>
        get() = entries.map { it.key }
    override val values: Iterable<V>
        get() = entries.map { it.value }
    protected val capacity
        get() = bucketArr.size
    open var numElems = 0
        set(value) {
            field = value
            if (numElems > capacity * LOAD_FACTOR) {
                this.resize(capacity * 2)
            }
        }

    open fun resize(newCapacity: Int) {
        val oldEntries = this.entries
        bucketArr = Array(newCapacity) { bucketFactory() }
        oldEntries.forEach { this.put(it) }
    }

    private fun getBucket(key: K): CustomMutableMap<K, V> = bucketArr[key.hashCode() % capacity]

    override fun get(key: K): V? = this.getBucket(key)[key]

    override fun remove(key: K): V? {
        numElems--
        return this.getBucket(key).remove(key)
    }

    override fun contains(key: K): Boolean = this.getBucket(key).contains(key)

    override fun set(
        key: K,
        value: V,
    ): V? {
        val setVal = this.getBucket(key).set(key, value)
        if (setVal == null) {
            numElems++
        }
        return setVal
    }

    override fun put(entry: Entry<K, V>): V? = this.set(entry.key, entry.value)

    override fun put(
        key: K,
        value: V,
    ): V? = this.set(key, value)
}
