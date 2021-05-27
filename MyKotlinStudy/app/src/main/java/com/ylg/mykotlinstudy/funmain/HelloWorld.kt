package com.ylg.mykotlinstudy.funmain


fun main() {
    val test = { println("Lambda 无参数") }
    test()

    var test1: (String) -> Int = { str ->
        println(str)
        str.length
    }
    test1("Lambda 参数test1")

    var test2 = { str: String ->
        println(str)
        str.length
    }
    test2("Lambda 参数test2")

    val arr = arrayOf(1, 3, 5, 7, 9)
    // 过滤掉数组中元素小于2的元素，取其第一个打印。这里的it就表示每一个元素。
    println(arr.filter { it < 5 }.component1())

    val map = mapOf("key1" to "value1", "key2" to "value2", "key3" to "value3")

    map.forEach { key, value ->
        println("$key \t $value")
    }

    map.forEach { value ->
        println("$value")
    }

    val mi = object : Myinterface {
        override fun doSom(key: String, value: String) {
            println("MIMI: $key \t $value")
        }
    }

    forEach(mi) { println("Map1: $map") }
}


interface Myinterface {
    fun doSom(key: String, value: String)
}
fun doSom(key: String, value: String) {
    println("fun doSom: $key \t $value" + key)
}
fun forEach(m: Myinterface, action: (map: Map<String, String>) -> Unit): Unit {
    var map1 = mapOf("key1" to "value1", "key2" to "value2", "key3" to "value3")
    action(map1)
    println("map1:first: " + map1.keys.toString() + " ," + map1.keys.first())
    m.doSom(map1.keys.first().toString(), map1.values.first())
    doSom(map1.keys.first().toString(), map1.values.first())
}