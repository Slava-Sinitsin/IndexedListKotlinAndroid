package com.example.lab3

import android.os.Environment
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import kotlin.math.floor
import kotlin.random.Random

class Lab3ViewModel : ViewModel() {
    enum class DataType {
        INTEGER,
        POINT2D
    }

    private var integerList = MyList<Int>(5)
    private var point2DList = MyList<Point2D>(5)

    var listAreaText by mutableStateOf("")
    var vectorAreaText by mutableStateOf("")

    var selectedDataType by mutableStateOf(DataType.INTEGER)
    var nValue by mutableStateOf("5")

    var dialogValue1 by mutableStateOf("")
    var dialogValue2 by mutableStateOf("")
    var dialogValue3 by mutableStateOf("")

    var showPushBackDialog by mutableStateOf(false)

    var showIndexAddDialog by mutableStateOf(false)

    var showMultiplyAllDialog by mutableStateOf(false)

    var showRemoveByIndexDialog by mutableStateOf(false)

    var showIntegerSaveFileToast by mutableStateOf(false)

    var showPoint2DSaveFileToast by mutableStateOf(false)

    fun integerListClean() {
        if (nValue != "") {
            integerList = MyList(nValue.toInt())
        }
        listAreaText = ""
        vectorAreaText = ""
    }

    fun point2DListClean() {
        if (nValue != "") {
            point2DList = MyList(nValue.toInt())
        }
        listAreaText = ""
        vectorAreaText = ""
    }

    fun onNValueChange(newN: String) {
        if (newN.length <= 3) {
            nValue = newN
        }
        integerListClean()
        point2DListClean()
    }

    fun onRandomAddButtonClick() {
        val random = Random
        if (nValue == "") {
            onNValueChange("5")
        }
        repeat(10) {
            val randomValue = when (selectedDataType) {
                DataType.INTEGER -> random.nextInt(0, 100)
                DataType.POINT2D -> Point2D(
                    floor(Random.nextDouble() * 100) / 10.0,
                    floor(Random.nextDouble() * 100) / 10.0
                )
            }
            when (selectedDataType) {
                DataType.INTEGER -> {
                    integerList.add(randomValue as Int)
                    listAreaText = integerList.toString()
                    vectorAreaText = integerList.vectorToString()
                }

                DataType.POINT2D -> {
                    point2DList.add(randomValue as Point2D)
                    listAreaText = point2DList.toString()
                    vectorAreaText = point2DList.vectorToString()
                }
            }
        }
    }

    fun onPushBackButtonClick() {
        showPushBackDialog = true
    }

    fun onConfirmPushBackDialogButtonClick() {
        showPushBackDialog = false
        when (selectedDataType) {
            DataType.INTEGER -> {
                if (dialogValue1 != "") {
                    integerList.add(dialogValue1.toInt())
                    listAreaText = integerList.toString()
                    vectorAreaText = integerList.vectorToString()
                }
            }

            DataType.POINT2D -> {
                if (dialogValue1 != "" && dialogValue2 != "") {
                    point2DList.add(
                        Point2D(
                            dialogValue1.toDouble(),
                            dialogValue2.toDouble()
                        )
                    )
                    listAreaText = point2DList.toString()
                    vectorAreaText = point2DList.vectorToString()
                }
            }
        }
        dialogValue1 = ""
        dialogValue2 = ""
    }

    fun onIndexAddButtonClick() {
        showIndexAddDialog = true
    }

    fun onConfirmIndexAddDialogButtonClick() {
        showIndexAddDialog = false
        when (selectedDataType) {
            DataType.INTEGER -> {
                if (dialogValue1 != "" && dialogValue2 != "") {
                    integerList.insert(dialogValue1.toInt(), dialogValue2.toInt())
                    listAreaText = integerList.toString()
                    vectorAreaText = integerList.vectorToString()
                }
            }

            DataType.POINT2D -> {
                if (dialogValue1 != "" && dialogValue2 != "" && dialogValue3 != "") {
                    point2DList.insert(
                        dialogValue1.toInt(),
                        Point2D(
                            dialogValue2.toDouble(),
                            dialogValue3.toDouble()
                        )
                    )
                    listAreaText = point2DList.toString()
                    vectorAreaText = point2DList.vectorToString()
                }
            }
        }
        if (listAreaText == "null <- null") {
            listAreaText = ""
            vectorAreaText = ""
        }
        dialogValue1 = ""
        dialogValue2 = ""
        dialogValue3 = ""
    }

    fun onMultiplyAllButtonClick() {
        showMultiplyAllDialog = true
    }

