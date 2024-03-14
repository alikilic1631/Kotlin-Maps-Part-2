package maps

class HashMapBackedByTrees<K,V>(keyComparator: Comparator<K>):
    GenericHashMap<K,V>({ TreeBasedMap(keyComparator) })
