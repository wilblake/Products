[200~/**
 * We declare a package-level function main which returns Unit and takes
 * an Array of strings as a parameter. Note that semicolons are optional.
 */

fun main(args: Array<String>) {
    
    
    // println (" Multiple ? " + MulNum1(6 ,4))
    gennums()
}

fun gennums () {
   
    for (i in 1..100  )
    {   
        	if (MulNum1 (i,15)) println ("FizzBuzz")
            else if (MulNum1(i,3)) println("Fizz")
            else if (MulNum1(i,5)) println("Buzz")
            else println (i)
        
    }
}
fun MulNum1 (num1: Int , num2: Int ) : Boolean {
    if ( (num1 % num2 ) == 0 )
    return true
    else
    return false
    
    
}

