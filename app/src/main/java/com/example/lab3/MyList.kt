package com.example.lab3

import android.util.Log
import java.io.*

class MyList<T1>(private val n: Int) {
    inner class MyNode<T2>(data: T2) {
        var prev: MyNode<T2>? = null
        var next: MyNode<T2>? = null
        var value: T2 = data
    }

    private var head: MyNode<T1>? = null
    private var tail: MyNode<T1>? = null
    private var size: Int = 0
    private val vector: MutableList<MyNode<T1>> = mutableListOf()

    fun getN(): Int = n


    @Suppress("unused")
    fun getSize(): Int = size

    fun add(data: T1) {
        val newNode = MyNode(data)
        if (head == null) {
            head = newNode
        } else {
            tail?.next = newNode
            newNode.prev = tail
        }
        tail = newNode
        size += 1
        if (size % n == 0) {
            vector.add(newNode)
        }
    }

    //noinspection KotlinUnusedSymbol
    fun get(index: Int): T1 {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("Index is out of bounds.")
        }
        var current = head
        repeat(index) {
            current = current?.next
        }
        return current!!.value
    }

    fun remove(index: Int) {
        if (index > -1 && index < size) {
            if (index == 0) {
                head = head?.next
                head?.prev = null
                if (size == 1) {
                    tail = null
                }
            } else {
                var current: MyNode<T1>?
                if (vector.size > 1 && index >= n) {
                    current = vector[index / n - 1]
                    for (i in (vector.size * n - index) until (index - 1)) {
                        current = current?.next
                    }
                } else {
                    current = head
                    repeat(index - 1) {
                        current = current?.next
                    }
                }
                current?.next = current?.next?.next
                current?.next?.prev = current
                if (current?.next == null) {
                    tail = current
                }
            }
            size -= 1
            updateVector(index, "rem")
        }
    }

    @Suppress("KotlinConstantConditions")
    fun insert(index: Int, data: T1) {
        val newNode = MyNode(data)
        if (index > -1 && index < size) {
            if (index == 0) {
                newNode.next = head
                head?.prev = newNode
                head = newNode
                if (size == 0) {
                    tail = newNode
                }
            } else {
                var current: MyNode<T1>?
                if (vector.size > 1 && index >= n) {
                    current = vector[index / n - 1]
                    for (i in (n * (index / n)) until index) {
                        current = current?.next
                    }
                } else {
                    current = head
                    repeat(index - 1) {
                        current = current?.next
                    }
                }
                newNode.next = current?.next
                current?.next?.prev = newNode
                current?.next = newNode
                newNode.prev = current
                if (newNode.next == null) {
                    tail = newNode
                }
            }
            size += 1
            updateVector(index, "add")
        }
    }

    private fun updateVector(index: Int, op: String) {
        if (op == "add") {
            for (i in (index / n) until vector.size) {
                vector[i] = vector[i].prev!!
            }
            if (size % n == 0) {
                vector.add(tail!!)
            }
        }
        if (op == "rem") {
            for (i in (index / n) until vector.size) {
                if (vector[i].next == null) {
                    vector.removeAt(i)
                    break
                } else {
                    vector[i] = vector[i].next!!
                }
            }
        }
        if (op == "sort") {
            vector.clear()
            var current = head
            var count = 1
            while (current != null) {
                if (count % n == 0) {
                    vector.add(current)
                }
                current = current.next
                count += 1
            }
            current = head
            current?.prev = null
            while (current?.next != null) {
                current.next?.prev = current
                current = current.next
            }
            tail = current
        }
    }


    @Suppress("unused")
    fun display() {
        var current = head
        print("null <- ")
        while (current != null) {
            print(current.value)
            if (current.next != null) {
                print(" <-> ")
            } else {
                print(" -> ")
            }
            current = current.next
        }
        println("null")
        print("N = $n: ")
        vector.forEach { node ->
            print("[${node.value}] ")
        }
        println()
    }

    override fun toString(): String {
        val builder = StringBuilder()
        var current = head
        builder.append("null <- ")
        while (current != null) {
            builder.append(current.value)
            if (current.next != null) {
                builder.append(" <-> ")
            } else {
                builder.append(" -> ")
            }
            current = current.next
        }
        builder.append("null")
        return builder.toString()
    }

    fun vectorToString(): String {
        val builder = StringBuilder()
        builder.append("N = $n: ")
        vector.forEach { node ->
            builder.append("[${node.value}] ")
        }
        return builder.toString()
    }


    fun mergeSort() {
        head = mergeSort(head)
        tail = findTail(head)
        updateVector(0, "sort")
    }

    private fun mergeSort(head: MyNode<T1>?): MyNode<T1>? {
        if (head?.next == null) {
            return head
        }

        val middle = findMiddle(head)
        val secondHalf = middle?.next
        middle?.next = null

        val left = mergeSort(head)
        val right = mergeSort(secondHalf)

        return merge(left, right)
    }

    private fun findMiddle(head: MyNode<T1>?): MyNode<T1>? {
        if (head == null) {
            return null
        }

        var slow = head
        var fast = head

        while (fast?.next != null && fast.next?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
        }

        return slow
    }

    private fun merge(left: MyNode<T1>?, right: MyNode<T1>?): MyNode<T1>? {
        val result: MyNode<T1>?
        if (left == null) {
            return right
        }
        if (right == null) {
            return left
        }
        if (compare(left.value, right.value) <= 0) {
            result = left
            result.next = merge(left.next, right)
        } else {
            result = right
            result.next = merge(left, right.next)
        }
        return result
    }

    private fun findTail(head: MyNode<T1>?): MyNode<T1>? {
        if (head == null) {
            return null
        }

        var current = head
        while (current?.next != null) {
            current = current.next
        }

        return current
    }

    @Suppress("UNCHECKED_CAST")
    private fun compare(a: T1, b: T1): Int {
        return when (a) {
            is Comparable<*> -> (a as Comparable<T1>).compareTo(b)
            else -> throw IllegalArgumentException("Elements must implement Comparable interface.")
        }
    }

    interface Callback<T> {
        fun toDo(v: T): T
    }

    fun forEach(callback: Callback<T1>) {
        var current = head
        while (current != null) {
            current.value = callback.toDo(current.value)
            current = current.next
        }
    }

    fun serializeToJson(): String {
        val json = StringBuilder()
        json.append("{")
        json.append("\"n\":").append(n).append(",")
        json.append("\"data\":[")
        var current = head
        while (current != null) {
            when (val value = current.value) {
                is Point2D -> json.append("\"(${value.getX()};${value.getY()})\",")
                else -> json.append("\"$value\",")
            }
            current = current.next
        }
        if (json.endsWith(",")) {
            json.setLength(json.length - 1)
        }
        json.append("]")
        json.append("}")
        return json.toString()
    }
}

object FileReader {
    @Suppress("UNCHECKED_CAST")
    fun <E> deserializeFromString(jsonString: String, elementType: Class<E>): MyList<E> {
        Log.e("jsonString", jsonString)
        var myList: MyList<E>? = null
        try {
            val index = jsonString.indexOf("\"n\":") + 4
            val endIndex = jsonString.indexOf(",", index)
            val n = Integer.parseInt(jsonString.substring(index, endIndex))
            myList = MyList(n)

            val dataStartIndex = jsonString.indexOf("\"data\":[") + 8
            val dataEndIndex = jsonString.lastIndexOf("]")
            val dataString = jsonString.substring(dataStartIndex, dataEndIndex)
            val elements = dataString.split(",")
            for (element in elements) {
                when (elementType) {
                    Int::class.java -> {
                        val value = Integer.parseInt(element.substring(1, element.length - 1))
                        myList.add(value as E)
                    }

                    Point2D::class.java -> {
                        val coordinates = element.substring(2, element.length - 2).split(";")
                        val x = coordinates[0].trim().toDouble()
                        val y = coordinates[1].trim().toDouble()
                        val point = Point2D(x, y)
                        myList.add(point as E)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return myList!!
    }
}