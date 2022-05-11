TODO List
==============

-Load in program
    -Via source code
        -Give source code, require they specify a main class (for everything else make this optional)
    -Via port connection?
        -Ie via currently running program
    -Via JAR
    -Via class files
    -Program fragments?
        -I want to take a function (calculate fibbonaci or smthn) and a range of inputs to do all of this information gathering
            -Range over multiple types of data, so 1-5 for numbers, aaaa-ZZZZ for all 4 letter words from all a's to all z's of both cases, over a range of characters (mby mark it with a control character? so #aaaa-ZZZZ All 4 character strings with char values between 'a' and 'Z'?), over different bases, AND give the user the ability to give a program (function / jar / whatever, same as higher layer) that supplies the range of inputs
-Gather a bunch of data
    -Runtime
    -Number and speed of disk accesses
    -Number and life span of threads
        -Sorted by thread type (ie what concrete object each thread is)
        -Sort by time created and died (Like a timeline of threads)
        -Give some sort of collapse function for programs that spin off thousands of threads of the same couple of types
        -And maybe something for programs that do massive weird stuff with hundreds of different types of threads
    -Memory usage
        -Maybe something to show redundent memory use (like the same stuff stored multiple times at the same time)
    -Function speed
        -Average time it takes each function to run
        -Store the inputs and states for extraordinary cases
            -This'll catch functions that run fine on all but one or two cases where they go insanely slow
-Display this data
-It'd be nice if I could use this as part of a build process like a test, so I can say "Fail this test if OSCA says u'll take more than 5 seconds to run"