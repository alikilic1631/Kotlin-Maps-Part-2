package maps

interface Node<T> {
    var next: ValueNode<T>?
}

class RootNode<T>(override var next: ValueNode<T>? = null) : Node<T>

class ValueNode<T>(val value: T, override var next: ValueNode<T>? = null) :
    Node<T>

class CustomLinkedList<T> : MutableIterable<T> {
    private var root: RootNode<T> = RootNode()

    private var head: ValueNode<T>?
        get() = root.next
        set(valNode) {
            root.next = valNode as ValueNode<T>
        }

    fun isEmpty() = head == null

    fun add(value: T) {
        head = ValueNode(value, head)
    }

    fun peek(): ValueNode<T>? {
        return head
    }

    fun remove(): T? {
        try {
            return head?.value
        } finally {
            head = head?.next
        }
    }

    override fun iterator(): MutableIterator<T> =
        object : MutableIterator<T> {
            var previous: Node<T>? = null
            var current: Node<T> = this@CustomLinkedList.root

            override fun hasNext(): Boolean = current.next != null

            override fun next(): T {
                if (!hasNext()) {
                    throw NoSuchElementException()
                }
                previous = current
                current = current.next as Node<T>
                return (current as ValueNode<T>).value
            }

            override fun remove() {
                if (!isEmpty()) {
                    throw UnsupportedOperationException()
                } else if (previous == this@CustomLinkedList.root) {
                    current = this@CustomLinkedList.root
                    previous = null
                } else {
                    previous!!.next = current.next
                }
            }
        }
}