    fun onConfirmMultiplyAllDialogButtonClick() {
        showMultiplyAllDialog = false
        when (selectedDataType) {
            DataType.INTEGER -> {
                if (dialogValue1 != "") {
                    integerList.forEach(object : MyList.Callback<Int> {
                        override fun toDo(v: Int): Int {
                            return v * dialogValue1.toInt()
                        }
                    })
                    listAreaText = integerList.toString()
                    vectorAreaText = integerList.vectorToString()
                }
            }

            DataType.POINT2D -> {
                if (dialogValue1 != "") {
                    point2DList.forEach(object : MyList.Callback<Point2D> {
                        override fun toDo(v: Point2D): Point2D {
                            return Point2D(
                                v.getX() * dialogValue1.toInt(),
                                v.getY() * dialogValue1.toInt()
                            )
                        }
                    })
                    listAreaText = point2DList.toString()
                    vectorAreaText = point2DList.vectorToString()
                }
            }
        }
        if (listAreaText == "null <- null") {
            listAreaText = ""
            vectorAreaText = ""
        }
        dialogValue1 = ""
    }

    fun onRemoveByIndexButtonClick() {
        showRemoveByIndexDialog = true
    }

    fun onConfirmRemoveDialogButtonClick() {
        showRemoveByIndexDialog = false
        when (selectedDataType) {
            DataType.INTEGER -> {
                if (dialogValue1 != "") {
                    integerList.remove(dialogValue1.toInt())
                    listAreaText = integerList.toString()
                    vectorAreaText = integerList.vectorToString()
                }
            }

            DataType.POINT2D -> {
                if (dialogValue1 != "") {
                    point2DList.remove(dialogValue1.toInt())
                    listAreaText = point2DList.toString()
                    vectorAreaText = point2DList.vectorToString()
                }
            }
        }
        if (listAreaText == "null <- null") {
            listAreaText = ""
            vectorAreaText = ""
        }
        dialogValue1 = ""
    }

    fun onSortButtonClick() {
        when (selectedDataType) {
            DataType.INTEGER -> {
                integerList.mergeSort()
                listAreaText = integerList.toString()
                vectorAreaText = integerList.vectorToString()
            }

            DataType.POINT2D -> {
                point2DList.mergeSort()
                listAreaText = point2DList.toString()
                vectorAreaText = point2DList.vectorToString()
            }
        }
        if (listAreaText == "null <- null") {
            listAreaText = ""
            vectorAreaText = ""
        }
    }

    private fun isSavedListEmpty(input: String): Boolean {
        val pattern = "\\{\"n\":\\d+,\"data\":\\[]\\}".toRegex()
        return input.matches(pattern)
    }

    fun onSaveToFileButtonClick() {
        when (selectedDataType) {
            DataType.INTEGER -> {
                val string = integerList.serializeToJson()
                if (!isSavedListEmpty(string)) {
                    Log.e("write", string)
                    writeFile("IntegerList.json", string)
                    showIntegerSaveFileToast = true
                }
            }

            DataType.POINT2D -> {
                val string = point2DList.serializeToJson()
                if (!isSavedListEmpty(string)) {
                    Log.e("write", string)
                    writeFile("Point2DList.json", string)
                    showPoint2DSaveFileToast = true
                }
            }
        }
    }

    fun onLoadFromFileButtonClick() {
        when (selectedDataType) {
            DataType.INTEGER -> {
                val string = readFile("IntegerList.json")
                if (string.isNotEmpty() && !isSavedListEmpty(string)) {
                    integerList =
                        FileReader.deserializeFromString(string, Int::class.java)
                    selectedDataType = DataType.INTEGER
                    nValue = integerList.getN().toString()
                    listAreaText = integerList.toString()
                    vectorAreaText = integerList.vectorToString()
                    Log.e("read", integerList.toString())
                }
            }

            DataType.POINT2D -> {
                val string = readFile("Point2DList.json")
                if (string.isNotEmpty() && !isSavedListEmpty(string)) {
                    point2DList = FileReader.deserializeFromString(
                        string,
                        Point2D::class.java
                    )
                    selectedDataType = DataType.POINT2D
                    nValue = point2DList.getN().toString()
                    listAreaText = point2DList.toString()
                    vectorAreaText = point2DList.vectorToString()
                    Log.e("read", point2DList.toString())
                }
            }
        }
        if (listAreaText == "null <- null") {
            listAreaText = ""
            vectorAreaText = ""
        }
    }

    private fun writeFile(fileName: String, text: String) {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        try {
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, fileName)
            val fos = FileOutputStream(file)
            fos.write(text.toByteArray())
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readFile(fileName: String): String {
        val myFile =
            File(
                Environment.getExternalStorageDirectory()
                    .toString() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + fileName
            )
        Log.e(
            "read",
            Environment.getExternalStorageDirectory()
                .toString() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + fileName
        )
        try {
            val inputStream = FileInputStream(myFile)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            return try {
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                stringBuilder.toString()
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return ""
        }
    }
}