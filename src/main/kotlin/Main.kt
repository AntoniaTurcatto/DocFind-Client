package org.example

import org.example.model.Doctor
import org.example.model.Patient
import org.example.model.Person
import org.example.model.Role
import java.util.*

fun getValuesFromStringInput(input:String, vararg keys:String):Map<String,String?>{
    val map = mutableMapOf<String,String?>()
    for (key in keys){//because of vararg
        var keyCharPosition =0
        var finalIndexOfKeyInInput = 0
        for((index, charInput) in input.toCharArray().withIndex()){
            //comparing each key's char with input's chars to find them in order to proceeds to get the next value
            //if is not equal, break the for-loop

            if (keyCharPosition == key.length) { // equals to length because after finding the last char's key in the input, the counter will still sum +1
                finalIndexOfKeyInInput = index
                break
            }
            if(charInput == key.toCharArray()[keyCharPosition]) {
                keyCharPosition++ //the next to be compared
            }else {
                keyCharPosition=0 //reitiniate the iteration
            }
        }

        if(finalIndexOfKeyInInput > 0){ // the position of the key's last char + space should be > 0 to the key be considered found

            if (input.lastIndex >= finalIndexOfKeyInInput +1 //+1 to ignore the space between key and value
                && input.toCharArray()[finalIndexOfKeyInInput] == ' '//checking if IS there a space between those
                && input.toCharArray()[finalIndexOfKeyInInput+1] != ' '){ //and NO SUBSEQUENT SPACE

                val value = StringBuilder("")
                var limitChar : Char? = null
                for (char in input.substring(finalIndexOfKeyInInput +1) ){ //ignoring the space
                    //extracting the string of the input until there is a space or " in case of haveSpaces
                    if (limitChar == null){ //first iteration
                        limitChar = if (char == '"')
                            '"'
                        else
                            ' '
                    }
                    if(char != limitChar){
                        value.append(char)
                    } else if (value.toString() != ""){
                        break
                    }
                }
                map[key] = value.toString()
            } else {
                map[key] = null
            }

        } else {
            map[key] = null
        }

    }

    return map
}


fun createPerson(map: Map<String, String?>): Person?{
    if (map.containsKey("--roleCod")){//doctor
        if (map["--name"] != null && (map["--roleCod"] != null || map["--role"] != null)){
            val role : Role? = if (map["--roleCod"] != null) {
                Role.getRoleEnum(map["--roleCod"]!!.toInt())
            } else {
                Role.getRoleEnum(map["--role"]!!)
            }
            if (role == null) return null
            return Doctor(null, map["--name"]!!, role)
        }else
            return null
    }
    //patient
    return if (map["--name"] != null && map["--age"] != null){
        Patient(null,
            map["--name"]!!,
            map["--age"]!!.toInt(),
            map["--address"]?: "Unknown")
    } else {
        null
    }
}

fun main() {
    var loggedUser:Person?
    var input :String
    while (true){
        input = readln()
            when{
                input.contains("create ac -d")
                        && input.contains("--name")
                        && (input.contains("--roleCod")
                         != input.contains("--role "))->{ //create doctor account, != working like a XOR
                    println(getValuesFromStringInput(input.substring(12), "--name","--roleCod", "--role"))

                    loggedUser = createPerson(
                        getValuesFromStringInput(input.substring(12), "--name","--roleCod", "--role")
                    )

                    println(loggedUser.toString())
                }


                input.contains("create ac -p")
                        && input.contains("--name")
                        && input.contains("--age")->{ //create patient account
                    println(getValuesFromStringInput(input.substring(12), "--name", "--age", "--address"))

                    loggedUser = createPerson(
                        getValuesFromStringInput(
                            input.substring(12), "--name", "--age", "--address")
                    )
                    println(loggedUser.toString())
                }


                else -> println("Invalid command")
            }
    }
}